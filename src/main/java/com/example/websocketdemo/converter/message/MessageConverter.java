package com.example.websocketdemo.converter.message;

import com.example.websocketdemo.converter.ConverterWrapper;
import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageConverter implements ConverterWrapper<Message, MessageDTO> {

    @Autowired
    private MessageToMessageDTOConverter messageToMessageDTOConverter;

    @Autowired
    private MessageDTOToMessageConverter messageDTOToMessageConverter;

    @Override
    public Converter<Message, MessageDTO> getEntityToDTOInstance() {
        return messageToMessageDTOConverter;
    }

    @Override
    public Converter<MessageDTO, Message> getDTOToEntityInstance() {
        return messageDTOToMessageConverter;
    }

    @Bean
    public MessageConverter getMessageConverter() {
        return new MessageConverter();
    }
}
