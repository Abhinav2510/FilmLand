package org.filmland.catalog.web.controllers;

import lombok.RequiredArgsConstructor;
import org.filmland.catalog.web.dto.UserCreateDTO;
import org.filmland.catalog.entity.User;
import org.filmland.catalog.web.dto.UserResponseDTO;
import org.filmland.catalog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "users")
@RequiredArgsConstructor
public class UserController {


    private final ModelMapper modelMapper;
    private final UserService userService;

    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO signUp(@RequestBody @Valid UserCreateDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        userService.createUser(user);
        return new UserResponseDTO("User created","Successfully created user");
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO signIn(@RequestBody @Valid UserCreateDTO userCreateDTO, HttpServletResponse httpResponse) {
        User user = modelMapper.map(userCreateDTO, User.class);
        String jwt = userService.signInUserAndGenerateJWT(user);
        httpResponse.setHeader("Access-Control-Expose-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        httpResponse.addHeader("Authentication", "Bearer " + jwt);
        return new UserResponseDTO("Login Successful","Logged in successfully. Find bearer token in headers");
    }
}
