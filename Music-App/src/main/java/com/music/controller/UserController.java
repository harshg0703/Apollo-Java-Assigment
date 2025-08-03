package com.music.controller;


import com.music.dto.UserProfileDto;
import com.music.modal.User;
import com.music.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {



    @Autowired
    private final UserService userService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ResponseEntity<String> loginUser(  @RequestParam String email,@RequestParam String password) {

        User user = userService.findByEmail(email);
        String name = user.getFullName();
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {

            return new ResponseEntity<>("Hii " + name +", You are logged in successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<UserProfileDto> Signup(@RequestBody User user) throws Exception {
        return new ResponseEntity<>(userService.SignUp(user), HttpStatus.CREATED);
    }

    @GetMapping("/login/{id}")
    public ResponseEntity<String> findUserById(@PathVariable Long id){

        return new ResponseEntity<>(userService.findUserById(id),HttpStatus.OK);
    }



}
