package com.example.websocketdemo.model.dto;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MessageDTO {
    private Long id;

    private String text;

    private LocalDate creationDate;

    private User author;

    private Channel channel;

    private boolean removed;

    public MessageDTO(Long id, String text, LocalDate creationDate, boolean removed, User author, Channel channel) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
        this.removed = removed;
        this.author = author;
        this.channel = channel;
    }

    public MessageDTO(Long id, String text, User author, Channel channel) {
        this(id, text, LocalDate.now(), false, author, channel);
    }

    public MessageDTO(Long id, String text, User author) {
        this(id, text, author, null);
    }

    public MessageDTO(Long id, String text) {
        this(id, text, null);
    }

    public MessageDTO(long id, boolean removed) {
        this(id, null, null, true, null, null);
    }

    public MessageDTO() {}
}
