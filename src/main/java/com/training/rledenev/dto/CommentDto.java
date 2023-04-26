package com.training.rledenev.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

public class CommentDto {

    @Size(max = 500, message = "Comment can not be longer than 500 characters")
    @Pattern(
            regexp = "[a-zA-Z0-9~.\"(),:;<>@\\[\\]!#$%&'*+\\-/=?^_`{|}\\s]*",
            message = "Comment contains invalid characters"
    )
    private String text;
    private LocalDate date;
    private String userEmail;
    public String getText() {
        return text;
    }

    public CommentDto setText(String text) {
        this.text = text;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public CommentDto setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public CommentDto setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
        CommentDto that = (CommentDto) o;
        return Objects.equals(text, that.text)
                && Objects.equals(date, that.date)
                && Objects.equals(userEmail, that.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, date, userEmail);
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "text='" + text + '\'' +
                ", localDateTime=" + date +
                ", user=" + userEmail +
                '}';
    }
}
