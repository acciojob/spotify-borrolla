package com.driver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Album {
    private String title;
    private Date releaseDate;
    private Artist artist;
    private String artistName;
    private List<Song> songs = new ArrayList<>();
    private int likeCount = 0;


    public Album() {
    }

    public Album(String title) {
        this.title = title;
        this.releaseDate = new Date();
    }

    public Album(String title, Artist artist) {
        this.title = title;
        this.artist = artist;
    }

    public Album(String title, String artistName) {
        this.title = title;
        this.artistName = artistName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void addSong(Song song) {
        // Implement the logic to add a song to the album
     this.songs.add(song);
    }

    public void addLike() {
        // Implement the logic to add a like
        this.likeCount++;
        
    }
}