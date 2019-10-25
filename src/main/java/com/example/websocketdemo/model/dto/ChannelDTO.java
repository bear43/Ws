package com.example.websocketdemo.model.dto;

import com.example.websocketdemo.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Data
public class ChannelDTO extends GeneralRemovableDTO {

    private String title;

    private LocalDate creationDate;

    private UserDTO author;

    public ChannelDTO(Long id, String title, LocalDate creationDate, UserDTO author) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.author = author;
    }

    public ChannelDTO() {}
}
