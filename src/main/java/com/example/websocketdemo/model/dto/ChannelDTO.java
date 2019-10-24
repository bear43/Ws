package com.example.websocketdemo.model.dto;

import com.example.websocketdemo.model.User;
import lombok.Data;

import java.time.LocalDate;


@Data
public class ChannelDTO {

    private Long id;

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
