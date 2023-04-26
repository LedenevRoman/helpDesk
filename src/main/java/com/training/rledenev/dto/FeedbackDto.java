package com.training.rledenev.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class FeedbackDto {

    private Long id;
    @NotBlank
    private String rateName;
    private String text;

    public Long getId() {
        return id;
    }

    public FeedbackDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRateName() {
        return rateName;
    }

    public FeedbackDto setRateName(String rateName) {
        this.rateName = rateName;
        return this;
    }

    public String getText() {
        return text;
    }

    public FeedbackDto setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackDto that = (FeedbackDto) o;
        return Objects.equals(id, that.id) && Objects.equals(rateName, that.rateName) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rateName, text);
    }

    @Override
    public String toString() {
        return "FeedbackDto{" +
                "id=" + id +
                ", rate=" + rateName +
                ", text='" + text + '\'' +
                '}';
    }
}
