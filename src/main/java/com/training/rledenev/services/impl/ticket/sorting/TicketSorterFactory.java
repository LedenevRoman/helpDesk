package com.training.rledenev.services.impl.ticket.sorting;

import com.training.rledenev.services.impl.ticket.sorting.sorter.TicketSorter;
import com.training.rledenev.services.impl.ticket.sorting.sorter.impl.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class TicketSorterFactory {
    private final Map<String, TicketSorter> orderByTicketSorterMap = new HashMap<>();

    @PostConstruct
    private void init() {
        orderByTicketSorterMap.put("id", new TicketSorterById());
        orderByTicketSorterMap.put("name", new TicketSorterByName());
        orderByTicketSorterMap.put("desiredResolutionDate", new TicketSorterByDate());
        orderByTicketSorterMap.put("urgencyName", new TicketSorterByUrgency());
        orderByTicketSorterMap.put("status", new TicketSorterByStatus());
    }

    public TicketSorter getSorter(String orderBy) {
        if (!orderByTicketSorterMap.containsKey(orderBy)) {
            return new TicketSorterByDefault();
        }
        return orderByTicketSorterMap.get(orderBy);
    }
}
