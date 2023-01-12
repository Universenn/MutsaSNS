package com.example.mutsasns.service;

import com.example.mutsasns.entity.Post;
import com.example.mutsasns.entity.User;
import com.example.mutsasns.entity.Likes;
import com.example.mutsasns.exception.AppException;
import com.example.mutsasns.exception.ErrorCode;
import com.example.mutsasns.repository.LikesRepository;
import com.example.mutsasns.repository.PostRepository;
import com.example.mutsasns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikesService {


    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public String pushLike(Long postId, String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        likesRepository.findByPostAndUser(post, user).ifPresent(likes -> {
            throw new AppException(ErrorCode.DUPLICATED_LIKE, ErrorCode.DUPLICATED_USERNAME.getMessage());
        });


        Likes like = Likes.of(user, post);
        likesRepository.save(like);

        return "좋아요를 눌렀습니다.";
    }

    public Integer countLike(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        // post 개수를 새어준다
        return likesRepository.countByPost(post);
    }
}
