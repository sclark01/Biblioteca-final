package com.thoughtworks.biblioteca;

/**
 * Created by sleblanc on 8/9/15.
 */
public class Movie implements Media {
    private final String title;
    private final String director;
    private final int year;
    private final int rating;
    private Boolean isAvailable;

    public Movie(String title, String director, int year, int rating) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.isAvailable = true;
    }

    public String getDetailsAsString() { return String.format("%-30.30s | %-30.30s | %d | %d", title, director, year, rating); }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void checkOut() {
        isAvailable = false;
    }

    @Override
    public void returnMedia() {

    }

    @Override
    public Boolean isMedia(String s) {
        return false;
    }
}
