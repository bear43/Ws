package com.example.websocketdemo.repository;

import com.example.websocketdemo.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
