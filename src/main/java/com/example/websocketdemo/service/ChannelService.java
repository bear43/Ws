package com.example.websocketdemo.service;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.model.dto.ChannelDTO;

import java.util.List;

public interface ChannelService {
    ChannelDTO createChannel(String title, User author) throws Exception;
    ChannelDTO editChannel(ChannelDTO channel) throws Exception;
    void removeChannel(ChannelDTO channel) throws Exception;
    List<Channel> readAll();
    ChannelDTO convertToDTO(Channel channel) throws Exception;
    Channel convertToEntity(ChannelDTO channelDTO) throws Exception;
}
