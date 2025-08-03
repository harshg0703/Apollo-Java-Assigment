package com.music.service;

import com.music.dto.UserProfileDto;
import com.music.modal.User;
import com.music.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public UserProfileDto SignUp(User user) throws Exception {

        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setUserName(user.getUserName());
        newUser.setPassword( passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());

        userRepository.save(newUser);
        UserProfileDto currentUser = new UserProfileDto();
        currentUser.setEmail(newUser.getEmail());
        currentUser.setUserName(newUser.getUserName());
        currentUser.setId(newUser.getId());
        currentUser.setFullName(newUser.getFullName());
        currentUser.setRole(newUser.getRole());
        return currentUser;
    }

    public String findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return optionalUser.get().getFullName();
        } else {
            throw new NoSuchElementException("User with ID " + id + " not found");
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }





}
