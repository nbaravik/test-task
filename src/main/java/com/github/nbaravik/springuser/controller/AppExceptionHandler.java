package com.github.nbaravik.springuser.controller;

import com.github.nbaravik.springuser.dto.ErrorDTO;
import com.github.nbaravik.springuser.dto.ViolationErrorsDTO;
import com.github.nbaravik.springuser.exception.UserNotFoundException;
import com.github.nbaravik.springuser.dto.Violation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AppExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundExceptions(UserNotFoundException ex, HttpServletRequest request) {
        LOGGER.error("{}: {} {}: {}", HttpStatus.NOT_FOUND, request.getMethod(), request.getServletPath(), ex.getMessage());
        return new ResponseEntity(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintValidationException(ConstraintViolationException ex, HttpServletRequest request) {
        LOGGER.error("{}: {} {}: {}", HttpStatus.BAD_REQUEST, request.getMethod(), request.getServletPath(), ex.getMessage());
        List<Violation> violations = ex.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ResponseEntity(new ViolationErrorsDTO(violations), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        LOGGER.error("{}: {} {}: {}", HttpStatus.BAD_REQUEST, request.getMethod(), request.getServletPath(), ex.getMessage());
        return new ResponseEntity(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, HttpServletRequest request) {
        LOGGER.error("{}: {} {}: {}", HttpStatus.INTERNAL_SERVER_ERROR, request.getMethod(), request.getServletPath(), ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity(new ErrorDTO("Something went wrong. Please, try again."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
