package com.music.repository;

import com.music.modal.User;
import com.music.modal.UserPlaybackStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPlaybackStatusRepository extends JpaRepository<UserPlaybackStatus, Long> {
    Optional<UserPlaybackStatus> findByUser(User user);
}
