package com.thoughtworks.biblioteca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class Library {
    private final PrintStream printStream;
    private BufferedReader bufferedReader;
    private List<Media> bookList;
    private List<Media> movieList;
    private Menu menu;
    private Map<String, User> users;


    public Library(BufferedReader bufferedReader, PrintStream printStream, List<Media> listOfBooks, Menu menu, List<Media> listOfMovies, Map<String, User> users){
        this.bufferedReader = bufferedReader;
        bookList = listOfBooks;
        movieList = listOfMovies;
        this.printStream = printStream;
        this.menu = menu;
        this.users = users;
    }

    public void start() {
        printStream.println("Welcome to the Biblioteca Library!");
        boolean stayOpen = menu.respondToUserInput(this);
        while(stayOpen){
            stayOpen = menu.respondToUserInput(this);
        }
    }

    public void listMedia(List<Media> mediaList) {
        String output = "";
        int index = 1;
        for (Media media : mediaList) {
            if(media.isAvailable()) {
                output += index++ + " " + media.getDetailsAsString() + "\n";
            }
        }
        printStream.print(output);
    }

    public void listBooks() {
        listMedia(bookList);
    }

    public void listMovies() {
        listMedia(movieList);
    }

    public Boolean checkOut(int i, List<Media> list) {
        if (i < 0 || i > list.size() - 1) return false;
        list.get(i).checkOut();
        return true;
    }

    public Boolean returnBook(String title) {
        for (Media book: bookList) {
            if (book.isMedia(title)) {
                book.returnMedia();
                return true;
            }
        }
        return false;
    }


    public Boolean checkOutMovie(int movie) {
        return checkOut(movie, movieList);
    }

    public Boolean checkOutBook(int book) {
        return checkOut(book, bookList);
    }

    public void logInUser() {
        printStream.println("Enter username: ");
        String username = readInput();
        printStream.println("Enter password: ");
        String password = readInput();
        if(validUsernameAndPassword(username, password)) {
            printStream.println(String.format("%s Logged in!", username));
        }
        else {
            printStream.println("Invalid username or password. Try again.");
        }
    }

    private boolean validUsernameAndPassword(String username, String password) {
        if(users.containsKey(username) && users.get(username).isCorrectPassword(password)) {
            return true;
        }
        return false;
    }

    private String readInput() {
        String input = "";
        try {
            input = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }
}
