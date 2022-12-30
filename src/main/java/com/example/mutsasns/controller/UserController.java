package com.example.mutsasns.controller;


import com.example.mutsasns.entity.dto.Response;
import com.example.mutsasns.entity.dto.user.UserRequest;
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
    public Response<UserJoinResponse> add(@RequestBody UserRequest dto) {
        return Response.success(userService.join(dto));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserRequest dto) {
        return Response.success(userService.login(dto));
    }


}
