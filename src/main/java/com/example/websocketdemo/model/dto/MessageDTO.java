package com.example.websocketdemo.model.dto;

import com.example.websocketdemo.model.MessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageDTO extends GeneralRemovableAuthoredDTO {

    private Byte[] data;

    private LocalDate creationDate;

    private ChannelDTO channel;

    private MessageType messageType;

    public MessageDTO(Long id, Byte[] data, LocalDate creationDate, boolean removed, UserDTO author, ChannelDTO channel, MessageType messageType) {
        this.id = id;
        this.data = data;
        this.creationDate = creationDate;
        this.removed = removed;
        this.author = author;
        this.channel = channel;
        this.messageType = messageType;
    }

    public MessageDTO(Long id, Byte[] data, UserDTO author, ChannelDTO channel) {
        this(id, data, LocalDate.now(), false, author, channel, MessageType.TEXT);
    }

    public MessageDTO(Long id, Byte[] data, UserDTO author) {
        this(id, data, author, null);
    }

    public MessageDTO(Long id, Byte[] data) {
        this(id, data, null);
    }

    public MessageDTO(long id, boolean removed) {
        this(id, null, null, true, null, null, MessageType.TEXT);
    }

    public MessageDTO() {}
}
