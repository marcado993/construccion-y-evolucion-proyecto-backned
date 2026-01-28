package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for API endpoints.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

    private final UserRepository userRepository;

    public ApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns a hello message.
     *
     * @return a greeting string
     */
    @GetMapping("/hello")
    public String hello() {
        return "¡Hola desde Spring Boot conectado a Supabase! 🔥";
    }

    /**
     * Test endpoint to verify backend connection.
     *
     * @return a response entity with status information
     */
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok()
            .body(java.util.Map.of(
                "status", "ok",
                "message", "Backend conectado correctamente",
                "timestamp", java.time.LocalDateTime.now().toString()
            ));
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the user ID
     * @return a response entity containing the user or not found
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return a response entity with the created user or conflict if email exists
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Validar que el email no exista
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * Updates an existing user.
     *
     * @param id the user ID
     * @param userDetails the updated user details
     * @return a response entity with the updated user or not found
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        User user = userOptional.get();
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(userDetails.getPassword());
        }
        
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the user ID
     * @return a response entity indicating success or not found
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a user by email.
     *
     * @param email the user email
     * @return a response entity containing the user or not found
     */
    @GetMapping("/users/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
}
