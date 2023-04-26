package com.training.rledenev.enums;

public enum Urgency {
    CRITICAL("Critical"),
    HIGH("High"),
    AVERAGE("Average"),
    LOW("Low");

    private final String name;

    Urgency(String name) {
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
