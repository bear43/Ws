package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.dto.GeneralDTO;
import com.example.websocketdemo.model.dto.MessageDTO;
import com.example.websocketdemo.service.MessageService;
import com.example.websocketdemo.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {

    private final MessageService messageService;

    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @MessageMapping("/update/message")
    @SendTo("/topic/messages")
    public MessageDTO updater(MessageDTO message) throws Exception {
        Message messageInstance = messageService.editMessage(message.getId(), message.getText());
        return messageService.convertToDTO(messageInstance);
    }

    @MessageMapping("/create/message")
    @SendTo("/topic/messages")
    public MessageDTO creator(MessageDTO message) throws Exception {
        Message messageInstance = messageService.createMessage(message.getText(), userService.getCurrentUser());
        return messageService.convertToDTO(messageInstance);
    }

    @MessageMapping("/delete/message")
    @SendTo("/topic/messages")
    public MessageDTO deleter(MessageDTO message) throws Exception {
        messageService.removeMessage(message.getId());
        message = new MessageDTO(message.getId(), true);
        return message;
    }

    @MessageMapping("/read/messages")
    @SendTo("/topic/messages")
    public List<MessageDTO> readAll(GeneralDTO wrapper) {
        List<Message> messageList = messageService.readAllByChannel(wrapper.getId());
        List<MessageDTO> messageDTOList = new ArrayList<>();
        messageList.forEach(x -> messageDTOList.add(messageService.convertToDTO(x)));
        return messageDTOList;
    }

}
