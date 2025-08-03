package com.music.service;

import com.music.dto.Role;
import com.music.modal.Song;
import com.music.modal.User;
import com.music.repository.SongRepository;
import com.music.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Song> songList(){
        return songRepository.findAll();
    }

    public Song songByTitle(String title, String artist, String genre) {
        List<Song> songs = songRepository.findAll();

        return songs.stream()
                .filter(song -> title == null || song.getTitle().equalsIgnoreCase(title))
                .filter(song -> artist == null || song.getArtist().equalsIgnoreCase(artist))
                .filter(song -> genre == null || song.getGenre().equalsIgnoreCase(genre))
                .findFirst()
                .orElse(null);
    }

    public void addSong(Song song,String username) throws Exception {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        if (user.getRole().equals(Role.ADMIN)){
            songRepository.save(song);
        }
        else {

            throw new Exception("Only Admins can add a song");

        }


    }
}
