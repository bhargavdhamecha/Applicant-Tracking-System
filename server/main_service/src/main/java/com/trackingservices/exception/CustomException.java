package com.trackingservices.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ControllerAdvice
public class CustomException{

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException ex){
        String errorMessage = "ID not found: "+ ex.getMessage();
        return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerError(Exception ex) {
        String errorMessage = "Internal Server error" + ex.getMessage();
        return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(Exception ex) {
        String errorMessage = "SQL Exception" + ex.getMessage();
        return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}