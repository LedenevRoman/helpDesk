package com.training.rledenev.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "HISTORIES")
public class History implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ticket ticket;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "ACTION")
    private String action;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "DESCRIPTION")
    private String description;

    public Long getId() {
        return id;
    }

    public History setId(Long id) {
        this.id = id;
        return this;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public History setTicket(Ticket ticket) {
        this.ticket = ticket;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public History setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getAction() {
        return action;
    }

    public History setAction(String action) {
        this.action = action;
        return this;
    }

    public User getUser() {
        return user;
    }

    public History setUser(User user) {
        this.user = user;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public History setDescription(String description) {
        this.description = description;
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
        History history = (History) o;
        return Objects.equals(id, history.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                '}';
    }
}
