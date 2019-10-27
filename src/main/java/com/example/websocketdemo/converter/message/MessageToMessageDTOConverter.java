package com.example.websocketdemo.converter.message;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.dto.ChannelDTO;
import com.example.websocketdemo.model.dto.MessageDTO;
import com.example.websocketdemo.model.dto.UserDTO;
import com.example.websocketdemo.service.MessageService;
import com.example.websocketdemo.service.UserService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageToMessageDTOConverter implements Converter<Message, MessageDTO> {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private UserService userService;


    @Override
    public MessageDTO convert(Message message) {
        UserDTO userDTO = conversionService.convert(message.getAuthor(), UserDTO.class);
        try {
            userDTO.isAuthor(userService.getCurrentUser().getId());
        } catch (Exception ignored) {

        }
        ChannelDTO channelDTO = conversionService.convert(message.getChannel(), ChannelDTO.class);
        return new MessageDTO(message.getId(), message.getData(), message.getCreationDate(), false, userDTO, channelDTO, message.getMessageType());
    }


}
