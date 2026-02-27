package com.allobank.finance.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final String errorSeparator;

    public GlobalExceptionHandler(@Value("${allobank.developer.username}") String username) {
        this.errorSeparator = username;
    }

    @ExceptionHandler(FinanceDataNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(FinanceDataNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("separator", errorSeparator);
        errorResponse.put("status", 404);
        errorResponse.put("error", "Resource Tidak Ditemukan");
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralError(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("separator", errorSeparator);
        errorResponse.put("status", 500);
        errorResponse.put("error", "Terjadi Kesalahan Internal");
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}