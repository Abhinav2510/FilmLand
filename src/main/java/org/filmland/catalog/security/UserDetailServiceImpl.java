package org.filmland.catalog.security;

import org.filmland.catalog.entity.User;
import org.filmland.catalog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * UserDetailServiceImpl
 * Overrides loadUserByUsername to use custom user entity
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param email of user to authenticate
     * @return UserDetails containing  User object with principal details
     * @throws UsernameNotFoundException spring Exception on missing username
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findById(email).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}