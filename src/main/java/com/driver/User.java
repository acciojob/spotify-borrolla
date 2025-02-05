package com.driver;

import java.util.List;

public class User {
    private String name;
    private String mobile;
    private List<Playlist> playlists;
    private List<Song> likedSongs;

    public User(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public User(String name, String mobile, List<Playlist> playlists) {
        this.name = name;
        this.mobile = mobile;
        this.playlists = playlists;
    }

    public User(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public boolean hasLikedSong(Song song) {
        return likedSongs.contains(song);
    }

    public boolean likeSong(Song song) {
        if (!likedSongs.contains(song)) {
            likedSongs.add(song);
        }
        return false;
    }
}