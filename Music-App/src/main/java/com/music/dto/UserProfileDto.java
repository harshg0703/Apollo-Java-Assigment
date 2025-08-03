package com.music.dto;


import lombok.Data;

@Data
public class UserProfileDto {
    private Long id;

    private String fullName;

    private String userName;

    private String email;

    private Role role;
}