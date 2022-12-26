package com.example.mutsasns.entity.dto.user;


import com.example.mutsasns.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
public class UserLoginResponse {
    private String jwt;

    public UserLoginResponse(String jwt) {
        this.jwt = jwt;
    }
}
