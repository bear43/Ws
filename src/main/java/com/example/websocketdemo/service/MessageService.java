package com.example.websocketdemo.service;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.User;

public interface MessageService {
    Message createMessage(String text, User author) throws Exception;
    Message editMessage(long id, String text) throws Exception;
    void removeMessage(long id) throws Exception;
}
