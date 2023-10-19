package edu.uiuc.cs427app;

import java.util.Arrays;
import java.util.List;

/**
User class that will be used to store: username, password, favoredCities and theme.
 */
public class User {

    String username;
    String password;
    List<String> favoredCities = Arrays.asList("LA", "Dallas");
    String theme = "default";

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, List<String> favoredCities, String theme) {
        this.username = username;
        this.password = password;
        this.favoredCities = favoredCities;
        this.theme = theme;
    }

    public User(long id, String username, String email) {
        new User(username, username);
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", favoredCities=" + favoredCities +
                ", theme='" + theme + '\'' +
                '}';
    }

    public static void main(String[] args) {

    }

    public String getUsername() {
        return this.username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public List<String> getFavoredCities() {
        return favoredCities;
    }


    public void setFavoredCities(List<String> favoredCities) {
        this.favoredCities = favoredCities;
    }


    public String getTheme() {
        return theme;
    }


    public void setTheme(String theme) {
        this.theme = theme;
    }
}
