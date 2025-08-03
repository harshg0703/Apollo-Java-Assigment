package com.music.modal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false)
    private String genre;

    private int duration;

    @Column(nullable = false)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;
}
