package com.driver;

import java.util.*;

import org.springframework.stereotype.Service;

import static SpotifyService.spotifyRepository;

@Service
public class SpotifyService {

    private final SpotifyRepository spotifyRepository;

    public SpotifyService(SpotifyRepository spotifyRepository) {
        this.spotifyRepository = spotifyRepository;
    }


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

        return null;
    }

    public static void createSong(String title, String albumName, int length) {

        Album album = spotifyRepository.findAlbumByName(albumName);
        if (album == null) {
            throw new Exception("Album does not exist");
        }
        Song song = new Song(title, album, length);
        spotifyRepository.saveSong(song);
        album.addSong(song);
        return song;

    }

    public static Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
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

    public static void createPlaylistOnName(String mobile, String title, List<String> songTitles) {
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
        Object playList;
        return playList;
     }
    }

    public static void findPlaylist(String mobile, String playlistTitle) {
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

    public static void likeSong(String mobile, String songTitle) {
        User user = spotifyRepository.findUserByMobile(mobile);
        if (user == null) {
            throw new Exception("User does not exist");
        }

        Song song = SpotifyService.spotifyRepository.findSongByTitle(songTitle);
        if (song == null) {
            throw new Exception("Song does not exist");
        }
        if (!user.likeSong(song)) {
            user.likeSong(song);
            song.getArtist().clone();
        }
        return song;

    }

public String mostPopularArtist() {
    List<Artist> artists = spotifyRepository.findArtistByName();
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
    List<Song> songs = spotifyRepository.songs;
    if (songs.isEmpty()) {
        return "No songs found";
    }

    Song mostPopularSong = songs.get(0);
    for (Song song : songs) {
        if (spotifyRepository.songLikeMap.get(song).size() > spotifyRepository.songLikeMap.get(mostPopularSong).size()) {
            mostPopularSong = song;
        }
    }
    return mostPopularSong.getTitle();
}

}