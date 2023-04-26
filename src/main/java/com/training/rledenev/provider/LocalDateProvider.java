package com.training.rledenev.provider;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LocalDateProvider {

    public LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }
}
