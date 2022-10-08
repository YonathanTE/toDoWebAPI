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
import java.time.*;

public class Error {
    // Record the time when the custom error is returned
    private LocalDateTime timestamp = LocalDateTime.now();
    // Stores what the custom error will say
    private String message;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
