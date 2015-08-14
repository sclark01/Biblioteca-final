package com.thoughtworks.biblioteca;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        final PrintStream printStream = new PrintStream(System.out);
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        List<Media> listOfBooks = new ArrayList<>();
        Book chronicles = new Book("Chronicles of Narnia", "CS Lewis", 1990);
        Book lordOfRings = new Book("Fellowship of the Rings", "JRR Tolkien", 1970);
        listOfBooks.add(chronicles);
        listOfBooks.add(lordOfRings);

        List<Media> listOfMovies = new ArrayList<>();
        Movie goodBurger = new Movie("Good Burger", "Brian Robbins", 1997, 3);
        listOfMovies.add(goodBurger);

        Map<String, User> users = new HashMap<>();
        User user = new User("password");
        users.put("123-4567", user);

        Library library = new Library(bufferedReader, printStream, listOfBooks, new Menu(printStream, bufferedReader), listOfMovies, users);
        library.start();
    }
}
