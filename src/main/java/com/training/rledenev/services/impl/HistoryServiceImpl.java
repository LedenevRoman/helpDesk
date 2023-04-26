package com.training.rledenev.services.impl;

import com.training.rledenev.converter.HistoryConverter;
import com.training.rledenev.dao.HistoryDao;
import com.training.rledenev.dto.HistoryDto;
import com.training.rledenev.enums.Status;
import com.training.rledenev.model.History;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;
import com.training.rledenev.provider.LocalDateProvider;
import com.training.rledenev.provider.UserProvider;
import com.training.rledenev.services.HistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements HistoryService {
    private final HistoryDao historyDao;
    private final HistoryConverter historyConverter;
    private final UserProvider userProvider;
    private final LocalDateProvider localDateProvider;

    public HistoryServiceImpl(HistoryDao historyDao, HistoryConverter historyConverter,
                              UserProvider userProvider, LocalDateProvider localDateProvider) {
        this.historyDao = historyDao;
        this.historyConverter = historyConverter;
        this.userProvider = userProvider;
        this.localDateProvider = localDateProvider;
    }

    @Transactional
    @Override
    public List<HistoryDto> getHistoryOfTicket(Long ticketId) {
        List<History> historyList = historyDao.findHistoryOfTicket(ticketId);
        return historyList.stream()
                .map(historyConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public History getCreationHistory(Ticket ticket) {
        History history = getInitialHistory(ticket);
        history.setAction("Ticket is created");
        history.setDescription("Ticket is created");
        return history;
    }

    @Override
    public History getChangeStatusHistory(Ticket ticket, Status previousStatus) {
        History history = getInitialHistory(ticket);
        history.setAction("Ticket Status is changed");
        history.setDescription("Ticket Status is changed from " + previousStatus.toString()
                + " to " + ticket.getStatus().toString());
        return history;
    }

    @Override
    public History getEditionHistory(Ticket ticket) {
        History history = getInitialHistory(ticket);
        history.setAction("Ticket is edited");
        history.setDescription("Ticket is edited");
        return history;
    }

    @Override
    public History getFileAttachedHistory(Ticket ticket, String name) {
        History history = getInitialHistory(ticket);
        history.setAction("File is attached");
        history.setDescription("File is attached: " + name);
        return history;
    }

    @Override
    public History getFileRemovedHistory(Ticket ticket, String name) {
        History history = getInitialHistory(ticket);
        history.setAction("File is removed");
        history.setDescription("File is removed: " + name);
        return history;
    }

    private History getInitialHistory(Ticket ticket) {
        History history = new History();
        User user = userProvider.getCurrentUser();
        history.setUser(user);
        history.setTicket(ticket);
        history.setDate(localDateProvider.getCurrentLocalDate());
        return history;
    }
}
