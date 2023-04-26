package com.training.rledenev.factory;

import com.training.rledenev.enums.Role;
import com.training.rledenev.enums.Status;
import com.training.rledenev.enums.Urgency;
import com.training.rledenev.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TicketFactory {

    public static Ticket getTicket() {
        Ticket ticket = new Ticket();
        ticket.setStatus(Status.NEW);
        return ticket;
    }

    public static Ticket getDraftTicket() {
        Ticket ticket = new Ticket();
        User user = new User();

        ticket.setId(31L);
        ticket.setCategory(new Category().setId(1L));
        ticket.setUrgency(Urgency.CRITICAL);
        ticket.setDesiredResolutionDate(LocalDate.now().plusDays(1L));
        ticket.setName("name");
        ticket.setDescription("description");
        ticket.setCreatedOn(LocalDate.now());
        ticket.setOwner(user);
        ticket.setStatus(Status.DRAFT);

        return ticket;
    }

    public static Ticket getTicketForConverter() {
        User owner = new User();
        owner.setId(1L);
        User approver = new User();
        approver.setId(4L);
        Ticket ticket = new Ticket();
        ticket.setId(7L);
        ticket.setName("test7");
        ticket.setUrgency(Urgency.AVERAGE);
        ticket.setOwner(owner);
        ticket.setApprover(approver);
        ticket.setStatus(Status.APPROVED);
        ticket.setCreatedOn(LocalDate.of(2021,8,2));
        ticket.setDesiredResolutionDate(LocalDate.of(2021, 11, 7));
        ticket.setDescription("any string");
        ticket.setFeedback(new Feedback());
        ticket.addAttachment(new Attachment());
        return ticket;
    }
}
