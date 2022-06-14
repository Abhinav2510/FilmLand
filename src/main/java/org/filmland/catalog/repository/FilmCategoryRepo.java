package org.filmland.catalog.repository;

import org.filmland.catalog.entity.FilmCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link FilmCategoryRepo} simple JPA repo to access {@link FilmCategory}
 */
@Repository
public interface FilmCategoryRepo extends JpaRepository<FilmCategory,String > {
}
