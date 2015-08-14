package com.thoughtworks.biblioteca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class Menu {

    private PrintStream printStream;
    private BufferedReader bufferedReader;
    private Library library;

    public Menu(PrintStream printStream, BufferedReader bufferedReader) {
        this.printStream = printStream;
        this.bufferedReader = bufferedReader;
    }

    public void showOptions() {
        printStream.println("Menu");
        printStream.println("[1] Show all books");
        printStream.println("[2] Check out book");
        printStream.println("[3] Return Book");
        printStream.println("[4] Show all movies");
        printStream.println("[5] Check out movies");
        printStream.println("[0] Quit to close the library");
    }

    public Boolean respondToUserInput(Library library) {
        this.library = library;
        showOptions();
        return (applySelectedMenuOption(readAndValidateUserIntegerInput("Menu")) != 0);
    }

    private int applySelectedMenuOption(Integer input) {
        switch (input) {
            case 0:
                break;
            case 1:
                library.listBooks();
                break;
            case 2:
                collectCheckOutOption();
                break;
            case 3:
                collectReturnInformation();
                break;
            case 4:
                library.listMovies();
                break;
            case 5:
                collectCheckOutMovieOption();
                break;
            case 6:
                library.logInUser();
                break;
            default:
                printStream.println("Select a valid option!");
        }
        return input;
    }

    private void collectCheckOutMovieOption() {
        library.listMovies();
        printStream.println("Which movie would you like to check out?");
        library.checkOutMovie(readAndValidateUserIntegerInput("Movie") - 1);
    }

    private void collectCheckOutOption() {
        Boolean continueCheckingOut = true;
        while(continueCheckingOut) {
            printStream.println("Which book would you like to check out?");
            library.listBooks();
            continueCheckingOut = !library.checkOutBook(readAndValidateUserIntegerInput("Book") - 1);
            if(continueCheckingOut){
                printStream.println("That book is not available.");
            }
        }
        printStream.println("Thank you! Enjoy the book");
    }

    private void collectReturnInformation() {
        Boolean continueReturning = true;
        while (continueReturning) {
            printStream.println("Which book would you like to return?");
            if (library.returnBook(readTitle())) {
                printStream.println("Thank you for returning the book.");
                continueReturning = false;
            } else {
                printStream.println("That is not a valid book to return.");
            }
        }
    }

    private String readTitle() {
        String bookTitle = "";
        try {
            bookTitle = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookTitle;
    }

    private Integer readAndValidateUserIntegerInput(String menuType) {
        Integer input = -1;
        try {
            printStream.print(menuType + " Selection: ");
            input = Integer.parseInt(bufferedReader.readLine());
        } catch (Exception e) {}

        return input;
    }
}