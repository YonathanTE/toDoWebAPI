/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ye.todowebapi.controller;

/**
 *
 * @author Yonathan
 */
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice // Annotation to enable this class to help controller
@RestController // Enables HTTP response
public class ToDoControllerExceptionHandler extends ResponseEntityExceptionHandler {
    // Custom error message
    private static final String msg = "Could not be saved. Please revise syntax and try again.";
    
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class) // Annotating the method so the exception handled is provided
    public final ResponseEntity<Error> handleSqlException(
            SQLIntegrityConstraintViolationException ex,
            WebRequest request) {
        
        Error error = new Error();
        error.setMessage(msg);
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
