package com.thoughtworks.biblioteca;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class UserTest {

    private final String VALID_PASSWORD = "password";
    private User user;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void should() throws Exception {
        user = new User(VALID_PASSWORD);
        assertTrue(user.isCorrectPassword(VALID_PASSWORD));
    }
}