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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public PostResponse update(PostRequest dto, String userName, Long id) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Post post = postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        if (!user.getId().equals(post.getId())) throw new AppException(ErrorCode.INVALID_TOKEN, ErrorCode.INVALID_TOKEN.getMessage());

        post.update(dto.getTitle(), dto.getBody());

        return PostResponse.of(post);
    }

    public PostResponse deletePost(String userName, Long id) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Post post = postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        if (!user.getId().equals(post.getId())) throw new AppException(ErrorCode.INVALID_TOKEN, ErrorCode.INVALID_TOKEN.getMessage());

        postRepository.deleteById(post.getId());

        return PostResponse.of(post);
    }

}
