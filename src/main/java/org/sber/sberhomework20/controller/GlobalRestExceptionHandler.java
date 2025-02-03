package org.sber.sberhomework20.controller;

import lombok.extern.slf4j.Slf4j;
import org.sber.sberhomework20.dto.Response;
import org.sber.sberhomework20.exception.UserAlreadyExistsException;
import org.sber.sberhomework20.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalRestExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> exceptionHandler(UserNotFoundException ex) {
        Response response = Response.builder()
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Response> exceptionHandler(UserAlreadyExistsException ex) {
        Response response = Response.builder()
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> exceptionHandler(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }

        Response response = Response.builder()
                .message(errors.toString())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response> exceptionHandler(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        Response response = Response.builder()
                .message("Внутренняя ошибка сервера")
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Response> exceptionHandler(NoResourceFoundException ex) {
        Response response = Response.builder()
                .message("Ресурс не найден")
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}