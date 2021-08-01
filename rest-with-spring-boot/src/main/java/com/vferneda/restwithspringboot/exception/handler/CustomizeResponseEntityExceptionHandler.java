package com.vferneda.restwithspringboot.exception.handler;

import com.vferneda.restwithspringboot.exception.ExceptionResponse;
import com.vferneda.restwithspringboot.exception.InvalidJwtAuthenticationException;
import com.vferneda.restwithspringboot.exception.UnsuportedMathOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exc, WebRequest request) {
        final ExceptionResponse excRes = new ExceptionResponse(LocalDateTime.now(), exc.getMessage(), request.getDescription(Boolean.FALSE));
        return new ResponseEntity<>(excRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsuportedMathOperationException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception exc, WebRequest request) {
        final ExceptionResponse excRes = new ExceptionResponse(LocalDateTime.now(), exc.getMessage(), request.getDescription(Boolean.FALSE));
        return new ResponseEntity<>(excRes, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public final ResponseEntity<ExceptionResponse> invalidJwtAuthenticationException(Exception exc, WebRequest request) {
        final ExceptionResponse excRes = new ExceptionResponse(LocalDateTime.now(), exc.getMessage(), request.getDescription(Boolean.FALSE));
        return new ResponseEntity<>(excRes, HttpStatus.BAD_REQUEST);
    }

}
