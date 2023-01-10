package com.example.mutsasns.controller;

import com.example.mutsasns.entity.dto.Response;
import com.example.mutsasns.entity.dto.comment.CommentRequest;
import com.example.mutsasns.entity.dto.comment.CommentResponse;
import com.example.mutsasns.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    public Response<CommentResponse> create(@PathVariable Long postId, @RequestBody CommentRequest dto, Authentication authentication) {
        CommentResponse commentResponse = commentService.create(dto, postId, authentication.getName());
        return Response.success(commentResponse);
    }

    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> findAll(@PathVariable Long postId, Pageable pageable) {
        Page<CommentResponse> list = commentService.findAll(pageable, postId);
        return Response.success(list);
    }
}
