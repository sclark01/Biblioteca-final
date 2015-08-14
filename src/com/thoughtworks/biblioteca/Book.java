package com.thoughtworks.biblioteca;


public class Book implements Media {
    private String title;
    private String author;
    private int year;
    private boolean availability;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
        availability = true;
    }

    public String getDetailsAsString() {
        return String.format("%-30.30s | %-30.30s | %d", title, author, year);
    }

    public void checkOut() {
        availability = false;
    }

    public Boolean isAvailable() {
        return availability;
    }

    public void returnMedia() { availability = true; }

    public Boolean isMedia(String title) {
        return this.title.equals(title);
    }
}
