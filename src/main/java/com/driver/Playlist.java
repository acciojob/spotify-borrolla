package com.driver;

import java.util.List;

public class Playlist {
    private String title;

    public Playlist(){

    }

    public Playlist(String title){
        this.title = title;
    }

    public Playlist(String title, User user) {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void addSong(Song song) {
    }

    public boolean hasListener(User user) {
    }

    public void addListener(User user) {
    }

    public String getListeners() {
    }

    public Object getCreator() {
    }
}
