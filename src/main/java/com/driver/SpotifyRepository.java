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

    public SpotifyRepository() {
        // To avoid hitting APIs multiple times, initialize all the hashmaps here with some dummy data
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

    public User createUser(String name, String mobile) {
        User user = new User(name, mobile);
        users.add(user);
        return user;
    }

    public Artist createArtist(String name) {
        Artist artist = new Artist(name);
        artists.add(artist);
        artistAlbumMap.put(artist, new ArrayList<>());
        return artist;
    }

    public Album createAlbum(String title, String artistName) {
        Artist artist = findArtistByName(artistName);
        if (artist == null) {
            artist = createArtist(artistName);
        }
        Album album = new Album(title);
        albums.add(album);
        artistAlbumMap.get(artist).add(album);
        albumSongMap.put(album, new ArrayList<>());
        return album;
    }

   public Artist findArtistByName(String artistName) {
        for (Artist artist : artists) {
            if (artist.getName().equalsIgnoreCase(artistName)) {
                return artist;
            }
        }
        return null;
    }

    public Song createSong(String title, String albumName, int length) throws Exception {
        Album album = findAlbumByName(albumName);
        if (album == null) {
            throw new Exception("Album does not exist");
        }
        Song song = new Song(title, length);
        songs.add(song);
        albumSongMap.get(album).add(song);
        songLikeMap.put(song, new ArrayList<>());
        return song;
    }

    Album findAlbumByName(String albumName) {
        for (Album album : albums) {
            if (album.getTitle().equalsIgnoreCase(albumName)) {
                return album;
            }
        }
        return null;
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
        User user = findUserByMobile(mobile);
        if (user == null) {
            throw new Exception("User does not exist");
        }
        Playlist playlist = new Playlist(title);
        playlists.add(playlist);
        List<Song> songsInPlayList = new ArrayList<>();
        for (Song song : songs) {
            if (song.getLength() == length) {
                songsInPlayList.add(song);
            }
        }
        playlistSongMap.put(playlist, songsInPlayList);
        playlistListenerMap.put(playlist, new ArrayList<>());
        creatorPlaylistMap.put(user, playlist);
        userPlaylistMap.computeIfAbsent(user, k -> new ArrayList<>()).add(playlist);
        return playlist;
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
        User user = findUserByMobile(mobile);
        if (user == null) {
            throw new Exception("User does not exist");
        }
        Playlist playlist = new Playlist(title);
        playlists.add(playlist);
        List<Song> songsInPlayList = new ArrayList<>();
        for (String songTitle : songTitles) {
            Song song = findSongByTitle(songTitle);
            if (song != null) {
                songsInPlayList.add(song);
            }
        }
        playlistSongMap.put(playlist, songsInPlayList);
        playlistListenerMap.put(playlist, new ArrayList<>());
        creatorPlaylistMap.put(user, playlist);
        userPlaylistMap.computeIfAbsent(user, k -> new ArrayList<>()).add(playlist);
        return playlist;
    }

   public Song findSongByTitle(String songTitle) {
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(songTitle)) {
                return song;
            }
        }
        return null;
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
        User user = findUserByMobile(mobile);
        if (user == null) {
            throw new Exception("User does not exist");
        }
        Playlist playlist = findPlaylistByTitle(playlistTitle);
        if (playlist == null) {
            throw new Exception("Playlist does not exist");
        }
        if (!playlistListenerMap.get(playlist).contains(user)) {
            playlistListenerMap.get(playlist).add(user);
        }
        return playlist;
    }

    public Song likeSong(String mobile, String songTitle) throws Exception {
        User user = findUserByMobile(mobile);
        if (user == null) {
            throw new Exception("User does not exist");
        }
        Song song = findSongByTitle(songTitle);
        if (song == null) {
            throw new Exception("Song does not exist");
        }
        if (!songLikeMap.get(song).contains(user)) {
            songLikeMap.get(song).add(user);
            Artist artist = findArtistBySong(song);
            if (artist != null) {
                artist.setLikes(artist.getLikes() + 1);
            }
        }
        return song;
    }

    public void saveSong(Song song) {
        songs.add(song);
    }

    public User findUserByMobile(String mobile) {
        for (User user : users) {
            if (user.getMobile().equals(mobile)) {
                return user;
            }
        }
        return null;
    }

    public List<Song> findAllSongs() {
        return songs;
    }

    public void savePlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public Playlist findPlaylistByTitle(String playlistTitle) {
        for (Playlist playlist : playlists) {
            if (playlist.getTitle().equalsIgnoreCase(playlistTitle)) {
                return playlist;
            }
        }
        return null;
    }

    private Artist findArtistBySong(Song song) {
        for (Album album : albums) {
            if (albumSongMap.get(album).contains(song)) {
                for (Artist artist : artists) {
                    if (artistAlbumMap.get(artist).contains(album)) {
                        return artist;
                    }
                }
            }
        }
        return null;
    }

    public String mostPopularArtist() {
        // Implementation logic here...
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
        if (songs.isEmpty()) {
            return "No songs found";
        }
        Song mostPopularSong = songs.get(0);
        for (Song song : songs) {
            if (song.getLikes() > mostPopularSong.getLikes()) {
                mostPopularSong = song;
            }
        }
        return mostPopularSong.getTitle();
    }


    public List<User> getSongLikes(Song song) {
        return songLikeMap.get(song);
    }

    public List<Artist> findAllArtists() {
        return new ArrayList<>(artists);
    }

    public void saveUser(User user) {
        users.add(user);
    }

    public void saveArtist(Artist artist) {
        artists.add(artist);
    }

    public void saveAlbum(Album album) {
        albums.add(album);
    }
}
