

package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;

    public SpotifyRepository(){
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public HashMap<Artist, List<Album>> getArtistAlbumMap() {
        return artistAlbumMap;
    }

    public void setArtistAlbumMap(HashMap<Artist, List<Album>> artistAlbumMap) {
        this.artistAlbumMap = artistAlbumMap;
    }

    public HashMap<Album, List<Song>> getAlbumSongMap() {
        return albumSongMap;
    }

    public void setAlbumSongMap(HashMap<Album, List<Song>> albumSongMap) {
        this.albumSongMap = albumSongMap;
    }

    public HashMap<Playlist, List<Song>> getPlaylistSongMap() {
        return playlistSongMap;
    }

    public void setPlaylistSongMap(HashMap<Playlist, List<Song>> playlistSongMap) {
        this.playlistSongMap = playlistSongMap;
    }

    public HashMap<Playlist, List<User>> getPlaylistListenerMap() {
        return playlistListenerMap;
    }

    public void setPlaylistListenerMap(HashMap<Playlist, List<User>> playlistListenerMap) {
        this.playlistListenerMap = playlistListenerMap;
    }

    public HashMap<User, Playlist> getCreatorPlaylistMap() {
        return creatorPlaylistMap;
    }

    public void setCreatorPlaylistMap(HashMap<User, Playlist> creatorPlaylistMap) {
        this.creatorPlaylistMap = creatorPlaylistMap;
    }

    public HashMap<User, List<Playlist>> getUserPlaylistMap() {
        return userPlaylistMap;
    }

    public void setUserPlaylistMap(HashMap<User, List<Playlist>> userPlaylistMap) {
        this.userPlaylistMap = userPlaylistMap;
    }

    public HashMap<Song, List<User>> getSongLikeMap() {
        return songLikeMap;
    }

    public void setSongLikeMap(HashMap<Song, List<User>> songLikeMap) {
        this.songLikeMap = songLikeMap;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public User createUser(String name, String mobile) {

    }

    public Artist createArtist(String name) {

    }

    public Album createAlbum(String title, String artistName) {
    }

    public Song createSong(String title, String albumName, int length) throws Exception{
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {

    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {

    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {

    }

    public Song likeSong(String mobile, String songTitle) throws Exception {

    }


}

