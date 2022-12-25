package com.example.mutsasns.controller;


import com.example.mutsasns.entity.dto.UserJoinRequest;
import com.example.mutsasns.entity.dto.UserJoinResponse;
import com.example.mutsasns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public UserJoinResponse add(UserJoinRequest dto) {
        return userService.add(dto);
    }


}
