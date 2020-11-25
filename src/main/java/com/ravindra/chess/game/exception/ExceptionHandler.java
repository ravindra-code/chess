package com.ravindra.chess.game.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception e, WebRequest request) {

        String errorMessage = e.getLocalizedMessage();
        if (errorMessage==null)errorMessage=  e.toString();
        Error error = new Error(new Date(), errorMessage);

        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {InvalidMoveException.class,
                                        InvalidStrategyException.class})
    public ResponseEntity<Object> handleCustomExceptions(Exception e, WebRequest request) {

        String errorMessage = e.getLocalizedMessage();
        if (errorMessage==null)errorMessage=  e.toString();
        Error error = new Error(new Date(), errorMessage);

        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
