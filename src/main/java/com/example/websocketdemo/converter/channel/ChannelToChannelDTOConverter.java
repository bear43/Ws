package com.example.websocketdemo.converter.channel;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.dto.ChannelDTO;
import com.example.websocketdemo.model.dto.UserDTO;
import com.example.websocketdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChannelToChannelDTOConverter implements Converter<Channel, ChannelDTO> {



    @Autowired
    private ConversionService conversionService;

    @Autowired
    private UserService userService;

    @Override
    public ChannelDTO convert(Channel channel) {
        UserDTO userDTO = conversionService.convert(channel.getAuthor(), UserDTO.class);
        try {
            userDTO.isAuthor(userService.getCurrentUser().getId());
        } catch (Exception ignored) {

        }
        return new ChannelDTO(channel.getId(), channel.getTitle(), channel.getCreationDate(), userDTO);
    }
}
