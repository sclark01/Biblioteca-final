package com.thoughtworks.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.*;

/**
 * Created by lfitzger on 8/6/15.
 */
public class MenuTest {

    private BufferedReader bufferedReader;
    private Library library;
    private PrintStream printStream;
    private List<Book> bookList;
    private Book harryPotter;
    private Menu menu;

    @Before
    public void setUp() throws Exception {
        bufferedReader = mock(BufferedReader.class);
        printStream = mock(PrintStream.class);
        library = mock(Library.class);
        bookList = new ArrayList<Book>();
        harryPotter = mock(Book.class);
        menu = new Menu(printStream, bufferedReader);

    }

    @Test
    public void shouldShowMenuOptionsWhenMenuIsDisplayed() {
        menu.showOptions();
        verify(printStream).println(contains("[1] Show all books"));
        verify(printStream).println(contains("[2] Check out book"));
        verify(printStream).println(contains("[3] Return Book"));
        verify(printStream).println(contains("[4] Show all movies"));
        verify(printStream).println(contains("[5] Check out movies"));

        verify(printStream).println(contains("[0] Quit"));
    }

    @Test
    public void shouldListBooksWhenMenuOptionOneIsSelected() throws IOException {
        when(bufferedReader.readLine()).thenReturn("1", "0");
        menu.respondToUserInput(library);
        verify(library).listBooks();
    }

    @Test
    public void shouldReportErrorWhenInvalidIntegerSelected() throws IOException {
        when(bufferedReader.readLine()).thenReturn("-1", "1", "0");
        menu.respondToUserInput(library);
        verify(printStream).println("Select a valid option!");
    }

    @Test
    public void shouldReportErrorMessageWhenInputIsNotInteger() throws IOException {
        when(bufferedReader.readLine()).thenReturn("not an integer", "1", "0");
        menu.respondToUserInput(library);
        verify(printStream).println(contains("Select a valid option!"));
    }

    @Test
    public void shouldBePromptedOnceWhenQuitIsFirstChoice() throws IOException {
        when(bufferedReader.readLine()).thenReturn("0", "1");
        menu.respondToUserInput(library);
        verify(bufferedReader).readLine();
    }

    @Test
    public void shouldPromptUserToPickBookWhenUserWantsToCheckOutBook() throws IOException {
        successfullyReturnBook();
        menu.respondToUserInput(library);
        verify(printStream).println("Which book would you like to check out?");
    }

    @Test
    public void shouldPromptUserToPickMovieWhenUserWantsToCheckOutMovie() throws IOException {
        when(bufferedReader.readLine()).thenReturn("5", "0");
        menu.respondToUserInput(library);
        verify(printStream).println("Which movie would you like to check out?");
    }

    @Test
    public void shouldListBooksWhenCustomerWantsToCheckOutABook() throws IOException {
        successfullyReturnBook();
        menu.respondToUserInput(library);
        verify(library).listBooks();
    }

    @Test
    public void shouldListMoviesWhenCustomerWantsToCheckOutAMovie() throws IOException {
        when(bufferedReader.readLine()).thenReturn("5", "0");
        menu.respondToUserInput(library);
        verify(library).listMovies();
    }

    @Test
    public void shouldCallCheckOutFromLibraryWithCorrectBook() throws IOException {
        successfullyReturnBook();
        menu.respondToUserInput(library);
        verify(library).checkOutBook(0);
    }

    @Test
    public void shouldCallCheckOutFromLibraryWithCorrectMovie() throws IOException {
        when(bufferedReader.readLine()).thenReturn("5", "1", "0");
        menu.respondToUserInput(library);
        verify(library).checkOutMovie(0);

    }

    @Test
    public void shouldReportErrorMessageWhenUserSelectsABookThatDoesNotExist() throws IOException {
        when(bufferedReader.readLine()).thenReturn("2", "100", "1", "0");
        when(library.checkOutBook(99)).thenReturn(false);
        when(library.checkOutBook(0)).thenReturn(true);
        bookList.add(harryPotter);
        menu.respondToUserInput(library);
        verify(printStream).println(contains("That book is not available."));
    }

    @Test
    public void shouldTellUserWhenTheyHaveSuccessfullyCheckedOutABook() throws IOException {
        successfullyReturnBook();
        menu.respondToUserInput(library);
        verify(printStream).println(contains("Thank you! Enjoy the book"));
    }

    @Test
    public void shouldPromptUserForBookTitleTheyWantToReturn() throws IOException {
        when(bufferedReader.readLine()).thenReturn("3", "SOME TITLE!", "0");
        when(library.returnBook(anyString())).thenReturn(true);
        menu.respondToUserInput(library);
        verify(printStream).println("Which book would you like to return?");
    }

    @Test
    public void shouldCallReturnToLibraryWithCorrectBook() throws IOException {
        when(bufferedReader.readLine()).thenReturn("3", "SOME BOOK TITLE!", "0");
        when(library.returnBook(anyString())).thenReturn(true);
        menu.respondToUserInput(library);
        verify(library).returnBook("SOME BOOK TITLE!");
    }

    @Test
    public void shouldThankUserWhenSuccessfulReturnOfBook() throws IOException {
        when(bufferedReader.readLine()).thenReturn("3", "1");
        when(library.returnBook(anyString())).thenReturn(true);
        menu.respondToUserInput(library);
        verify(printStream).println("Thank you for returning the book.");
    }

    @Test
    public void shouldPromptUserWhenFailedReturnBook() throws IOException {
        when(bufferedReader.readLine()).thenReturn("3", "1");
        when(library.returnBook(anyString())).thenReturn(false, true);
        menu.respondToUserInput(library);
        verify(printStream).println("That is not a valid book to return.");
    }

    @Test
    public void shouldAllowUserToReenterReturningBookTitle() throws IOException {
        when(bufferedReader.readLine()).thenReturn("3", "1");
        when(library.returnBook(anyString())).thenReturn(false, true);
        menu.respondToUserInput(library);
        verify(printStream).println("That is not a valid book to return.");
        verify(printStream).println("Thank you for returning the book.");
    }

    @Test
    public void shouldListMoviesWhenFourIsChosen() throws IOException {
        when(bufferedReader.readLine()).thenReturn("4", "0");
        menu.respondToUserInput(library);
        verify(library).listMovies();
    }

    private void successfullyReturnBook() throws IOException {
        when(bufferedReader.readLine()).thenReturn("2", "1", "0");
        when(library.checkOutBook(0)).thenReturn(true);
    }

    @Test
    public void shouldLogInUserWhenCustomerSelectsOption6() throws IOException {
        when(bufferedReader.readLine()).thenReturn("6", "0");
        menu.respondToUserInput(library);
        verify(library).logInUser();
    }

}