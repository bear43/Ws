package com.example.websocketdemo.converter.message;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.User;
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
        Message message = new Message(messageDTO.getText(), user, messageDTO.getChannel());
        message.setId(messageDTO.getId());
        return message;
    }
}
