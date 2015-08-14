package com.thoughtworks.biblioteca;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.*;


public class LibraryTest {

    private PrintStream printStream;
    private Library library;
    private List<Media> bookList;
    private Book harryPotter;
    private BufferedReader bufferedReader;
    private Menu menu;
    private Movie titanic;
    private List<Media> movieList;
    private User user;
    private Map<String, User> users;

    @Before
    public void setUp(){
        printStream = mock(PrintStream.class);
        bookList = new ArrayList<>();
        movieList = new ArrayList<>();
        bufferedReader = mock(BufferedReader.class);
        menu = mock(Menu.class);
        titanic = mock(Movie.class);
        harryPotter = mock(Book.class);
        user = mock(User.class);
        users = new HashMap<>();
        users.put("username", user);
        library = new Library(bufferedReader, printStream, bookList, menu, movieList, users);

    }

    @Test
    public void shouldPrintWelcomeMessageWhenStarting() throws IOException {
        when(bufferedReader.readLine()).thenReturn("0");
        library.start();
        verify(printStream).println(contains("Welcome"));
    }

    @Test
    public void shouldListExistingBooksInLibrary(){
        bookList.add(harryPotter);
        when(harryPotter.getDetailsAsString()).thenReturn("some string");
        when(harryPotter.isAvailable()).thenReturn(true);
        library.listBooks();
        verify(printStream).print(contains("some string"));
    }


    @Test
    public void shouldListMoviesWhenLibraryOpens() {
        movieList.add(titanic);
        when(titanic.isAvailable()).thenReturn(true);
        library.listMovies();
        verify(titanic).getDetailsAsString();
    }

    @Test
    public void shouldListExistingMoviesInLibrary() {
        movieList.add(titanic);
        when(titanic.getDetailsAsString()).thenReturn("some string");
        when(titanic.isAvailable()).thenReturn(true);
        library.listMovies();
        verify(printStream).print(contains("some string"));
    }

    @Test
    public void shouldListNothingWhenNoBooksInLibrary(){
        library.listBooks();
        verify(printStream).print(contains(""));
    }

    @Test
    public void libraryShouldNotPrintOutBookWhenBookIsCheckedOut() {
        when(harryPotter.isAvailable()).thenReturn(false);
        when(harryPotter.getDetailsAsString()).thenReturn("some string");
        bookList.add(harryPotter);
        library.listBooks();
        verify(printStream, never()).print(contains("some string"));
    }

    @Test
    public void shouldCheckOutCorrectBook() throws IOException {
        when(harryPotter.isAvailable()).thenReturn(true);
        when(harryPotter.getDetailsAsString()).thenReturn("some string");
        bookList.add(harryPotter);
        library.checkOutBook(0);
        verify(harryPotter).checkOut();
    }

    @Test
    public void shouldCheckOutCorrectMovie() {
        when(titanic.isAvailable()).thenReturn(true);
        when(titanic.getDetailsAsString()).thenReturn("some string");
        movieList.add(titanic);
        library.checkOutMovie(0);
        verify(titanic).checkOut();
    }

    @Test
    public void shouldReturnFalseWhenUserChoosesInvalidBookOptions(){
        assertFalse(library.checkOutBook(0));
    }

    @Test
    public void shouldReturnCorrectBook() {
        bookList.add(harryPotter);
        when(harryPotter.isMedia(anyString())).thenReturn(true);
        library.returnBook("Harry Potter");
        verify(harryPotter).returnMedia();
    }

    @Test
    public void libraryShouldNotPrintOutMovieWhenMovieIsNotAvailable() {
        when(titanic.isAvailable()).thenReturn(false);
        when(titanic.getDetailsAsString()).thenReturn("some string");
        movieList.add(titanic);
        library.listMovies();
        verify(printStream, never()).print(contains("some string"));
    }

    @Test
    public void shouldAskForUsernameAndPasswordWhenUserLogsIn() throws Exception {
        library.logInUser();
        verify(printStream).println(contains("Enter username:"));

    }

    @Test
    public void shouldReportMessageWhenUsernameIsNotValid() throws Exception {
        String invalid_username = "username1";
        when(bufferedReader.readLine()).thenReturn(invalid_username, "password");
        library.logInUser();
        verify(printStream).println(contains("Invalid username"));
    }

    @Test
    public void shouldNotReportMessageWhenUsernameIsValid() throws Exception {
        String valid_username = "username";
        when(bufferedReader.readLine()).thenReturn(valid_username, "password");
        library.logInUser();
        verify(printStream, never()).println(contains("Invalid username or password"));
    }

    @Test
    public void shouldReportMessageWhenPasswordIsInvalid() throws Exception {
        String anyUsername = "username";
        String invalid_password = "password1";
        when(bufferedReader.readLine()).thenReturn(anyUsername, invalid_password);
        when(user.isCorrectPassword(invalid_password)).thenReturn(false);
        library.logInUser();
        verify(printStream).println(contains("Invalid username or password"));
    }

    @Test
    public void shouldNotReportMessageWhenPasswordIsValid() throws Exception {
        String anyUsername = "username";
        String valid_password = "password";
        when(bufferedReader.readLine()).thenReturn(anyUsername, valid_password);
        when(user.isCorrectPassword(valid_password)).thenReturn(true);
        library.logInUser();
        verify(printStream, never()).println(contains("Invalid username or password"));
    }


}