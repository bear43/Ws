package com.example.websocketdemo.converter;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.dto.MessageDTO;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.core.convert.converter.Converter;

public class MessageToMessageDTOConverter implements Converter<Message, MessageDTO> {
    @Override
    public MessageDTO convert(Message message) {
        return new MessageDTO(message.getId(), message.getText(), message.getCreationTime(), false);
    }


}
