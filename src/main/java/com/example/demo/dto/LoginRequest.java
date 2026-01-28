package com.example.demo.dto;

/**
 * DTO for login requests.
 */
public class LoginRequest {
    private String email;
    private String password;

    /**
     * Default constructor.
     */
    public LoginRequest() {
    }

    /**
     * Constructor with email and password.
     *
     * @param email the email
     * @param password the password
     */
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
