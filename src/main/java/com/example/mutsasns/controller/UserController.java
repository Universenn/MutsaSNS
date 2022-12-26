package com.example.mutsasns.controller;


import com.example.mutsasns.entity.dto.Response;
import com.example.mutsasns.entity.dto.user.UserJoinRequest;
import com.example.mutsasns.entity.dto.user.UserJoinResponse;
import com.example.mutsasns.entity.dto.user.UserLoginResponse;
import com.example.mutsasns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> add(@RequestBody UserJoinRequest dto) {
        return Response.success(userService.add(dto));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserJoinRequest dto) {
        return Response.success(userService.login(dto));
    }


}
