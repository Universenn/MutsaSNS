package com.example.mutsasns.service;

import com.example.mutsasns.entity.*;
import com.example.mutsasns.entity.dto.comment.CommentDeleteResponse;
import com.example.mutsasns.entity.dto.comment.CommentRequest;
import com.example.mutsasns.entity.dto.comment.CommentResponse;
import com.example.mutsasns.entity.dto.comment.CommentUpdateResponse;
import com.example.mutsasns.entity.dto.user.UserRole;
import com.example.mutsasns.exception.AppException;
import com.example.mutsasns.exception.ErrorCode;
import com.example.mutsasns.repository.AlarmRepository;
import com.example.mutsasns.repository.CommentRepository;
import com.example.mutsasns.repository.PostRepository;
import com.example.mutsasns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AlarmRepository alarmRepository;

    /**
     * 댓글 등록
     */
    public CommentResponse create(CommentRequest dto, String userName, Long postId) {
        User user = findByUserName(userName);
        Post post = findByPostId(postId);

        Comment comment =commentRepository.save(dto.toEntity(user, post));

        alarmRepository.save(new Alarm(AlarmType.NEW_COMMENT_ON_POST, post.getUser(), user.getId(), postId, "new comment"));

        return CommentResponse.of(comment);
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public CommentUpdateResponse update(CommentRequest dto, String userName, Long postId, Long id) {
        Comment comment = findTargetComment(userName, postId, id);

        comment.update(dto.getComment());

        return CommentUpdateResponse.of(comment);
    }

    /**
     * 댓글 페이징 조회
     */
    public Page<CommentResponse> findCommentsList(Long postId, Pageable pageable) {
        return commentRepository.findAllByPostId(postId, pageable).map(CommentResponse::of);
    }

    /**
     * 댓글 삭제
     */
    public CommentDeleteResponse delete(String userName, Long postId, Long id) {
        Comment comment = findTargetComment(userName, postId, id);

        commentRepository.deleteById(id);

        return CommentDeleteResponse.of(comment);
    }

    /***************************메서드***************************/

    /**
     * UserName 찾기
     */
    private User findByUserName(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        return user;
    }

    /**
     * Post 찾기
     */
    private Post findByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        return post;
    }

    /**
     * Comment 찾기
     */
    private Comment getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));
        return comment;
    }

    /**
     * Token 일치 확인, ADMIN 유무 확인
     */
    private static void extracted(User user, Post post) {
        if (!post.getUser().getId().equals(user.getId()) && user.getRole() != UserRole.ROLE_ADMIN) throw new AppException(ErrorCode.INVALID_TOKEN, ErrorCode.INVALID_TOKEN.getMessage());
    }

    private Comment findTargetComment(String userName, Long postId, Long id) {
        User user = findByUserName(userName);
        Post post = findByPostId(postId);
        Comment comment = getComment(id);

        extracted(user, post);
        return comment;
    }
}
