package com.example.mutsasns.service;

import com.example.mutsasns.entity.User;
import com.example.mutsasns.entity.dto.user.UserJoinRequest;
import com.example.mutsasns.entity.dto.user.UserJoinResponse;
import com.example.mutsasns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserJoinResponse add(UserJoinRequest userJoinRequest) {
        User user  = userRepository.save(userJoinRequest.toEntity());
        return UserJoinResponse.of(user);
    }

}
