package com.training.rledenev.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ATTACHMENTS")
public class Attachment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Lob
    @Column(name = "BLOB", columnDefinition = "BLOB")
    private byte[] blob;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ticket ticket;

    @Column(name = "NAME")
    private String name;

    public Long getId() {
        return id;
    }

    public Attachment setId(Long id) {
        this.id = id;
        return this;
    }

    public byte[] getBlob() {
        return blob;
    }

    public Attachment setBlob(byte[] blob) {
        this.blob = blob;
        return this;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Attachment setTicket(Ticket ticket) {
        this.ticket = ticket;
        return this;
    }

    public String getName() {
        return name;
    }

    public Attachment setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attachment that = (Attachment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
