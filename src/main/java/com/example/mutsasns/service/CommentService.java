package com.example.mutsasns.service;

import com.example.mutsasns.entity.Comment;
import com.example.mutsasns.entity.Post;
import com.example.mutsasns.entity.User;
import com.example.mutsasns.entity.dto.comment.CommentRequest;
import com.example.mutsasns.entity.dto.comment.CommentResponse;
import com.example.mutsasns.exception.AppException;
import com.example.mutsasns.exception.ErrorCode;
import com.example.mutsasns.repository.CommentRepository;
import com.example.mutsasns.repository.PostRepository;
import com.example.mutsasns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentResponse create(CommentRequest dto, Long id, String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Post post = postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        if (!post.getUser().getId().equals(user.getId())) throw new AppException(ErrorCode.INVALID_TOKEN, ErrorCode.INVALID_TOKEN.getMessage());

        Comment comment = commentRepository.save(dto.toEntity(user, post));
        return CommentResponse.of(comment);
    }

    public Page<CommentResponse> findAll(Pageable pageable, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        return commentRepository.findByPost(post, pageable).map(CommentResponse::of);
    }
}
