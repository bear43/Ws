package com.example.websocketdemo.service;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.model.dto.ChannelDTO;
import com.example.websocketdemo.util.permission.Permissionable;

import java.util.List;

public interface ChannelService extends Permissionable {
    ChannelDTO createChannel(String title, User author) throws Exception;
    ChannelDTO editChannel(ChannelDTO channel) throws Exception;
    void removeChannel(long id) throws Exception;
    List<Channel> readAll();
    ChannelDTO convertToDTO(Channel channel);
    Channel convertToEntity(ChannelDTO channelDTO);
}
