package com.example.websocketdemo.service.implementation;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.model.dto.MessageDTO;
import com.example.websocketdemo.repository.MessageRepository;
import com.example.websocketdemo.service.MessageService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final ConversionService conversionService;

    public MessageServiceImpl(MessageRepository messageRepository, ConversionService conversionService) {
        this.messageRepository = messageRepository;
        this.conversionService = conversionService;
    }

    private void emptyChecker(String text) throws Exception {
        text = text.trim();
        if(text.isEmpty()) throw new Exception("Empty message are not allowed");

    }


    @Override
    public Message createMessage(String text, User author) throws Exception {
        emptyChecker(text);
        Message message = new Message(text, author);
        messageRepository.save(message);
        return message;
    }

    @Override
    public Message editMessage(long id, String text) throws Exception {
        emptyChecker(text);
        Message message = messageRepository.findById(id).orElseThrow(() -> new Exception("Cannot find message with id " + id));
        message.setText(text);
        messageRepository.save(message);
        return message;
    }

    @Override
    public void removeMessage(long id) throws Exception {
        messageRepository.deleteById(id);
    }

    @Override
    public MessageDTO convertToDTO(Message message) {
        return conversionService.convert(message, MessageDTO.class);
    }

    @Override
    public Message convertToEntity(MessageDTO messageDTO) {
        return conversionService.convert(messageDTO, Message.class);
    }

    @Override
    public List<Message> readAll() {
        List<Message> messageList = new ArrayList<>();
        messageRepository.findAll().forEach(messageList::add);
        return messageList;
    }
}
