package com.example.websocketdemo.service.implementation;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.repository.MessageRepository;
import com.example.websocketdemo.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @Override
    public Message createMessage(String text, User author) {
        Message message = new Message(text, author);
        messageRepository.save(message);
        return message;
    }

    @Override
    public Message editMessage(long id, String text) throws Exception {
        Message message = messageRepository.findById(id).orElseThrow(() -> new Exception("Cannot find message with id " + id));
        message.setText(text);
        messageRepository.save(message);
        return message;
    }

    @Override
    public void removeMessage(long id) throws Exception {
        messageRepository.deleteById(id);
    }
}
