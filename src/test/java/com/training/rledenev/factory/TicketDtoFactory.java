package com.training.rledenev.factory;

import com.training.rledenev.dto.FeedbackDto;
import com.training.rledenev.dto.TicketDto;
import com.training.rledenev.enums.Role;
import com.training.rledenev.enums.Status;

import java.time.LocalDate;
import java.util.ArrayList;

public class TicketDtoFactory {
    public static TicketDto getTicketDto() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setName("test7")
                .setId(7L)
                .setCreatedOn(LocalDate.of(2021,8,2))
                .setDesiredResolutionDate(LocalDate.of(2030, 11, 7))
                .setCategoryId(4L)
                .setUrgencyName("Average")
                .setDescription("any string")
                .setStatus(Status.APPROVED.toString())
                .setActions(new ArrayList<>())
                .setOwnerName("user1_mogilev@yopmail.com")
                .setApproverName("manager2_mogilev@yopmail.com")
                .setAssigneeName("Not assigned")
                .setAttachmentsDto(new ArrayList<>())
                .setAuthorizedUserRole(Role.EMPLOYEE)
                .setFeedbackDto(new FeedbackDto())
                .setAuthorizedUserName("user1_mogilev@yopmail.com");
        return ticketDto;
    }
}
