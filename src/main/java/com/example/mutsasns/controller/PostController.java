package com.example.mutsasns.controller;

import com.example.mutsasns.entity.dto.Response;
import com.example.mutsasns.entity.dto.post.PostCreateResponse;
import com.example.mutsasns.entity.dto.post.PostRequest;
import com.example.mutsasns.entity.dto.post.PostResponse;
import com.example.mutsasns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping()
    public Response<PostCreateResponse> create(Authentication authentication, @RequestBody PostRequest dto) {
        String userName = authentication.getName();
        PostResponse postResponse = postService.create(dto, userName);
        return Response.success(PostCreateResponse.success("포스트 등록 완료", postResponse.getId()));
    }

}
