package com.example.websocketdemo.service;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.dto.MessageDTO;
import com.example.websocketdemo.util.permission.Permissionable;

import java.util.List;

public interface MessageService extends Permissionable {
    MessageDTO createMessage(MessageDTO messageDTO) throws Exception;
    MessageDTO editMessage(MessageDTO messageDTO) throws Exception;
    void removeMessage(long id) throws Exception;
    MessageDTO convertToDTO(Message message);
    Message convertToEntity(MessageDTO messageDTO);
    List<Message> readAll();
    List<MessageDTO> readAllByChannel(Long id);
}
