package com.example.websocketdemo.converter.channel;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.dto.ChannelDTO;
import org.springframework.core.convert.converter.Converter;

public class ChannelDTOToChannelConverter implements Converter<ChannelDTO, Channel> {
    @Override
    public Channel convert(ChannelDTO channelDTO) {
        return new Channel(channelDTO.getId(), channelDTO.getTitle(), channelDTO.getCreationDate(), channelDTO.getAuthor());
    }
}
