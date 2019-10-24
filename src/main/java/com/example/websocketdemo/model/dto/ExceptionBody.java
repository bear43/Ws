package com.example.websocketdemo.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionBody {
    /**
     * Exception creation time
     */
    private LocalDateTime dateTime;
    /**
     * Exception message
     */
    private String message;

    public ExceptionBody(LocalDateTime dateTime, String message) {
        this.dateTime = dateTime;
        this.message = message;
    }

    public ExceptionBody() {}
}
