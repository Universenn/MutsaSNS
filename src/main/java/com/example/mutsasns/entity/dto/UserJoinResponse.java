package com.example.mutsasns.entity.dto;

import com.example.mutsasns.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserJoinResponse {
    private Long userId;
    private String userName;

    public static UserJoinResponse of(User user) {
        return UserJoinResponse.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .build();
    }
}
