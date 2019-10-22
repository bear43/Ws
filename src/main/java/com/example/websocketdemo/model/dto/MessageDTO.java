package com.example.websocketdemo.model.dto;

import com.example.websocketdemo.model.Message;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MessageDTO {
    private Long id;

    private String text;

    private LocalDate creationDate;

    private boolean removed;

    public MessageDTO(Long id, String text, LocalDate creationDate, boolean removed) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
        this.removed = removed;
    }

    public MessageDTO(Long id, String text) {
        this(id, text, LocalDate.now(), false);
    }

    public MessageDTO(long id, boolean removed) {
        this(id, null, null, true);
    }

    public MessageDTO() {}
}
