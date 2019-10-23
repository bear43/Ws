package com.example.websocketdemo.converter.channel;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.dto.ChannelDTO;
import org.springframework.core.convert.converter.Converter;

public class ChannelToChannelDTOConverter implements Converter<Channel, ChannelDTO> {
    @Override
    public ChannelDTO convert(Channel channel) {
        return new ChannelDTO(channel.getId(), channel.getTitle(), channel.getCreationDate(), channel.getAuthor());
    }
}
