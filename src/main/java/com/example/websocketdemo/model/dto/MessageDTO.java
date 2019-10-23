package com.example.websocketdemo.model.dto;

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

    private boolean removed;

    public MessageDTO(Long id, String text, LocalDate creationDate, boolean removed, User author) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
        this.removed = removed;
        this.author = author;
    }

    public MessageDTO(Long id, String text) {
        this(id, text, LocalDate.now(), false, null);
    }

    public MessageDTO(long id, boolean removed) {
        this(id, null, null, true, null);
    }

    public MessageDTO() {}
}
