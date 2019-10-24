package com.example.websocketdemo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Channel extends Audit {

    @Column
    private String title;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messageList;

    public Channel(String title, User author) {
        this.title = title;
        this.author = author;
    }

    public Channel(Long id, String title, LocalDate creationDate, User author) {
        super(id, creationDate, null);
        this.title = title;
        this.author = author;
    }

    public Channel(String title) {
        this(title, null);
    }

    public Channel() {}
}
