package com.example.mutsasns.controller;

import com.example.mutsasns.entity.dto.user.UserJoinRequest;
import com.example.mutsasns.entity.dto.user.UserJoinResponse;
import com.example.mutsasns.entity.dto.user.UserLoginResponse;
import com.example.mutsasns.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    // given
    String userName = "name";
    String password = "q1w2e3r4";

    String token = "token";
    UserJoinRequest userJoinRequest = new UserJoinRequest(userName, password);
    UserJoinResponse userJoinResponse = UserJoinResponse.of(userJoinRequest.toEntity());


    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void join() throws Exception {

        // when
        when(userService.add(userJoinRequest))
                .thenReturn(userJoinResponse);

        // then
        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("로그인 성공")
    @WithMockUser
    void login_success() throws Exception {

        // when
        when(userService.login(userJoinRequest))
                .thenReturn(new UserLoginResponse(token));

        // then
        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}