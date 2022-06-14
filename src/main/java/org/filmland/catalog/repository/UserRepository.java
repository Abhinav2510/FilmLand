package org.filmland.catalog.repository;

import org.filmland.catalog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link UserRepository} simple JPA repo to access {@link User}
 */
@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
