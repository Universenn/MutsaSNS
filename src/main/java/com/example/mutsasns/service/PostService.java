package com.example.mutsasns.service;

import com.example.mutsasns.entity.Post;
import com.example.mutsasns.entity.User;
import com.example.mutsasns.entity.dto.post.PostRequest;
import com.example.mutsasns.entity.dto.post.PostResponse;
import com.example.mutsasns.entity.dto.user.UserRole;
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

    /**
     * 포스트 등록
     */
    public PostResponse create(PostRequest dto, String userName) {
        User user = findUserName(userName);
        Post post = postRepository.save(dto.toEntity(user));
        return PostResponse.of(post);
    }

    /**
     * 포스트 수정
     */
    @Transactional
    public PostResponse update(PostRequest dto, String userName, Long id) {
        Post post = findTargetPost(userName, id);
        post.update(dto.getTitle(), dto.getBody());
        return PostResponse.of(post);
    }

    /**
     * 포스트 삭제
     */
    public PostResponse deletePost(String userName, Long id) {
        Post post = findTargetPost(userName, id);
        postRepository.deleteById(id);
        return PostResponse.of(post);
    }

    /**
     * 포스트 상세정보
     */
    public PostResponse detailPost(Long id) {
        Post post = findByPostId(id);
        return PostResponse.of(post);
    }

    /**
     * 포스트 리스트 정보
     */
    public Page<PostResponse> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponse::of);
    }


    /**
     * 포스트 마이피드
     */
    public Page<PostResponse> myFeed(String userName,Pageable pageable) {
        User user = findUserName(userName);
        return postRepository.findAllByUser(pageable, user).map(PostResponse::of);
    }


    /**
     * UserName 찾기
     */
    private User findUserName(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        return user;
    }

    /**
     * Post 찾기
     */
    private Post findByPostId(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        return post;
    }

    /**
     * Token 일치 확인, ADMIN 유무 확인
     */
    private static void extracted(User user, Post post) {
        if (!post.getUser().getId().equals(user.getId()) && user.getRole() != UserRole.ROLE_ADMIN) throw new AppException(ErrorCode.INVALID_TOKEN, ErrorCode.INVALID_TOKEN.getMessage());
    }

    private Post findTargetPost(String userName, Long id) {
        User user = findUserName(userName);

        Post post = findByPostId(id);

        extracted(user, post);
        return post;
    }

}
