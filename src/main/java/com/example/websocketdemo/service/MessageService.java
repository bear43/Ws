package com.example.websocketdemo.service;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.model.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    Message createMessage(String text, User author) throws Exception;
    Message editMessage(long id, String text) throws Exception;
    void removeMessage(long id) throws Exception;
    MessageDTO convertToDTO(Message message);
    Message convertToEntity(MessageDTO messageDTO);
    List<Message> readAll();
    List<Message> readAllByChannel(Long id);
}
