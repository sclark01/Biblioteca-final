package com.thoughtworks.biblioteca;

/**
 * Created by sclarkti on 8/10/15.
 */
public class User {

    private String password;

    public User(String password) {
        this.password = password;
    }

    public boolean isCorrectPassword(String password) {
        if(password == this.password)
            return true;
        else
            return false;
    }
}
