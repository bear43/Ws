package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.dto.GeneralDTO;
import com.example.websocketdemo.model.dto.MessageDTO;
import com.example.websocketdemo.service.MessageService;
import com.example.websocketdemo.service.UserService;
import com.example.websocketdemo.util.sender.Sender;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {

    private final MessageService messageService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final Sender sender;

    private static final String channelRoute = "/topic/channel/";

    public MessageController(MessageService messageService, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.sender = new Sender(simpMessagingTemplate);
    }

    private String makeRouteToChannel(Long channelId) throws Exception {
        if(channelId == null) throw new Exception("Wrong channel id");
        return channelRoute + channelId;
    }

    @MessageMapping("/update/message")
    public void updater(MessageDTO message, Principal principal) throws Exception {
        sender.sendToUserAndAll(
                makeRouteToChannel(message.getChannel().getId()),
                principal,
                messageService.editMessage(message));
    }

    @MessageMapping("/create/message")
    public void creator(MessageDTO message, Principal principal) throws Exception {
        sender.sendToUserAndAll(
                makeRouteToChannel(message.getChannel().getId()),
                principal,
                messageService.createMessage(message)
        );
    }

    @MessageMapping("/delete/message")
    public void deleter(MessageDTO message) throws Exception {
        messageService.removeMessage(message.getId());
        message.setRemoved(true);
        simpMessagingTemplate.convertAndSend(
                makeRouteToChannel(message.getChannel().getId()),
                message);
    }

    @MessageMapping("/read/messages")
    public void readAll(GeneralDTO wrapper, Principal principal) throws Exception{
        Optional<Long> channelId = Optional.of(wrapper.getId());
        List<MessageDTO> messageDTOList = messageService.readAllByChannel(channelId.orElseThrow(() -> new Exception("Wrong channel id!")));
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), makeRouteToChannel(channelId.get()), messageDTOList);
    }

}
