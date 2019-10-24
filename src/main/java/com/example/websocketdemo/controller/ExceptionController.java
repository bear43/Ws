package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.dto.ExceptionBody;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    @MessageExceptionHandler
    @SendToUser("/queue/error")
    public ExceptionBody exceptionBody(Exception ex){
        return new ExceptionBody(LocalDateTime.now(), ex.getMessage());
    }

}
