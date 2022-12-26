package com.example.mutsasns.entity.dto;

import com.example.mutsasns.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private ErrorCode errorCode;
    private String message;

    public ErrorResponse(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
