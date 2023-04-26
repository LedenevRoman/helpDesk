package com.training.rledenev.enums;

public enum Status {
    DRAFT("Draft"),
    NEW("New"),
    APPROVED("Approved"),
    DECLINED("Declined"),
    IN_PROGRESS("In progress"),
    DONE("Done"),
    CANCELED("Canceled");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
