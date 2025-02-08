package com.minipay.common.exception.handler;

import com.minipay.common.exception.DataNotFoundException;
import com.minipay.common.exception.DomainRuleException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@Hidden
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(DomainRuleException.class)
    ResponseEntity<ErrorResponse> handleDomainRuleException(DomainRuleException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                Objects.requireNonNullElse(e.getMessage(), "Domain rule violation")
        ));
    }

    @ExceptionHandler(DataNotFoundException.class)
    ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(
                HttpStatus.NOT_FOUND.value(),
                Objects.requireNonNullElse(e.getMessage(), "Request data not found")
        ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                Objects.requireNonNullElse(e.getMessage(), "Constraint violation")
        ));
    }
}
