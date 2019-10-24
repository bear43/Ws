package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.dto.MessageDTO;
import com.example.websocketdemo.service.MessageService;
import com.example.websocketdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UpdaterController {

    private final MessageService messageService;

    private final UserService userService;

    public UpdaterController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @MessageMapping("/update")
    @SendTo("/topic/message")
    public MessageDTO updater(MessageDTO message) throws Exception {
        Message messageInstance = messageService.editMessage(message.getId(), message.getText());
        return messageService.convertToDTO(messageInstance);
    }

    @MessageMapping("/create")
    @SendTo("/topic/message")
    public MessageDTO creator(MessageDTO message) throws Exception {
        Message messageInstance = messageService.createMessage(message.getText(), userService.getCurrentUser());
        return messageService.convertToDTO(messageInstance);
    }

    @MessageMapping("/delete")
    @SendTo("/topic/message")
    public MessageDTO deleter(MessageDTO message) throws Exception {
        messageService.removeMessage(message.getId());
        message = new MessageDTO(message.getId(), true);
        return message;
    }

    @MessageMapping("/read/messages")
    @SendTo("/topic/message")
    public List<MessageDTO> readAll() {
        List<Message> messageList = messageService.readAll();
        List<MessageDTO> messageDTOList = new ArrayList<>();
        messageList.forEach(x -> messageDTOList.add(messageService.convertToDTO(x)));
        return messageDTOList;
    }

}
