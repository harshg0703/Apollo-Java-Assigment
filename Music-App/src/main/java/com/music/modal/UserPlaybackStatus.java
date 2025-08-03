package com.music.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_playback_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPlaybackStatus {

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


    public enum PlaybackState {
        PLAYING,
        PAUSED
    }
}