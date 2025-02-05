package com.driver;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class SpotifyService {

    private final SpotifyRepository spotifyRepository;

    public SpotifyService(SpotifyRepository spotifyRepository) {
        this.spotifyRepository = spotifyRepository;
    }

    public User createUser(String name, String mobile) {
        User user = new User(name, mobile);
        spotifyRepository.saveUser(user);
        return user;
    }

    public Artist createArtist(String name) {
        Artist artist = new Artist(name);
        spotifyRepository.saveArtist(artist);
        return artist;
    }

    public Album createAlbum(String title, String artistName) {
        Artist artist = spotifyRepository.findArtistByName(artistName);
        if (artist == null) {
            throw new RuntimeException("Artist does not exist");
        }
        Album album = new Album(title, artist);
        spotifyRepository.saveAlbum(album);
        artist.addAlbum(album);
        return album;
    }

    public Song createSong(String title, String albumName, int length) throws Exception {
        Album album = spotifyRepository.findAlbumByName(albumName);
        if (album == null) {
            throw new Exception("Album does not exist");
        }
        Song song = new Song(title, album, length);
        spotifyRepository.saveSong(song);
        album.addSong(song);
        return song;
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
        User user = spotifyRepository.findUserByMobile(mobile);
        if (user == null) {
            throw new Exception("User does not exist");
        }
        Playlist playlist = new Playlist(title, user);
        for (Song song : spotifyRepository.findAllSongs()) {
            if (song.getLength() == length) {
                playlist.addSong(song);
            }
        }
        spotifyRepository.savePlaylist(playlist);
        user.addPlaylist(playlist);
        return playlist;
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
        User user = spotifyRepository.findUserByMobile(mobile);
        if (user == null) {
            throw new Exception("User does not exist");
        }
        Playlist playlist = new Playlist(title, user);
        for (String songTitle : songTitles) {
            Song song = spotifyRepository.findSongByTitle(songTitle);
            if (song != null) {
                playlist.addSong(song);
            }
        }
        spotifyRepository.savePlaylist(playlist);
        user.addPlaylist(playlist);
        return playlist;
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) {
        User user = spotifyRepository.findUserByMobile(mobile);
        if (user == null) {
            throw new RuntimeException("User does not exist");
        }
        Playlist playlist = spotifyRepository.findPlaylistByTitle(playlistTitle);
        if (playlist == null) {
            throw new RuntimeException("Playlist does not exist");
        }
        if (!playlist.hasListener(user)) {
            playlist.addListener(user);
            user.addPlaylist(playlist);
        }
        return playlist;
    }

    public Song likeSong(String mobile, String songTitle) {
        User user = spotifyRepository.findUserByMobile(mobile);
        if (user == null) {
            throw new RuntimeException("User does not exist");
        }
        Song song = spotifyRepository.findSongByTitle(songTitle);
        if (song == null) {
            throw new RuntimeException("Song does not exist");
        }
        if (!user.likeSong(song)) {
            user.likeSong(song);
            song.getArtist().addLike();
        }
        return song;
    }

    public String mostPopularArtist() {
        List<Artist> artists = spotifyRepository.findAllArtists();
        if (artists.isEmpty()) {
            return "No artists found";
        }
        Artist mostPopularArtist = artists.get(0);
        for (Artist artist : artists) {
            if (artist.getLikes() > mostPopularArtist.getLikes()) {
                mostPopularArtist = artist;
            }
        }
        return mostPopularArtist.getName();
    }

    public String mostPopularSong() {
        List<Song> songs = spotifyRepository.findAllSongs();
        if (songs.isEmpty()) {
            return "No songs found";
        }
        Song mostPopularSong = songs.get(0);
        for (Song song : songs) {
            if (spotifyRepository.getSongLikes(song).size() > spotifyRepository.getSongLikes(mostPopularSong).size()) {
                mostPopularSong = song;
            }
        }
        return mostPopularSong.getTitle();
    }
}