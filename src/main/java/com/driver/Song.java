package com.driver;

import java.util.List;

public class Song {
    private String title;
    private int length;
    private int likes;
    private Album album;
    private String albumName;

    public Song() {
    }

    public Song(String title, int length) {
        this.title = title;
        this.length = length;
    }

    public Song(String title, Album album, int length) {
        this.title = title;
        this.album = album;
        this.length = length;
    }

    public Song(String title, String albumName, int length) {
        this.title = title;
        this.albumName = albumName;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Album getArtist() {
        return album;
    }

    public List<User> getLikers() {
        return null; // Implement the logic to return the list of users who liked this song
    }

    public void addLiker(User user) {
        this.likes++;
    }

    public String getArtistName() {
        return albumName;
    }

    public int getLikeCount() {
        return likes;
    }

    public void add(Song song) {
        // Implement the logic to add a song
    }
}