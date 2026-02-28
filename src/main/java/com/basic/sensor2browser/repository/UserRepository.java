package com.basic.sensor2browser.repository;

import com.basic.sensor2browser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for managing User entities and lookups by email.
 */
public interface UserRepository extends JpaRepository <User, Integer> {

    Optional<User> findByEmail(String email);
}
