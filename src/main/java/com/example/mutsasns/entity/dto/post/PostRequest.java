package com.example.mutsasns.entity.dto.post;

import com.example.mutsasns.entity.Post;
import com.example.mutsasns.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String title;
    private String body;
    private String user;

    public Post toEntity(User user) {
        return Post.builder()
                .title(title)
                .body(body)
                .user(user)
                .build();
    }
}
