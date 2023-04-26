package com.training.rledenev.services.impl.ticket.sorting.sorter;

import com.training.rledenev.model.Ticket;

import java.util.List;

public interface TicketSorter {

    List<Ticket> getSortedTickets(List<Ticket> tickets, String order);
}
