package com.example.mutsasns.controller;

import com.example.mutsasns.entity.User;
import com.example.mutsasns.entity.dto.Response;
import com.example.mutsasns.entity.dto.post.PostCreateResponse;
import com.example.mutsasns.entity.dto.post.PostRequest;
import com.example.mutsasns.entity.dto.post.PostResponse;
import com.example.mutsasns.service.LikesService;
import com.example.mutsasns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final LikesService likesService;

    @PostMapping()
    public Response<PostCreateResponse> create(Authentication authentication, @RequestBody PostRequest dto) {
        String userName = authentication.getName();
        PostResponse postResponse = postService.create(dto, userName);
        return Response.success(PostCreateResponse.success("포스트 등록 완료", postResponse.getId()));
    }

    @PutMapping("/{id}")
    public Response<PostCreateResponse> update(Authentication authentication, @RequestBody PostRequest dto, @PathVariable Long id) {
        String userName = authentication.getName();
        PostResponse postResponse = postService.update(dto, userName, id);
        return Response.success(PostCreateResponse.success("포스트 수정 완료", postResponse.getId()));
    }

    @DeleteMapping("/{id}")
    public Response<PostCreateResponse> deletePost(Authentication authentication, @PathVariable Long id) {
        String userName = authentication.getName();
        PostResponse postResponse = postService.deletePost(userName, id);
        return Response.success(PostCreateResponse.success("포스트 삭제 완료", postResponse.getId()));
    }

    @GetMapping("/{id}")
    public Response<PostResponse> detailPost(@PathVariable Long id) {
        PostResponse postResponse = postService.detailPost(id);
        return Response.success(postResponse);
    }

    @GetMapping()
    public Response<Page<PostResponse>> list(@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponse> list = postService.list(pageable);
        return Response.success(list);
    }

    /**
     * 좋아요 기능
     */

    @PostMapping("/{postId}/likes")
    public Response<String> doLikes(@PathVariable Long postId, Authentication authentication) {
        String userName = authentication.getName();
        String result = likesService.pushLike(postId, userName);
        return Response.success(result);
    }
    @GetMapping("/{postId}/likes")
    public Response<Integer> countLikes(@PathVariable Long postId) {
        Integer result = likesService.countLike(postId);
        return Response.success(result);
    }

    /**
     * 마이 피드
     */

    @GetMapping("/my")
    public Response<Page<PostResponse>> myFeed(@PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
        String userName = authentication.getName();
        Page<PostResponse> list = postService.myFeed(userName, pageable);
        return Response.success(list);
    }


}
