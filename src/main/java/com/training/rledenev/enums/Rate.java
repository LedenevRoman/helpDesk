package com.training.rledenev.enums;

public enum Rate {
    VERY_GOOD("Very Good"),
    GOOD("Good"),
    NORMAL("Normal"),
    BAD("Bad"),
    VERY_BAD("Very Bad");

    private final String name;

    Rate(String name) {
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

