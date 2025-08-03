package com.music.service;

import com.music.modal.Playlist;
import com.music.modal.Song;
import com.music.modal.User;
import com.music.repository.PlayListRepository;
import com.music.repository.SongRepository;
import com.music.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayListService {

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    public Playlist createPlayList(Playlist playlist) {

        Long userId = playlist.getCreatedBy().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        playlist.setCreatedBy(user);

        Set<Song> resolvedSongs = playlist.getSongs().stream()
                .map(song -> songRepository.findById(song.getId())
                        .orElseThrow(() -> new RuntimeException("Song not found with ID: " + song.getId())))
                .collect(Collectors.toSet());
        playlist.setSongs(resolvedSongs);

        return playListRepository.save(playlist);
    }

    public List<Playlist> playListOfUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        return playListRepository.findByCreatedBy(user);
    }

    public Playlist addSongToExistingPlaylist(Long playlistId, Long songId) {
        Playlist existingPlaylist = playListRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found with ID: " + playlistId));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found with ID: " + songId));

        existingPlaylist.getSongs().add(song);

        return playListRepository.save(existingPlaylist);
    }
    public String deleteSongFromExistingPlaylist(Long playlistId, Long songId) {

        Playlist existingPlaylist = playListRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found with ID: " + playlistId));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found with ID: " + songId));

        if (!existingPlaylist.getSongs().contains(song)) {
            throw new RuntimeException("Song is not part of the playlist with ID: " + playlistId);
        }

        existingPlaylist.getSongs().remove(song);

        playListRepository.save(existingPlaylist);

        return "Song has been deleted from the playlist";
    }




}
