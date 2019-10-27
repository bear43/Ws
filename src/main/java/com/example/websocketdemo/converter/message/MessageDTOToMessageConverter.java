package com.example.websocketdemo.converter.message;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.model.dto.ChannelDTO;
import com.example.websocketdemo.model.dto.MessageDTO;
import com.example.websocketdemo.model.dto.UserDTO;
import com.example.websocketdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class MessageDTOToMessageConverter implements Converter<MessageDTO, Message> {

    @Autowired
    private ConversionService conversionService;


    @Override
    public Message convert(MessageDTO messageDTO) {
        User user = conversionService.convert(messageDTO.getAuthor(), User.class);
        Channel channel = conversionService.convert(messageDTO.getChannel(), Channel.class);
        Message message = new Message(messageDTO.getData(), user,  channel, messageDTO.getMessageType());
        message.setId(messageDTO.getId());
        return message;
    }
}
