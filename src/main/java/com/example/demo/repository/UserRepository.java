package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Finds a user by email.
     *
     * @param email the email to search for
     * @return an optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by username.
     *
     * @param username the username to search for
     * @return an optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user exists by email.
     *
     * @param email the email to check
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a user exists by username.
     *
     * @param username the username to check
     * @return true if exists, false otherwise
     */
    boolean existsByUsername(String username);
}
