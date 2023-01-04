package com.example.mutsasns.entity.dto.comment;

import com.example.mutsasns.entity.Comment;
import com.example.mutsasns.entity.Post;
import com.example.mutsasns.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CommentRequest {
    private String comment;

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .comment(comment)
                .user(user)
                .post(post)
                .build();

    }
}
