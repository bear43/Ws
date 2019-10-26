package com.example.websocketdemo.model.dto;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageDTO extends GeneralRemovableAuthoredDTO {

    private String text;

    private LocalDate creationDate;

    private ChannelDTO channel;

    public MessageDTO(Long id, String text, LocalDate creationDate, boolean removed, UserDTO author, ChannelDTO channel) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
        this.removed = removed;
        this.author = author;
        this.channel = channel;
    }

    public MessageDTO(Long id, String text, UserDTO author, ChannelDTO channel) {
        this(id, text, LocalDate.now(), false, author, channel);
    }

    public MessageDTO(Long id, String text, UserDTO author) {
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
