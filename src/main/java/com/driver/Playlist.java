package com.driver;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String title;
    private User creator;
    private List<Song> songs = new ArrayList<>();
    private List<User> listeners = new ArrayList<>();


    public Playlist() {
    }

    public Playlist(String title) {
        this.title = title;
    }

    public Playlist(String title, User user) {
        this.title = title;
        this.creator = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public boolean hasListener(User user) {
        return listeners.contains(user);
    }

    public void addListener(User user) {
        listeners.add(user);
    }

    public List<User> getListeners() {
        return listeners;
    }

    public User getCreator() {
        return creator;
    }
}