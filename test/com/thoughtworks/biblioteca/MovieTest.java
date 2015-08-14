package com.thoughtworks.biblioteca;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sleblanc on 8/9/15.
 */
public class MovieTest {

    private Movie movie;

    @Before
    public void setUp() {
        movie = new Movie("Title", "Director", 2015, 10);
    }

    @Test
    public void shouldConvertDetailsToString() {
        assertEquals(String.format("%-30.30s | %-30.30s | %d | %d", "Title", "Director", 2015, 10), movie.getDetailsAsString());
    }

    @Test
    public void shouldMarkMovieUnavailableWhenCheckingItOut(){
        movie.checkOut();
        assertFalse(movie.isAvailable());
    }

    @Test
    public void shouldReturnTrueWhenMovieIsAvailable() {
        assertTrue(movie.isAvailable());
    }

}