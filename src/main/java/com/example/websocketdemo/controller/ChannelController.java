package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.dto.ChannelDTO;
import com.example.websocketdemo.model.dto.MessageDTO;
import com.example.websocketdemo.service.ChannelService;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ChannelController {

    private final ChannelService channelService;

    private final UserService userService;

    public ChannelController(ChannelService channelService, UserService userService) {
        this.channelService = channelService;
        this.userService = userService;
    }

    @MessageMapping("/update/channel")
    @SendTo("/topic/channels")
    public ChannelDTO updater(ChannelDTO channelDTO) throws Exception {
        return channelService.editChannel(channelDTO);
    }

    @MessageMapping("/create/channel")
    @SendTo("/topic/channels")
    public ChannelDTO creator(ChannelDTO channelDTO) throws Exception {
        return channelService.createChannel(channelDTO.getTitle(), userService.getCurrentUser());
    }

    @MessageMapping("/delete/channel")
    @SendTo("/topic/channels")
    public ChannelDTO deleter(ChannelDTO channelDTO) throws Exception {
        channelService.removeChannel(channelDTO);
        channelDTO.setRemoved(true);
        return channelDTO;
    }

    @MessageMapping("/read/channels")
    @SendTo("/topic/channels")
    public List<ChannelDTO> readAll() {
        List<Channel> channelList = channelService.readAll();
        List<ChannelDTO> channelDTOList = new ArrayList<>();
        channelList.forEach(x -> {
            try {
                channelDTOList.add(channelService.convertToDTO(x));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        return channelDTOList;
    }



}
