package com.training.rledenev.services.impl.ticket;

import com.training.rledenev.converter.CommentConverter;
import com.training.rledenev.converter.CreateTicketConverter;
import com.training.rledenev.converter.TicketConverter;
import com.training.rledenev.dao.TicketDao;
import com.training.rledenev.dto.CommentDto;
import com.training.rledenev.dto.CreateTicketDto;
import com.training.rledenev.dto.TicketDto;
import com.training.rledenev.enums.Action;
import com.training.rledenev.enums.Role;
import com.training.rledenev.enums.Status;
import com.training.rledenev.model.*;
import com.training.rledenev.provider.UserProvider;
import com.training.rledenev.services.HistoryService;
import com.training.rledenev.services.TicketService;
import com.training.rledenev.services.impl.ticket.sorting.TicketSorterFactory;
import com.training.rledenev.services.impl.ticket.sorting.sorter.TicketSorter;
import com.training.rledenev.services.mail.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final CreateTicketConverter createTicketConverter;
    private final TicketConverter ticketConverter;
    private final CommentConverter commentConverter;
    private final TicketDao ticketDao;
    private final EmailService emailService;
    private final HistoryService historyService;
    private final UserProvider userProvider;
    private final TicketSorterFactory ticketSorterFactory;

    public TicketServiceImpl(CreateTicketConverter createTicketConverter, TicketConverter ticketConverter,
                             CommentConverter commentConverter, TicketDao ticketDao,
                             EmailService emailService, HistoryService historyService,
                             UserProvider userProvider, TicketSorterFactory ticketSorterFactory) {
        this.createTicketConverter = createTicketConverter;
        this.ticketConverter = ticketConverter;
        this.commentConverter = commentConverter;
        this.ticketDao = ticketDao;
        this.emailService = emailService;
        this.historyService = historyService;
        this.userProvider = userProvider;
        this.ticketSorterFactory = ticketSorterFactory;
    }

    @Transactional
    @Override
    public Ticket saveAsDraft(CreateTicketDto createTicketDto) {
        Ticket ticket = createTicket(createTicketDto);
        ticket.setStatus(Status.DRAFT);
        History history = historyService.getCreationHistory(ticket);
        ticket.addHistory(history);
        ticketDao.save(ticket);
        return ticket;
    }

    @Transactional
    @Override
    public Ticket saveAsNew(CreateTicketDto createTicketDto) {
        Ticket ticket = createTicket(createTicketDto);
        ticket.setStatus(Status.NEW);
        History history = historyService.getCreationHistory(ticket);
        ticket.addHistory(history);
        ticketDao.save(ticket);
        emailService.sendTicketEmail(Status.DRAFT, ticket);
        return ticket;
    }

    @Transactional
    @Override
    public Ticket updateAsDraft(Long ticketId, CreateTicketDto createTicketDto) {
        Ticket updatedTicket = updateDraftTicket(ticketId, createTicketDto);
        ticketDao.update(updatedTicket);
        return updatedTicket;
    }

    @Transactional
    @Override
    public Ticket updateAsNew(Long ticketId, CreateTicketDto createTicketDto) {
        Ticket updatedTicket = updateDraftTicket(ticketId, createTicketDto);
        changeTicketStatus(updatedTicket, Status.NEW);
        return updatedTicket;
    }

    @Transactional
    @Override
    public Page<TicketDto> getAllTickets(int pageNumber, int pageSize,
                                         String orderBy, String order, String filter) {
        User user = userProvider.getCurrentUser();
        List<Ticket> tickets = user.getRole().getAllTickets(user, ticketDao);
        List<Ticket> filteredTickets = getFilteredTickets(filter, tickets);
        return getPageSortedTicketDto(filteredTickets, pageNumber, pageSize, orderBy, order);
    }

    @Transactional
    @Override
    public Page<TicketDto> getMyTickets(int pageNumber, int pageSize,
                                        String orderBy, String order, String filter) {
        User user = userProvider.getCurrentUser();
        List<Ticket> tickets = user.getRole().getMyTickets(user, ticketDao);
        List<Ticket> filteredTickets = getFilteredTickets(filter, tickets);
        return getPageSortedTicketDto(filteredTickets, pageNumber, pageSize, orderBy, order);
    }


    @Transactional
    @Override
    public TicketDto getTicketDto(Long ticketId) {
        Ticket ticket = findTicket(ticketId);
        return ticketConverter.fromEntity(ticket);
    }

    @Transactional
    @Override
    public void doActionWithTicket(Long ticketId, String actionName) {
        Action action = Action.valueOf(getEnumName(actionName));
        Status newStatus = action.getStatus();
        User user = userProvider.getCurrentUser();
        Ticket ticket = findTicket(ticketId);
        if (user.getRole().equals(Role.MANAGER)) {
            ticket.setApprover(user);
        }
        if (user.getRole().equals(Role.ENGINEER)) {
            ticket.setAssignee(user);
        }
        changeTicketStatus(ticket, newStatus);
    }

    @Override
    public Ticket findTicket(Long ticketId) {
        return ticketDao.findOne(ticketId).orElseThrow(() -> new EntityNotFoundException("No such ticket found"));
    }

    @Transactional
    @Override
    public void deleteTicket(Long ticketId) {
        ticketDao.deleteById(ticketId);
    }

    private Ticket createTicket(CreateTicketDto createTicketDto) {
        User user = userProvider.getCurrentUser();
        Ticket ticket = createTicketConverter.fromDto(createTicketDto);
        if (!createTicketDto.getCommentText().isEmpty()) {
            Comment comment = commentConverter.fromDto(new CommentDto().setText(createTicketDto.getCommentText()));
            ticket.addComment(comment);
        }
        ticket.setOwner(user);
        return ticket;
    }

    private Ticket updateDraftTicket(Long ticketId, CreateTicketDto createTicketDto) {
        Ticket oldTicket = findTicket(ticketId);
        Ticket newTicket = createTicket(createTicketDto);
        newTicket.setId(ticketId)
                .setStatus(Status.DRAFT)
                .setCreatedOn(oldTicket.getCreatedOn());
        History history = historyService.getEditionHistory(newTicket);
        newTicket.addHistory(history);
        return newTicket;
    }

    private void changeTicketStatus(Ticket ticket, Status newStatus) {
        Status oldStatus = ticket.getStatus();
        ticket.setStatus(newStatus);
        History history = historyService.getChangeStatusHistory(ticket, oldStatus);
        ticket.addHistory(history);
        ticketDao.update(ticket);
        emailService.sendTicketEmail(oldStatus, ticket);
    }

    private List<Ticket> doSort(List<Ticket> tickets, String orderBy, String order) {
        TicketSorter sorter = ticketSorterFactory.getSorter(orderBy);
        return sorter.getSortedTickets(tickets, order);
    }

    private List<Ticket> getFilteredTickets(String filter, List<Ticket> tickets) {
        if (filter == null || filter.equals("")) {
            return tickets;
        }
        return tickets.stream()
                .filter(ticket -> isContainsFilter(filter, ticket))
                .collect(Collectors.toList());
    }

    private boolean isContainsFilter(String filter, Ticket ticket) {
        String regexp = ".*" + filter.toLowerCase() + ".*";
        return ticket.getId().toString().toLowerCase().matches(regexp)
                || ticket.getName().toLowerCase().matches(regexp)
                || ticket.getDesiredResolutionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).matches(regexp)
                || ticket.getUrgency().toString().toLowerCase().matches(regexp)
                || ticket.getStatus().toString().toLowerCase().matches(regexp);
    }

    private Page<TicketDto> getPageSortedTicketDto(List<Ticket> tickets, int pageNumber, int pageSize,
                                                   String orderBy, String order) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Ticket> sortedTickets = doSort(tickets, orderBy, order);
        int start = Math.min((int) pageable.getOffset(), tickets.size());
        int end = Math.min((start + pageable.getPageSize()), tickets.size());
        return new PageImpl<>(sortedTickets.subList(start, end), pageable, tickets.size()).map(ticketConverter::fromEntity);
    }

    private String getEnumName(String actionName) {
        return actionName.replaceAll("\\s", "_").toUpperCase(Locale.ROOT);
    }
}
