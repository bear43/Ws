package com.example.websocketdemo.converter.channel;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.model.dto.ChannelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChannelDTOToChannelConverter implements Converter<ChannelDTO, Channel> {

    @Autowired
    private ConversionService conversionService;

    @Override
    public Channel convert(ChannelDTO channelDTO) {
        User user = conversionService.convert(channelDTO.getAuthor(), User.class);
        return new Channel(channelDTO.getId(), channelDTO.getTitle(), channelDTO.getCreationDate(), user);
    }
}
