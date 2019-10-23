package com.example.websocketdemo.service;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.dto.ChannelDTO;

public interface ChannelService {
    Channel createChannel(String title) throws Exception;
    Channel editChannel(Channel channel) throws Exception;
    void removeChannel(Channel channel) throws Exception;
    ChannelDTO convertToDTO(Channel channel) throws Exception;
    Channel convertToEntity(ChannelDTO channelDTO) throws Exception;
}
