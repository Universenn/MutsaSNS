package com.example.mutsasns.entity.dto.comment;

import com.example.mutsasns.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentDeleteResponse {

    private Long id;
    private String message;


    public static CommentDeleteResponse of(Comment comment) {
        return CommentDeleteResponse.builder()
                .id(comment.getId())
                .message("댓글 삭제 완료")
                .build();
    }
}
