package com.claims.stoploss_monitor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            MemberNotFoundException.class
    )
    public ResponseEntity<String>
    handleMemberNotFound(
            MemberNotFoundException ex
    ) {

        return ResponseEntity
                .status(
                        HttpStatus.NOT_FOUND
                )
                .body(
                        ex.getMessage()
                );
    }

    @ExceptionHandler(
            Exception.class
    )
    public ResponseEntity<String>
    handleGenericException(
            Exception ex
    ) {

        return ResponseEntity
                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
                .body(
                        "Something went wrong"
                );
    }
    @ExceptionHandler(
            AccessDeniedException.class
    )
    public ResponseEntity<String>
    handleAccessDenied(
            AccessDeniedException ex
    ) {

        return ResponseEntity
                .status(403)
                .body(ex.getMessage());
    }
}