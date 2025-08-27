package com.trackingservices.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@RestControllerAdvice
public class ValidationException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ConcurrentHashMap<String, String> handleValidation(MethodArgumentNotValidException ex) {
        ConcurrentHashMap<String, String> ermap = new ConcurrentHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            ermap.put(error.getField(), Objects.requireNonNull(error.getDefaultMessage()));
        });
        return ermap;
    }

}
