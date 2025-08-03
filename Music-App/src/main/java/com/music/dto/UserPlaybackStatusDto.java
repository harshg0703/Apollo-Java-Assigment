package com.music.dto;

import com.music.modal.Song;
import com.music.modal.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPlaybackStatusDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "song_id")
    private Song song;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaybackState state;

    // This is the missing PlaybackState enum
    public enum PlaybackState {
        PLAYING,
        PAUSED
    }

    public void setState(PlaybackState state) {
        this.state = state;
    }
}