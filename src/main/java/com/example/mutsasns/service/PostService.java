package com.example.mutsasns.service;

import com.example.mutsasns.entity.Post;
import com.example.mutsasns.entity.User;
import com.example.mutsasns.entity.dto.post.PostRequest;
import com.example.mutsasns.entity.dto.post.PostResponse;
import com.example.mutsasns.exception.AppException;
import com.example.mutsasns.exception.ErrorCode;
import com.example.mutsasns.repository.PostRepository;
import com.example.mutsasns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse create(PostRequest dto, String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        Post post = postRepository.save(dto.toEntity(user));
        return PostResponse.of(post);
    }
}
