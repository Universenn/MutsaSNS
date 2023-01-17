package com.example.mutsasns.service;

import com.example.mutsasns.entity.User;
import com.example.mutsasns.entity.dto.user.UserRequest;
import com.example.mutsasns.entity.dto.user.UserJoinResponse;
import com.example.mutsasns.entity.dto.user.UserLoginResponse;
import com.example.mutsasns.exception.AppException;
import com.example.mutsasns.exception.ErrorCode;
import com.example.mutsasns.repository.UserRepository;
import com.example.mutsasns.security.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;

    private Long expireTimesMs = 1000 * 60 * 60L; // 1000*60*60 = 1시간

    public UserJoinResponse join(UserRequest dto) {

        // userName 중복
        userRepository.findByUserName(dto.getUserName()).ifPresent(user -> {
            throw new AppException(ErrorCode.DUPLICATED_USERNAME, ErrorCode.DUPLICATED_USERNAME.getMessage());
        });


        String encodePw = encoder.encode(dto.getPassword());
        User user = userRepository.save(dto.toEntity(encodePw));

        return UserJoinResponse.of(user);
    }

    public UserLoginResponse login(UserRequest dto) {

        // userName 찾을 수 없음
        User user = userRepository.findByUserName(dto.getUserName())
                .orElseThrow(()-> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));


        // password 틀림
        log.info("selectedPw : {}, pw : {}", user.getPassword(), dto.getPassword());
        if (!encoder.matches(dto.getPassword(), user.getPassword()))
            throw new AppException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());

        String token = JwtTokenUtil.createToken(dto.getUserName(), secretKey, expireTimesMs);

        return new UserLoginResponse(token);
    }
}
