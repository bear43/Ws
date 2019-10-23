package com.example.websocketdemo.repository;

import com.example.websocketdemo.model.Channel;
import org.springframework.data.repository.CrudRepository;

public interface ChannelRepository extends CrudRepository<Channel, Long> {
}
