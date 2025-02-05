package com.driver;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class SpotifyService {

    //Auto-wire will not work in this case, no need to change this and add autowire

    static SpotifyRepository spotifyRepository = new SpotifyRepository();

    public static User createUser(String name, String mobile){
           User user = new User(name,mobile);
           spotifyRepository.saveUser(user);
           return user;
    }

    public static Artist createArtist(String name) {
         Artist artist = new Artist(name);
         spotifyRepository.saveArtist(artist);
         return artist;
    }

    public static Album createAlbum(String title, String artistName) {
        {
            Artist artist = spotifyRepository.findArtistByName(artistName);
            if (artist == null) {
                artist = createArtist(artistName);
            }
            Album album = new Album(title, artist);
            spotifyRepository.saveAlbum(album);
            return album;
        }

    public Song createSong(String title, String String albumName;
        albumName, int length) throws Exception {
        Album album = spotifyRepository.findAlbumByName(albumName);
        if (album == null) {
            throw new Exception("Album does not exist");
        }
        Song song = new Song(title,album,length);
        spotifyRepository.saveSong(song);
        album.addSong(song);
        return song;
    }

    public Playlist createPlaylistOnLength(String Object mobile;
        mobile, String title, int length) throws Exception {
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
            {
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
                return playList;
            }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
                User user = spotifyRepository.findUserByMobile(mobile);
                if (user == null) {
                    throw new Exception("User does not exist");
                }
                Playlist playlist = spotifyRepository.findPlaylistByTitle(playlistTitle);
                if (playlist == null) {
                    throw new Exception("Playlist does not exist");
                }
                if (!playlist.hasListener(user)) {
                    playlist.addListener(user);
                    user.addPlaylist(playlist);
                }
                return playlist;
    }

    public Song likeSong(String mobile, String songTitle) throws Exception {
                User user = spotifyRepository.findUserByMobile(mobile);
                if (user == null) {
                    throw new Exception("User does not exist");
                }
                Song song = spotifyRepository.findSongByTitle(songTitle);
                if (song == null) {
                    throw new Exception("Song does not exist");
                }
                if (!user.hasLikedSong(song)) {
                    user.likeSong(song);
                    song.getArtist().addLike();
                }
                return song;
    }

    public String mostPopularArtist() {
                return spotifyRepository.findAllArtists().getClass()
                        .max(Comparator.comparing(Artist::getLikes))
                        .map(Artist::getName)
                        .orElse("No artists found");
    }

    public String mostPopularSong() {
                return spotifyRepository.findAllSongs().getClass()
                        .max(Comparator.comparing(Song::getLikes))
                        .map(Song::getTitle)
                        .orElse("No songs found");
            }

    }
}

    public static void createSong(String title, String albumName, int length) {
    }

    public static void createPlaylistOnLength(String mobile, String title, int length) {
    }

    public static void createPlaylistOnName(String mobile, String title, List<String> songTitles) {
    }

    public static void findPlaylist(String mobile, String playlistTitle) {
    }

    public static void likeSong(String mobile, String songTitle) {
    }

