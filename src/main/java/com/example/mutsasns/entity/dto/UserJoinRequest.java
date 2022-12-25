package com.example.mutsasns.entity.dto;

import com.example.mutsasns.entity.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserJoinRequest {

    private String userName;
    private String password;
    public User toEntity() {
        return User.builder()
                .userName(userName)
                .password(password)
                .build();
    }
}
