package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.dto.ChannelDTO;
import com.example.websocketdemo.service.ChannelService;
import com.example.websocketdemo.service.UserService;
import com.example.websocketdemo.util.sender.Sender;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChannelController {

    private final ChannelService channelService;

    private final UserService userService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final Sender sender;

    private final String route = "/topic/channels";

    public ChannelController(ChannelService channelService, UserService userService, SimpMessagingTemplate simpMessagingTemplate) {
        this.channelService = channelService;
        this.userService = userService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.sender = new Sender(simpMessagingTemplate);
    }

    @MessageMapping("/update/channel")
    public void updater(ChannelDTO channelDTO, Principal principal) throws Exception {
        sender.sendToUserAndAll(
                route,
                principal,
                channelService.editChannel(channelDTO)
        );
    }

    @MessageMapping("/create/channel")
    public void creator(ChannelDTO channelDTO, Principal principal) throws Exception {
        sender.sendToUserAndAll(
                route,
                principal,
                channelService.createChannel(channelDTO.getTitle(), userService.getCurrentUser())
        );
    }

    @MessageMapping("/delete/channel")
    @SendTo("/topic/channels")
    public ChannelDTO deleter(ChannelDTO channelDTO) throws Exception {
        channelService.removeChannel(channelDTO.getId());
        channelDTO.setRemoved(true);
        return channelDTO;
    }

    @MessageMapping("/read/channels")
    @SendToUser("/topic/channels")
    public List<ChannelDTO> readAll() throws Exception {
        return channelService.readAll().stream().map(channelService::convertToDTO).collect(Collectors.toList());
    }



}
