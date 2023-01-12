package com.example.mutsasns.controller;

import com.example.mutsasns.entity.dto.comment.CommentDeleteResponse;
import com.example.mutsasns.entity.dto.comment.CommentRequest;
import com.example.mutsasns.entity.dto.comment.CommentResponse;
import com.example.mutsasns.entity.dto.comment.CommentUpdateResponse;
import com.example.mutsasns.exception.AppException;
import com.example.mutsasns.exception.ErrorCode;
import com.example.mutsasns.security.config.EncoderConfig;
import com.example.mutsasns.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    CommentService commentService;
    
    @MockBean
    EncoderConfig encoderConfig;
    
    @Autowired
    ObjectMapper objectMapper;


    /**
     * given
     */
    String comment = "댓글";
    String commentUpdate = "수정 댓글";

    CommentRequest commentRequest = new CommentRequest(comment);
    CommentRequest commentUpdateRequest = new CommentRequest(commentUpdate);

    // 댓글 응답
    CommentResponse commentResponse = CommentResponse.builder()
            .id(1L)
            .comment(commentRequest.getComment())
            .userName("user1")
            .postId(3L)
            .createdAt(LocalDateTime.now())
            .build();


    // 수정 댓글 응답
    CommentUpdateResponse commentUpdateResponse = CommentUpdateResponse.builder()
            .id(1L)
            .comment(commentUpdateRequest.getComment())
            .userName("이름")
            .postId(3L)
            .createdAt(LocalDateTime.now())
            .lastModifiedBy(LocalDateTime.now())
            .build();

    CommentDeleteResponse commentDeleteResponse = CommentDeleteResponse.builder()
            .id(1L)
            .message("댓글 삭제 완료")
            .build();



    @Test
    @DisplayName("댓글 등록 - 성공")
    @WithMockUser
    void comment_success() throws Exception {

        // when
        given(commentService.create(any(), any(), any())).willReturn(commentResponse);

        // then
        mockMvc.perform(post("/api/v1/posts/1/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.resultCode").exists())
                .andExpect(jsonPath("$.result.id").exists())
                .andExpect(jsonPath("$.result.comment").value(commentRequest.getComment()))
                .andExpect(jsonPath("$.result.userName").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(jsonPath("$.result.createdAt").exists())
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("댓글 등록 - 실패(게시물이 없는 경우)")
    @WithMockUser
    void comment_fail1() throws Exception {

        // when
        given(commentService.create(any(), any(), any()))
                .willThrow(new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // then
        mockMvc.perform(post("/api/v1/posts/1/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.resultCode").exists())
                .andExpect(jsonPath("$.result.errorCode").exists())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getHttpStatus().value()));
    }

    @Test
    @DisplayName("댓글 등록 - 실패(로그인 실패)")
    @WithMockUser
    void comment_fail2() throws Exception {

        // when
        given(commentService.create(any(), any(), any()))
                .willThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

        // then
        mockMvc.perform(post("/api/v1/posts/3/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.resultCode").exists())
                .andExpect(jsonPath("$.result.errorCode").exists())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("댓글 수정 - 성공")
    @WithMockUser
    void comment_update_success() throws Exception {

        // when
        given(commentService.update(any(), any(), any(), any()))
                .willReturn(commentUpdateResponse);

        // then
        mockMvc.perform(put("/api/v1/posts/3/comments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentUpdate)))
                .andDo(print())
                .andExpect(jsonPath("$.resultCode").exists())
                .andExpect(jsonPath("$.result.id").exists())
                .andExpect(jsonPath("$.result.comment").value(commentUpdateRequest.getComment()))
                .andExpect(jsonPath("$.result.userName").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(jsonPath("$.result.createdAt").exists())
                .andExpect(jsonPath("$.result.lastModifiedBy").exists())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 수정 - 실패(인증 실패)")
    @WithMockUser
    void comment_update_fail1() throws Exception {

        // when
        given(commentService.update(any(), any(), any(), any()))
                .willThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

        // then
        mockMvc.perform(put("/api/v1/posts/3/comments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentUpdate)))
                .andDo(print())
                .andExpect(jsonPath("$.resultCode").exists())
                .andExpect(jsonPath("$.result.errorCode").exists())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));
//                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("댓글 수정 - 실패(게시물 없는 경우)")
    @WithMockUser
    void comment_update_fail2() throws Exception {

        // when
        given(commentService.update(any(), any(), any(), any()))
                .willThrow(new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // then
        mockMvc.perform(put("/api/v1/posts/3/comments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentUpdate)))
                .andDo(print())
                .andExpect(jsonPath("$.resultCode").exists())
                .andExpect(jsonPath("$.result.errorCode").exists())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getHttpStatus().value()));
    }

    @Test
    @DisplayName("댓글 조회 - 성공")
    @WithMockUser
    void comment_join_success() throws Exception {

        // when
        given(commentService.findCommentsList(any(), any()))
                .willReturn(Page.empty());

        // then
        mockMvc.perform(get("/api/v1/posts/3/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("댓글 삭제 - 성공")
    @WithMockUser
    void comment_delete_success() throws Exception {

        // when
        given(commentService.delete(any(), any(), any()))
                .willReturn(commentDeleteResponse);

        // then
        mockMvc.perform(delete("/api/v1/posts/3/comments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.resultCode").exists())
                .andExpect(jsonPath("$.result.id").exists())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(status().isOk());
    }


}