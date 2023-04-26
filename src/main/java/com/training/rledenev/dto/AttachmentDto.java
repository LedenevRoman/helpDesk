package com.training.rledenev.dto;

import java.util.Objects;

public class AttachmentDto {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public AttachmentDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AttachmentDto setName(String name) {
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
        AttachmentDto that = (AttachmentDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AttachmentDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
