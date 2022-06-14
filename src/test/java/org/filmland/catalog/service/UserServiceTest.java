package org.filmland.catalog.service;

import org.filmland.catalog.entity.User;
import org.filmland.catalog.repository.UserRepository;
import org.filmland.catalog.web.errors.ErrorCodes;
import org.filmland.catalog.web.errors.FilmLandFeatureException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    UserService userService;

    User user = new User("test@user.com", "Password");


    @Test
    public void test_UserIsValid_AlreadyExist() {
        Mockito.when(userRepository.findById(Mockito.anyString())).
                thenReturn(Optional.of(user));
        FilmLandFeatureException exception =
                Assert.assertThrows(FilmLandFeatureException.class, () -> userService.createUser(user));
        Assert.assertEquals(ErrorCodes.USER_ALREADY_EXIST,exception.getErrorCode());
    }

    @Test
    public void test_UserValid_IsNewUser() {
        Mockito.when(userRepository.findById(Mockito.anyString())).
                thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        userService.createUser(user);
        Mockito.verify(userRepository,Mockito.times(1)).findById(user.getEmail());
        Mockito.verify(userRepository,Mockito.times(1)).save(user);
    }

}