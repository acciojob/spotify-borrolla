package com.driver;

import java.util.Date;
import java.util.List;

public class Album {
    private String title;
    private Date releaseDate;

    public Album(){

    }

    public Album(String title){
        this.title = title;
        this.releaseDate = new Date();
    }

    public Album(String title, Artist artist) {

    }

    public Album(String title, String artistName) {
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

    }

}
