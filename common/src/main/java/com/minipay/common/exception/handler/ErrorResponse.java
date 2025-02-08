package com.minipay.common.exception.handler;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String message
) {
    public static ErrorResponse of(int status, String message) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .message(message)
                .build();
    }
}
