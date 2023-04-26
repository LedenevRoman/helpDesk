package com.training.rledenev.services.impl.ticket.sorting.sorter.impl;

import com.training.rledenev.model.Ticket;
import com.training.rledenev.services.impl.ticket.sorting.sorter.TicketSorter;

import java.util.Comparator;
import java.util.List;

public class TicketSorterByUrgency implements TicketSorter {
    @Override
    public List<Ticket> getSortedTickets(List<Ticket> tickets, String order) {
        Comparator<Ticket> comparator = Comparator.comparing(Ticket::getUrgency);
        if (order.equals("desc")) {
            tickets.sort(comparator.reversed());
        } else {
            tickets.sort(comparator);
        }
        return tickets;
    }
}
