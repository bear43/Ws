package com.example.websocketdemo.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@Data
public class Audit {

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate creationDate;

    @UpdateTimestamp
    private LocalDate updateDate;

    public Audit(Long id,LocalDate creationDate, LocalDate updateDate) {
        this.id = id;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public Audit() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audit audit = (Audit) o;
        return Objects.equals(id, audit.id) &&
                Objects.equals(creationDate, audit.creationDate) &&
                Objects.equals(updateDate, audit.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, updateDate);
    }
}
