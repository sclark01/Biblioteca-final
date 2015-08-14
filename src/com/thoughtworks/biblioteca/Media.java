package com.thoughtworks.biblioteca;

public interface Media {
    public String getDetailsAsString();
    public Boolean isAvailable();
    public void checkOut();
    public void returnMedia();
    public Boolean isMedia(String s);
}
