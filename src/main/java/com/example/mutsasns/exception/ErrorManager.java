package com.example.mutsasns.exception;

import com.example.mutsasns.entity.dto.ErrorResponse;
import com.example.mutsasns.entity.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorManager {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Response> appExceptionHandler(AppException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getMessage());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Response.error(errorResponse));
    }
}
