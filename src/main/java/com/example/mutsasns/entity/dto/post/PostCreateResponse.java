package com.example.mutsasns.entity.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostCreateResponse {
    private String message;
    private Long postId;

    public static PostCreateResponse success(String message,Long postId) {
        return new PostCreateResponse(message, postId);
    }
}
