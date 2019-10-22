package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.dto.MessageDTO;
import com.example.websocketdemo.service.MessageService;
import com.example.websocketdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdaterController {

    private final MessageService messageService;

    private final UserService userService;

    public UpdaterController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @MessageMapping("/update")
    @SendTo("/sender/update")
    public MessageDTO updater(MessageDTO message) throws Exception {
        Message messageInstance = messageService.editMessage(message.getId(), message.getText());
        return messageService.convertToDTO(messageInstance);
    }

    @MessageMapping("/create")
    @SendTo("/sender/update")
    public MessageDTO creator(MessageDTO message) throws Exception {
        Message messageInstance = messageService.createMessage(message.getText(), userService.getCurrentUser());
        return messageService.convertToDTO(messageInstance);
    }

    @MessageMapping("/delete")
    @SendTo("/sender/update")
    public MessageDTO deleter(MessageDTO message) throws Exception {
        messageService.removeMessage(message.getId());
        message = new MessageDTO(message.getId(), true);
        return message;
    }

    @GetMapping("/test")
    public void test() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
    }
}
