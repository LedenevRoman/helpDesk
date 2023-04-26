package com.training.rledenev.services;

import com.training.rledenev.dto.CreateTicketDto;
import com.training.rledenev.dto.TicketDto;
import com.training.rledenev.model.Ticket;
import org.springframework.data.domain.Page;

public interface TicketService {

    Ticket saveAsDraft(CreateTicketDto createTicketDto);

    Ticket saveAsNew(CreateTicketDto createTicketDto);

    Ticket updateAsDraft(Long ticketId, CreateTicketDto createTicketDto);

    Ticket updateAsNew(Long ticketId, CreateTicketDto createTicketDto);

    Page<TicketDto> getAllTickets(int pageNumber, int pageSize, String orderBy, String order, String filter);

    Page<TicketDto> getMyTickets(int pageNumber, int pageSize, String orderBy, String order, String filter);

    TicketDto getTicketDto(Long ticketId);

    void doActionWithTicket(Long ticketId, String actionName);

    Ticket findTicket(Long ticketId);

    void deleteTicket(Long ticketId);
}
