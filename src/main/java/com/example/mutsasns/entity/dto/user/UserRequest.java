package com.example.mutsasns.entity.dto.user;

import com.example.mutsasns.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRequest {

    private String userName;
    private String password;


    // DTO -> Entity
    public User toEntity() {
        return User.builder()
                .userName(userName)
                .password(password)
                .build();
    }
}
