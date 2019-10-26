package com.example.websocketdemo.repository;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findAllByChannelId(Long id);
    @Query("SELECT CASE msg.author.id WHEN ?2 THEN TRUE ELSE FALSE END FROM Message msg WHERE msg.id=?1")
    boolean hasPermission(Long messageId, Long userId);
}
