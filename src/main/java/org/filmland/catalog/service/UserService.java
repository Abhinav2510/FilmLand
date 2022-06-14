package org.filmland.catalog.service;

import lombok.extern.slf4j.Slf4j;
import org.filmland.catalog.entity.User;
import org.filmland.catalog.repository.UserRepository;
import org.filmland.catalog.security.utils.AuthUtils;
import org.filmland.catalog.web.errors.ErrorCodes;
import org.filmland.catalog.web.errors.FilmLandFeatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@Slf4j
public class UserService {
    @Value("${jwt.secret}")
    private String authenticationSigningSecret;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * Creates user in database with encoded password
     * @param user input user
     */
    public void createUser(@Valid User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.findById(user.getEmail()).
                ifPresent(existingUser -> {
                    log.warn("Can not create user. already exist : {}", user.getEmail());
                    throw new FilmLandFeatureException(ErrorCodes.USER_ALREADY_EXIST, "User already exists with same email address");
                });

        userRepo.save(user);
    }

    /**
     * checks if user is valid and generates JWT token
     * @param user input user credentials
     * @return JWT token
     */
    public String signInUserAndGenerateJWT(@Valid User user) {
        authenticateUser(user);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        return AuthUtils.generateJWT(userDetails.getUsername(),authenticationSigningSecret);
    }

    /**
     * Validates user creds
     * @param user input user
     */
    private void authenticateUser(User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (DisabledException | BadCredentialsException ex) {
            log.debug("User login attempt failed for {} reason : {}", user.getEmail(), ex.getLocalizedMessage());
            throw new FilmLandFeatureException(ErrorCodes.LOGIN_FAILED);
        }
    }

}
