package com.training.rledenev.factory;

import com.training.rledenev.dto.CreateTicketDto;

import java.time.LocalDate;

public class CreateTicketDtoFactory {

    public static CreateTicketDto getCreateTicket() {
        CreateTicketDto createTicketDto = new CreateTicketDto();
        createTicketDto.setCategoryId(1L);
        createTicketDto.setUrgencyName("Critical");
        createTicketDto.setDesiredResolutionDate(LocalDate.now().plusDays(1L));
        createTicketDto.setName("name");
        createTicketDto.setCommentText("");
        createTicketDto.setDescription("description");
        return createTicketDto;
    }
}
