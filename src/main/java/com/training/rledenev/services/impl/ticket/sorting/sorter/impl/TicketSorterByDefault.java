package com.training.rledenev.services.impl.ticket.sorting.sorter.impl;

import com.training.rledenev.model.Ticket;
import com.training.rledenev.services.impl.ticket.sorting.sorter.TicketSorter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TicketSorterByDefault implements TicketSorter {

    @Override
    public List<Ticket> getSortedTickets(List<Ticket> tickets, String order) {
        Comparator<Ticket> comparator = Comparator.comparing(Ticket::getUrgency)
                .thenComparing(Ticket::getDesiredResolutionDate);
        return tickets.stream().sorted(comparator).collect(Collectors.toList());
    }
}
