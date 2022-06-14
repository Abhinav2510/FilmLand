package org.filmland.catalog.repository;

import org.filmland.catalog.entity.CategorySubscriptions;
import org.filmland.catalog.entity.FilmCategory;
import org.filmland.catalog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * {@link CategorySubscriptionRepo} simple JPA repo for accessing {@link CategorySubscriptions}
 */
@Repository
public interface CategorySubscriptionRepo extends JpaRepository<CategorySubscriptions, Long> {

    @Query("SELECT c FROM CategorySubscriptions c WHERE c.filmCategory=?2 AND c.user=?1")
    CategorySubscriptions findByUserAndFilmCategory(User user, FilmCategory category);

    @Query("SELECT c FROM CategorySubscriptions c WHERE c.user=?1 or c.sharedWith=?1")
    List<CategorySubscriptions> findBydOwnerOrSharedUser(User user);

}
