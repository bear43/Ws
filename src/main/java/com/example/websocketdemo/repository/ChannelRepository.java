package com.example.websocketdemo.repository;

import com.example.websocketdemo.model.Channel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChannelRepository extends CrudRepository<Channel, Long> {
    boolean existsByTitle(String title);
    @Query("SELECT channel.title FROM Channel channel WHERE channel.id=?1")
    String getTitleById(long id);
    @Query("SELECT CASE channel.author.id WHEN ?1 THEN TRUE ELSE FALSE END FROM Channel channel WHERE channel.id=?2")
    boolean hasPermission(long userId, long channelId);
}
