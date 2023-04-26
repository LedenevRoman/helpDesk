package com.training.rledenev.converter;

import com.training.rledenev.dto.CreateTicketDto;
import com.training.rledenev.enums.Urgency;
import com.training.rledenev.exception.WrongDesiredResolutionDate;
import com.training.rledenev.model.Category;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.provider.LocalDateProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;

@Component
public class CreateTicketConverter {

    private final LocalDateProvider localDateProvider;

    public CreateTicketConverter(LocalDateProvider localDateProvider) {
        this.localDateProvider = localDateProvider;
    }

    public Ticket fromDto(CreateTicketDto createTicketDto) {
        if (createTicketDto == null) {
            return new Ticket();
        }
        Ticket ticket = new Ticket();

        validateData(createTicketDto);

        ticket.setName(createTicketDto.getName())
                .setDescription(createTicketDto.getDescription())
                .setDesiredResolutionDate(createTicketDto.getDesiredResolutionDate())
                .setCategory(new Category().setId(createTicketDto.getCategoryId()))
                .setUrgency(Urgency.valueOf(getEnumName(createTicketDto.getUrgencyName())))
                .setCreatedOn(LocalDate.now());
        return ticket;
    }

    private void validateData(CreateTicketDto createTicketDto) {
        if (createTicketDto.getDesiredResolutionDate() == null) {
            createTicketDto.setDesiredResolutionDate(localDateProvider.getCurrentLocalDate().plusYears(10));
        }
        if (createTicketDto.getDesiredResolutionDate().compareTo(localDateProvider.getCurrentLocalDate()) < 0) {
            throw new WrongDesiredResolutionDate("Desired date can not be less than the current date");
        }
    }

    private String getEnumName(String urgencyName) {
        return urgencyName.toUpperCase(Locale.ROOT);
    }
}
