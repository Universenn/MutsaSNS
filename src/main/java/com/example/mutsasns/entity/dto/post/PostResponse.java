package com.example.mutsasns.entity.dto.post;

import com.example.mutsasns.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String body;
    private String userName;
    private LocalDateTime creatAt;
    private LocalDateTime lastModifiedAt;

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .userName(post.getUser().getUserName())
//                .creatAt(post.getCreatedAt())
//                .lastModifiedAt(post.getLastModifiedAt())
                .build();
    }
}
