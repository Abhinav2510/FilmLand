package org.filmland.catalog.service;

import org.filmland.catalog.entity.FilmCategory;
import org.filmland.catalog.entity.User;
import org.filmland.catalog.repository.CategorySubscriptionRepo;
import org.filmland.catalog.repository.FilmCategoryRepo;
import org.filmland.catalog.repository.UserRepository;
import org.filmland.catalog.web.errors.ErrorCodes;
import org.filmland.catalog.web.errors.FilmLandFeatureException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class FilmCategoryServiceUnitTests {

    @Mock
    private FilmCategoryRepo filmCategoryRepo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategorySubscriptionRepo categorySubscriptionRepo;

    @InjectMocks
    FilmCategoryService filmCategoryService;

    User user = new User("test@user.com", "Password");
    FilmCategory filmCategory = new FilmCategory("Dutch Films", 10, "EUR", 1000);

    @Test
    public void test_NonExistingUser_getSubscribedCategories() {
        Mockito.when(userRepository.findById(Mockito.anyString())).
                thenReturn(Optional.empty());
        FilmLandFeatureException exception =
                Assert.assertThrows(FilmLandFeatureException.class, () -> filmCategoryService.getSubscribedCategories(user.getEmail()));
        Assert.assertEquals(ErrorCodes.OPERATION_NOT_PERMITTED, exception.getErrorCode());
    }

    @Test
    public void test_NonExistingUser_addSubscription() {
        Mockito.when(userRepository.findById(Mockito.anyString())).
                thenReturn(Optional.empty());
        FilmLandFeatureException exception =
                Assert.assertThrows(FilmLandFeatureException.class, () -> filmCategoryService.addSubscription(user.getEmail(), "testuser2@email.com", "Dutch Films"));
        Assert.assertEquals(ErrorCodes.OPERATION_NOT_PERMITTED, exception.getErrorCode());
    }

    @Test
    public void test_NonExistingCategory_addSubscription() {
        Mockito.when(userRepository.findById(Mockito.anyString())).
                thenReturn(Optional.of(user));
        Mockito.when(filmCategoryRepo.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

        FilmLandFeatureException exception =
                Assert.assertThrows(FilmLandFeatureException.class, () -> filmCategoryService.addSubscription(user.getEmail(), "testuser2@email.com", "Dutch Films"));
        Assert.assertEquals(ErrorCodes.CATEGORY_DOESNT_EXIST, exception.getErrorCode());
    }

    @Test
    public void test_NonExistingSubscription_addSubscription() {

        Mockito.when(userRepository.findById(Mockito.anyString())).
                thenReturn(Optional.of(user));
        Mockito.when(filmCategoryRepo.findById(Mockito.anyString()))
                .thenReturn(Optional.of(filmCategory));
        Mockito.when(categorySubscriptionRepo.findByUserAndFilmCategory(Mockito.any(), Mockito.any())).thenReturn(null);

        filmCategoryService.addSubscription(user.getEmail(), null, filmCategory.getName());
        Mockito.verify(categorySubscriptionRepo, times(1)).save(any());
    }
}