package com.driver;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spotify")
public class SpotifyController {

    // Autowire will not work in this case, no need to change this and add autowire

    static HashMap<String, User> userDB = new HashMap<>();
    static HashMap<String, Artist> artistDB = new HashMap<>();
    static HashMap<String, Album> albumDB = new HashMap<>();
    static HashMap<String, Song> songDB = new HashMap<>();
    static HashMap<String, Playlist> playlistDB = new HashMap<>();

    @PostMapping("/add-user")
    public ResponseEntity<User> createUser(@RequestParam(name = "name") String name, @RequestParam(name = "mobile") String mobile) {
        // Create the user with given name and number
        if (name == null || name.isEmpty() || mobile == null || mobile.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        User user = new User(name, mobile);
        userDB.put(mobile, user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
   //one line added
    @PostMapping("/add-artist")
    public void createArtist(@RequestParam(name = "name") String name) {
        // Create the artist with given name
        Artist artist = new Artist(name);
        artistDB.put(name, artist);
    }

    @PostMapping("/add-album")
    public String createAlbum(@RequestParam(name = "title") String title, @RequestParam(name = "artistName") String artistName) {
        // If the artist does not exist, first create an artist with the given name
        // Create an album with given title and artist
        if (!artistDB.containsKey(artistName)) {
            createArtist(artistName);
        }
        Album album = new Album(title, artistName);
        albumDB.put(title, album);
        return "Success";
    }

    @PostMapping("/add-song")
    public String createSong(@RequestParam(name = "title") String title, @RequestParam(name = "albumName") String albumName, @RequestParam(name = "length") int length) throws Exception {
        // If the album does not exist in the database, throw "Album does not exist" exception
        // Create and add the song to the respective album
        if (!albumDB.containsKey(albumName)) {
            throw new Exception("Album does not exist");
        }
        Song song = new Song(title, albumName, length);
        songDB.put(title, song);
        return "Success";
    }

    @PostMapping("/add-playlist-on-length")
    public String createPlaylistOnLength(@RequestParam(name = "mobile") String mobile, @RequestParam(name = "title") String title, @RequestParam(name = "length") int length) throws Exception {
        // Create a playlist with given title and add all songs having the given length in the database to that playlist
        // The creator of the playlist will be the given user and will also be the only listener at the time of playlist creation
        // If the user does not exist, throw "User does not exist" exception
        if (!userDB.containsKey(mobile)) {
            throw new Exception("User does not exist");
        }
        Playlist playlist = new Playlist(title);
        for (Song song : songDB.values()) {
            if (song.getLength() == length) {
                playlist.addSong(song);
            }
        }
        playlist.addListener(userDB.get(mobile));
        playlistDB.put(title, playlist);
        return "Success";
    }

    @PostMapping("/add-playlist-on-name")
    public String createPlaylistOnName(@RequestParam(name = "mobile") String mobile, @RequestParam(name = "title") String title, @RequestParam(name = "songTitles") List<String> songTitles) throws Exception {
        // Create a playlist with given title and add all songs having the given titles in the database to that playlist
        // The creator of the playlist will be the given user and will also be the only listener at the time of playlist creation
        // If the user does not exist, throw "User does not exist" exception
        if (!userDB.containsKey(mobile)) {
            throw new Exception("User does not exist");
        }
        Playlist playlist = new Playlist(title);
        for (String songTitle : songTitles) {
            if (songDB.containsKey(songTitle)) {
                playlist.addSong(songDB.get(songTitle));
            }
        }
        playlist.addListener(userDB.get(mobile));
        playlistDB.put(title, playlist);
        return "Success";
    }

    @PutMapping("/find-playlist")
    public String findPlaylist(@RequestParam(name = "mobile") String mobile, @RequestParam(name = "playlistTitle") String playlistTitle) throws Exception {
        // Find the playlist with given title and add user as listener of that playlist and update user accordingly
        // If the user is the creator or already a listener, do nothing
        // If the user does not exist, throw "User does not exist" exception
        // If the playlist does not exist, throw "Playlist does not exist" exception
        // Return the playlist after updating
        if (!userDB.containsKey(mobile)) {
            throw new Exception("User does not exist");
        }
        if (!playlistDB.containsKey(playlistTitle)) {
            throw new Exception("Playlist does not exist");
        }
        Playlist playlist = playlistDB.get(playlistTitle);
        User user = userDB.get(mobile);
        if (!playlist.getListeners().contains(user) && !playlist.getCreator().equals(user)) {
            playlist.addListener(user);
        }
        return "Success";
    }

    @PutMapping("/like-song")
    public String likeSong(@RequestParam(name = "mobile") String mobile, @RequestParam(name = "songTitle") String songTitle) throws Exception {
        // The user likes the given song. The corresponding artist of the song gets auto-liked
        // A song can be liked by a user only once. If a user tries to like a song multiple times, do nothing
        // However, an artist can indirectly have multiple likes from a user, if the user has liked multiple songs of that artist.
        // If the user does not exist, throw "User does not exist" exception
        // If the song does not exist, throw "Song does not exist" exception
        // Return the song after updating
        if (!userDB.containsKey(mobile)) {
            throw new Exception("User does not exist");
        }
        if (!songDB.containsKey(songTitle)) {
            throw new Exception("Song does not exist");
        }
        Song song = songDB.get(songTitle);
        User user = userDB.get(mobile);
        if (!song.getLikers().contains(user)) {
            song.addLiker(user);
            Artist artist = artistDB.get(song.getArtistName());
            artist.addLiker(user);
        }
        return "Success";
    }

    @GetMapping("/popular-artist")
    public String mostPopularArtist() {
        // Return the artist name with maximum likes
        return artistDB.values().stream().max(Comparator.comparingInt(Artist::getLikeCount)).map(Artist::getName).orElse("No artists found");
    }

    @GetMapping("/popular-song")
    public String mostPopularSong() {
        // Return the song title with maximum likes
        return songDB.values().stream().max(Comparator.comparingInt(Song::getLikeCount)).map(Song::getTitle).orElse("No songs found");
    }
}