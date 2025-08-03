package com.music.controller;

import com.music.modal.Playlist;
import com.music.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlayListController {

    private final PlayListService playListService;

    @PostMapping
    public ResponseEntity<Playlist> createPlayList(@RequestBody Playlist playlist){

    return new ResponseEntity<>(playListService.createPlayList(playlist),HttpStatus.CREATED);
    }


    @PutMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Playlist> addSongToExistingPlaylist(@PathVariable Long playlistId,@PathVariable Long songId ){

        return new ResponseEntity<>(playListService.addSongToExistingPlaylist(playlistId,songId),HttpStatus.CREATED);
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<String> deleteSongFromExistingPlaylist(@PathVariable Long playlistId,@PathVariable Long songId){

        return new ResponseEntity<>(playListService.deleteSongFromExistingPlaylist(playlistId,songId),HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public List<Playlist> playListOfUser(@PathVariable Long userId){
        return playListService.playListOfUser(userId);
    }

}
