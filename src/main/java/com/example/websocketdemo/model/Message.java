package com.example.websocketdemo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

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

    @ManyToOne
    private Channel channel;

    public Message(String text, User author, Channel channel) {
        this.text = text;
        this.author = author;
        this.channel = channel;
    }

    public Message(String text, User author) {
        this(text, author, null);
    }

    public Message(String text) {
        this(text, null);
    }

    public Message() {}
}
