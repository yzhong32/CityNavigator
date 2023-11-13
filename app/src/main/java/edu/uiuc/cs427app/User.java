package edu.uiuc.cs427app;

import java.util.Arrays;
import java.util.List;

/**
User class that will be used to store: username, password, favoredCities and theme.
 */
public class User {
    // User attributes
    String username;
    String password;
    List<String> favoredCities = Arrays.asList("LA", "Dallas");
    String theme = "default";

    /**
     * Constructor with username and password.
     *
     * @param username The username of the user.
     * @param password The password associated with the username.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor with username, password, favored cities, and theme.
     *
     * @param username      The username of the user.
     * @param password      The password associated with the username.
     * @param favoredCities The list of favored cities.
     * @param theme         The theme preference of the user.
     */
    public User(String username, String password, List<String> favoredCities, String theme) {
        this.username = username;
        this.password = password;
        this.favoredCities = favoredCities;
        this.theme = theme;
    }

    // constructor with id, username, and email
    public User(long id, String username, String email) {
        new User(username, username);
    }

    // default constructor
    public User() {
    }

    /**
     * Override of the toString method for debugging and logging purposes.
     *
     * @return A string representation of the User object.
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", favoredCities=" + favoredCities +
                ", theme='" + theme + '\'' +
                '}';
    }


    // getter for username
    public String getUsername() {
        return this.username;
    }


    // setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // getter for password
    public String getPassword() {
        return password;
    }

    // setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // getter for cities
    public List<String> getFavoredCities() {
        return favoredCities;
    }

    // setter for cities
    public void setFavoredCities(List<String> favoredCities) {
        this.favoredCities = favoredCities;
    }

    // getter for theme
    public String getTheme() {
        return theme;
    }

    // setter for theme
    public void setTheme(String theme) {
        this.theme = theme;
    }
}
