package com.example.mutsasns.service;

import com.example.mutsasns.entity.User;
import com.example.mutsasns.entity.dto.user.UserJoinRequest;
import com.example.mutsasns.entity.dto.user.UserJoinResponse;
import com.example.mutsasns.exception.AppException;
import com.example.mutsasns.exception.ErrorCode;
import com.example.mutsasns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    public UserJoinResponse add(UserJoinRequest userJoinRequest) {

        // userName 중복
        userRepository.findByUserName(userJoinRequest.getUserName()).ifPresent(user -> {
            throw new AppException(ErrorCode.DUPLICATED_USERNAME, ErrorCode.DUPLICATED_USERNAME.getMessage());
        });

//
        User user = User.builder()
                .userName(userJoinRequest.getUserName())
                .password(encoder.encode(userJoinRequest.getPassword()))
                .build();

        userRepository.save(user);

        return UserJoinResponse.of(user);
    }

}
