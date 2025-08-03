package com.music.service;

import com.music.dto.PlaybackStatus;
import com.music.dto.PlaybackStatusDto;
import com.music.dto.SongDto;
import com.music.modal.Song;
import com.music.modal.User;
import com.music.modal.UserPlaybackStatus;
import com.music.repository.SongRepository;
import com.music.repository.UserPlaybackStatusRepository;
import com.music.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaybackService {

    private final UserPlaybackStatusRepository playbackRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public PlaybackService(UserPlaybackStatusRepository playbackRepository, UserRepository userRepository, SongRepository songRepository) {
        this.playbackRepository = playbackRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    public PlaybackStatusDto playSong(String username, Long songId) throws Exception {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new Exception("User not found"));
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new Exception("Song not found"));

        UserPlaybackStatus status = playbackRepository.findByUser(user)
                .orElse(new UserPlaybackStatus());

        status.setUser(user);
        status.setSong(song);
        status.setState(UserPlaybackStatus.PlaybackState.PLAYING);
        UserPlaybackStatus savedStatus = playbackRepository.save(status);

        return mapToDto(savedStatus);
    }

    public PlaybackStatusDto pauseSong(String username) throws Exception {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new Exception("User not found"));

        UserPlaybackStatus status = playbackRepository.findByUser(user)
                .orElseThrow(() -> new Exception("No song is currently playing"));

        if (status.getState() == UserPlaybackStatus.PlaybackState.PAUSED) {
            return mapToDto(status); // Already paused
        }

        status.setState(UserPlaybackStatus.PlaybackState.PAUSED);
        UserPlaybackStatus savedStatus = playbackRepository.save(status);
        return mapToDto(savedStatus);
    }

    public PlaybackStatusDto resumeSong(String username) throws Exception {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new Exception("User not found"));

        UserPlaybackStatus status = playbackRepository.findByUser(user)
                .orElseThrow(() -> new Exception("No song is currently paused"));

        if (status.getState() == UserPlaybackStatus.PlaybackState.PLAYING) {
            return mapToDto(status); // Already playing
        }

        status.setState(UserPlaybackStatus.PlaybackState.PLAYING);
        UserPlaybackStatus savedStatus = playbackRepository.save(status);
        return mapToDto(savedStatus);
    }

    public void stopSong(String username) throws Exception {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new Exception("User not found"));

        playbackRepository.findByUser(user)
                .ifPresent(playbackRepository::delete);
    }

    public PlaybackStatusDto getCurrentPlayingSong(String username) throws Exception {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new Exception("User not found"));

        UserPlaybackStatus status = playbackRepository.findByUser(user)
                .orElseThrow(() -> new Exception("No song is currently playing or paused"));

        return mapToDto(status);
    }

    private PlaybackStatusDto mapToDto(UserPlaybackStatus status) {
        PlaybackStatusDto dto = new PlaybackStatusDto();
        dto.setCurrentSong(new SongDto(status.getSong().getId(), status.getSong().getTitle(), status.getSong().getArtist(),  status.getSong().getGenre()));
        dto.setState(PlaybackStatus.valueOf(status.getState().name()));
        return dto;
    }
}