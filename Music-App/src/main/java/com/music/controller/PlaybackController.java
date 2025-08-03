package com.music.controller;



import com.music.dto.PlaybackStatusDto;
import com.music.service.PlaybackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playback2")
@RequiredArgsConstructor
public class PlaybackController {

    private final PlaybackService playbackService;


    @PostMapping("/play/{songId}")
    public ResponseEntity<PlaybackStatusDto> playSong(@PathVariable Long songId, @RequestParam String username) throws Exception {
        PlaybackStatusDto status = playbackService.playSong(username, songId);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/pause")
    public ResponseEntity<PlaybackStatusDto> pauseSong(@RequestParam String username) throws Exception {

        PlaybackStatusDto status = playbackService.pauseSong(username);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/resume")
    public ResponseEntity<PlaybackStatusDto> resumeSong(@RequestParam String username) throws Exception {

        PlaybackStatusDto status = playbackService.resumeSong(username);
        return ResponseEntity.ok(status);
    }

    @DeleteMapping("/stop")
    public ResponseEntity<Void> stopSong(@RequestParam String username) throws Exception {

        playbackService.stopSong(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/current")
    public ResponseEntity<PlaybackStatusDto> getCurrentPlayingSong(@RequestParam String username) throws Exception {

        PlaybackStatusDto status = playbackService.getCurrentPlayingSong(username);
        return ResponseEntity.ok(status);
    }
}