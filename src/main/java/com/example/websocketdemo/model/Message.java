package com.example.websocketdemo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Message extends Audit {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String text;

    @CreationTimestamp
    @Column
    private LocalDate creationTime;

    @ManyToOne
    private User author;

    public Message(String text, User author) {
        this.text = text;
        this.author = author;
    }

    public Message(String text) {
        this(text, null);
    }

    public Message() {}
}
