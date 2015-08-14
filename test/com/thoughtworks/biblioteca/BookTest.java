package com.thoughtworks.biblioteca;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {
    private Book book;

    @Before
    public void setUp() {
        book = new Book("Title 1", "Author 1", 1);
    }

    @Test
    public void shouldConvertDetailsToString() {
        assertEquals(String.format("%-30.30s | %-30.30s | %d", "Title 1", "Author 1", 1), book.getDetailsAsString());
    }

    @Test
    public void shouldBecomeUnavailableWhenCheckedOut() {
        book.checkOut();
        assertFalse(book.isAvailable());
    }

    @Test
    public void shouldReturnTrueWhenHasCorrectTitle() {
        assertTrue(book.isMedia("Title 1"));
    }

    @Test
    public void shouldBecomeAvailableWhenReturned() {
        book.checkOut();
        book.returnMedia();
        assertTrue(book.isAvailable());
    }
}