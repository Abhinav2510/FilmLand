package org.filmland.catalog.service;

import lombok.RequiredArgsConstructor;
import org.filmland.catalog.entity.CategorySubscriptions;
import org.filmland.catalog.entity.FilmCategory;
import org.filmland.catalog.entity.User;
import org.filmland.catalog.repository.CategorySubscriptionRepo;
import org.filmland.catalog.repository.FilmCategoryRepo;
import org.filmland.catalog.repository.UserRepository;
import org.filmland.catalog.web.errors.ErrorCodes;
import org.filmland.catalog.web.errors.FilmLandFeatureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * {@link FilmCategoryService} service layer holding business logic
 */
@Service
@RequiredArgsConstructor
public class FilmCategoryService {

    private final FilmCategoryRepo filmCategoryRepo;

    private final UserRepository userRepository;

    private final CategorySubscriptionRepo categorySubscriptionRepo;

    /**
     * Paginated list of all categories available
     * @param page page number
     * @param size number of categories in one page
     * @return list of categories in page
     */
    public List<FilmCategory> getAllFilmCategories(int page, int size) {
        return Collections.unmodifiableList(filmCategoryRepo.findAll(PageRequest.of(page, size)).getContent());
    }

    /**
     * List of all the subscriptions user has either subscribed himself or shared by someone else
     * @param email email of user
     * @return list of all subscribed and shared categories for given email
     */
    public List<CategorySubscriptions> getSubscribedCategories(String email) {
        User user = userRepository.findById(email).orElseThrow(() -> new FilmLandFeatureException(ErrorCodes.OPERATION_NOT_PERMITTED));
        return Collections.unmodifiableList(categorySubscriptionRepo.findBydOwnerOrSharedUser(user));
    }

    /**
     * Adds subscriptions for users
     * case 1: User not subscribed Shared with Not subscribed then create a new subscription
     * case 2: User  subscribed Shared with Not subscribed then update existing subscription
     * case 3: User  subscribed Shared  subscribed then throw exception
     * case 4: User subscribed Shared is null then throw exception
     * @param primaryUserEmail email of primary subscriber
     * @param userSharedWithEmail email of user with whom the subscription should be shared
     * @param categoryToSubscribe category to which subscription should be added
     */
    public void addSubscription(String primaryUserEmail, String userSharedWithEmail, String categoryToSubscribe) {
        User user = userRepository.findById(primaryUserEmail).orElseThrow(() -> new FilmLandFeatureException(ErrorCodes.OPERATION_NOT_PERMITTED));
        User sharedWithUser = null;

        if (userSharedWithEmail != null) {
            sharedWithUser = userRepository.findById(userSharedWithEmail).orElseThrow(() -> new FilmLandFeatureException(ErrorCodes.OPERATION_NOT_PERMITTED, "Customer you want to share subscription with does not exist"));

        }
        FilmCategory filmCategory = filmCategoryRepo.findById(categoryToSubscribe).orElseThrow(() -> new FilmLandFeatureException(ErrorCodes.CATEGORY_DOESNT_EXIST));
        CategorySubscriptions alreadySubscribed = categorySubscriptionRepo.findByUserAndFilmCategory(user, filmCategory);

        if (alreadySubscribed != null) {
            if (userSharedWithEmail == null) {
                throw new FilmLandFeatureException(ErrorCodes.ALREADY_SUBSCRIBED);
            }

            if (alreadySubscribed.getSharedWith() != null && userSharedWithEmail.equalsIgnoreCase(alreadySubscribed.getSharedWith().getEmail())) {
                throw new FilmLandFeatureException(ErrorCodes.ALREADY_SUBSCRIBED, "You are already sharing subscription with user");
            }
            alreadySubscribed.setSharedWith(sharedWithUser);
            categorySubscriptionRepo.save(alreadySubscribed);
            return;
        }

        CategorySubscriptions subscription = CategorySubscriptions.builder()
                .filmCategory(filmCategory)
                .user(user)
                .sharedWith(sharedWithUser)
                .subscribedOn(LocalDate.now())
                .lastBillingDateTime(null)
                .build();

        categorySubscriptionRepo.save(subscription);

    }
}
