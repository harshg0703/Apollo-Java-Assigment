package com.music.dto;

import lombok.Data;

@Data
public class PlaybackStatusDto {
    private SongDto currentSong;
    private PlaybackStatus state;


}
