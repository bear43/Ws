package com.example.websocketdemo.converter.channel;

import com.example.websocketdemo.converter.ConverterWrapper;
import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.dto.ChannelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChannelConverter implements ConverterWrapper<Channel, ChannelDTO> {

    @Autowired
    private ChannelToChannelDTOConverter channelToChannelDTOConverter;

    @Autowired
    private ChannelDTOToChannelConverter channelDTOToChannelConverter;

    @Override
    public Converter<Channel, ChannelDTO> getEntityToDTOInstance() {
        return channelToChannelDTOConverter;
    }

    @Override
    public Converter<ChannelDTO, Channel> getDTOToEntityInstance() {
        return channelDTOToChannelConverter;
    }
}
