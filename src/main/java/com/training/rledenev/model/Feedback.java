package com.training.rledenev.model;

import com.training.rledenev.enums.Rate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "FEEDBACKS")
public class Feedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "RATE")
    private Rate rate;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "TEXT")
    private String text;

    @OneToOne(fetch = FetchType.LAZY)
    private Ticket ticket;

    public Long getId() {
        return id;
    }

    public Feedback setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Feedback setUser(User user) {
        this.user = user;
        return this;
    }

    public Rate getRate() {
        return rate;
    }

    public Feedback setRate(Rate rate) {
        this.rate = rate;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Feedback setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getText() {
        return text;
    }

    public Feedback setText(String text) {
        this.text = text;
        return this;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Feedback setTicket(Ticket ticket) {
        this.ticket = ticket;
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
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                '}';
    }
}
