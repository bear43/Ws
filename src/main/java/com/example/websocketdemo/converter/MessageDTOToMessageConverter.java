package com.example.websocketdemo.converter;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.dto.MessageDTO;
import org.springframework.core.convert.converter.Converter;

public class MessageDTOToMessageConverter implements Converter<MessageDTO, Message> {
    @Override
    public Message convert(MessageDTO messageDTO) {
        Message message = new Message(messageDTO.getText(), messageDTO.getAuthor());
        message.setId(messageDTO.getId());
        return message;
    }
}
