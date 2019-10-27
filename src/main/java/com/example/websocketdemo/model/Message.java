package com.example.websocketdemo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Message extends Audit {

    @Column
    private Byte[] data;

    @ManyToOne
    private User author;

    @ManyToOne
    private Channel channel;

    @Column
    private MessageType messageType;

    public Message(Byte[] data, User author, Channel channel, MessageType messageType) {
        this.data = data;
        this.author = author;
        this.channel = channel;
        this.messageType = messageType;
    }

    public Message(Byte[] data, User author) {
        this(data, author, null, MessageType.TEXT);
    }

    public Message(Byte[] data) {
        this(data, null);
    }

    public Message() {}
}
