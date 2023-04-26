package com.training.rledenev.dto;

import java.time.LocalDate;
import java.util.Objects;

public class HistoryDto {

    private LocalDate date;
    private String action;
    private String email;
    private String description;

    public HistoryDto setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public HistoryDto setAction(String action) {
        this.action = action;
        return this;
    }

    public HistoryDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public HistoryDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAction() {
        return action;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HistoryDto that = (HistoryDto) o;
        return Objects.equals(date, that.date)
                && Objects.equals(action, that.action)
                && Objects.equals(email, that.email)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, action, email, description);
    }

    @Override
    public String toString() {
        return "HistoryDto{" +
                "date=" + date +
                ", action='" + action + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
