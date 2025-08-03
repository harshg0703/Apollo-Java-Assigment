package com.music.controller;

import com.music.modal.Song;
import com.music.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping()
    public List<Song> songList(){
        return songService.songList();
    }

    @GetMapping("/search")
    public Song songByTitle(@RequestParam(required = false) String title,@RequestParam(required = false) String artist,@RequestParam(required = false) String genre) {

        return songService.songByTitle(title, artist, genre);
    }

    @PostMapping()
    public ResponseEntity<String> addSong(@RequestBody Song song,@RequestParam String username) throws Exception {

        songService.addSong(song,username);
        return new ResponseEntity<>("Song has been added", HttpStatus.CREATED);
    }
}
