package com.example.mutsasns.controller;

import com.example.mutsasns.entity.dto.Response;
import com.example.mutsasns.entity.dto.comment.CommentDeleteResponse;
import com.example.mutsasns.entity.dto.comment.CommentRequest;
import com.example.mutsasns.entity.dto.comment.CommentResponse;
import com.example.mutsasns.entity.dto.comment.CommentUpdateResponse;
import com.example.mutsasns.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    public Response<CommentResponse> create(@RequestBody CommentRequest dto, Authentication authentication, @PathVariable Long postId) {
        String userName = authentication.getName();
        CommentResponse commentResponse = commentService.create(dto, userName, postId);
        return Response.success(commentResponse);
    }

    @PutMapping("/{postId}/comments/{id}")
    public Response<CommentUpdateResponse> update(@PathVariable Long id, @PathVariable Long postId, Authentication authentication, @RequestBody CommentRequest dto) {
        String userName = authentication.getName();
        CommentUpdateResponse commentupdateResponse = commentService.update(dto, userName, postId, id);
        return Response.success(commentupdateResponse);
    }

    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> findCommentsList(@PathVariable Long postId,@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CommentResponse> CommentResponse = commentService.findCommentsList(postId, pageable);
        return Response.success(CommentResponse);
    }

    @DeleteMapping("/{postId}/comments/{id}")
    public Response<CommentDeleteResponse> delete(@PathVariable Long id, @PathVariable Long postId, Authentication authentication) {
        String userName = authentication.getName();
        CommentDeleteResponse commentDeleteResponse = commentService.delete(userName, postId, id);
        return Response.success(commentDeleteResponse);
    }
}
