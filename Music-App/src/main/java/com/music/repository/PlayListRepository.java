package com.music.repository;

import com.music.modal.Playlist;
import com.music.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayListRepository extends JpaRepository<Playlist,Long> {

    List<Playlist> findByCreatedBy(User user);
}
