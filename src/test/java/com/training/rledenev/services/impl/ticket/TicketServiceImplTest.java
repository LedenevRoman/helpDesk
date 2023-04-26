package com.training.rledenev.services.impl.ticket;

import com.training.rledenev.converter.CommentConverter;
import com.training.rledenev.converter.CreateTicketConverter;
import com.training.rledenev.converter.TicketConverter;
import com.training.rledenev.dao.TicketDao;
import com.training.rledenev.dto.CommentDto;
import com.training.rledenev.dto.CreateTicketDto;
import com.training.rledenev.dto.TicketDto;
import com.training.rledenev.enums.Action;
import com.training.rledenev.enums.Status;
import com.training.rledenev.factory.TicketFactory;
import com.training.rledenev.factory.UserFactory;
import com.training.rledenev.model.Comment;
import com.training.rledenev.model.History;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;
import com.training.rledenev.provider.UserProvider;
import com.training.rledenev.services.HistoryService;
import com.training.rledenev.services.impl.ticket.sorting.TicketSorterFactory;
import com.training.rledenev.services.impl.ticket.sorting.sorter.impl.TicketSorterByDefault;
import com.training.rledenev.services.mail.EmailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class TicketServiceImplTest {
    private final User user = UserFactory.getUser();
    private final CreateTicketDto createTicketDto = new CreateTicketDto();
    private final Comment comment = new Comment();
    private final History history = new History();
    private final Ticket ticketWithoutId = new Ticket();
    private final List<Ticket> tickets = Collections.singletonList(ticketWithoutId);
    private final TicketDto ticketDtoWithoutId = new TicketDto();
    private final List<TicketDto> ticketsDto = Collections.singletonList(ticketDtoWithoutId);
    private Ticket ticket;

    @Mock
    private CreateTicketConverter createTicketConverter;

    @Mock
    private TicketConverter ticketConverter;

    @Mock
    private CommentConverter commentConverter;

    @Mock
    private TicketDao ticketDao;

    @Mock
    private EmailService emailService;

    @Mock
    private HistoryService historyService;

    @Mock
    private UserProvider userProvider;

    @Mock
    private TicketSorterFactory ticketSorterFactory;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setId(1L);
        createTicketDto.setCommentText("ALOHA");
    }

    @AfterEach
    void tearDown() {
        ticket = null;
        createTicketDto.setCommentText(null);
    }

    @Test
    void saveAsDraft() {
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(createTicketConverter.fromDto(any(CreateTicketDto.class))).thenReturn(ticket);
        when(commentConverter.fromDto(any(CommentDto.class))).thenReturn(comment);
        when(historyService.getCreationHistory(any(Ticket.class))).thenReturn(history);
        doNothing().when(ticketDao).save(any(Ticket.class));

        Ticket expected = new Ticket();
        expected.setId(1L);

        Ticket actual = ticketService.saveAsDraft(createTicketDto);

        verify(ticketDao, times(1)).save(ticket);
        assertEquals(expected, actual);
    }

    @Test
    void saveAsNew() {
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(createTicketConverter.fromDto(any(CreateTicketDto.class))).thenReturn(ticket);
        when(commentConverter.fromDto(any(CommentDto.class))).thenReturn(comment);
        when(historyService.getCreationHistory(any(Ticket.class))).thenReturn(history);


        Ticket expected = new Ticket();
        expected.setId(1L);

        Ticket actual = ticketService.saveAsNew(createTicketDto);

        verify(ticketDao, times(1)).save(ticket);
        assertEquals(expected, actual);
    }

    @Test
    void updateAsDraft() {
        when(ticketDao.findOne(any(Long.class))).thenReturn(Optional.ofNullable(ticket));
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(createTicketConverter.fromDto(any(CreateTicketDto.class))).thenReturn(ticketWithoutId);
        when(commentConverter.fromDto(any(CommentDto.class))).thenReturn(comment);
        when(historyService.getEditionHistory(any(Ticket.class))).thenReturn(history);

        Ticket expected = new Ticket();
        expected.setId(1L);

        Ticket actual = ticketService.updateAsDraft(1L, createTicketDto);

        verify(ticketDao, times(1)).update(ticket);
        assertEquals(expected, actual);
    }

    @Test
    void updateAsNew() {
        // given
        when(ticketDao.findOne(any(Long.class))).thenReturn(Optional.ofNullable(ticket));
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(createTicketConverter.fromDto(any(CreateTicketDto.class))).thenReturn(ticketWithoutId);
        when(commentConverter.fromDto(any(CommentDto.class))).thenReturn(comment);
        when(historyService.getEditionHistory(any(Ticket.class))).thenReturn(history);
        when(historyService.getChangeStatusHistory(any(Ticket.class), any(Status.class))).thenReturn(history);
        doNothing().when(ticketDao).update(any(Ticket.class));
        doNothing().when(emailService).sendTicketEmail(any(Status.class), any(Ticket.class));

        Ticket expected = new Ticket().setId(1L);

        // when
        Ticket actual = ticketService.updateAsNew(1L, createTicketDto);

        // then
        verify(ticketDao, times(1)).update(ticket);
        verify(emailService, times(1)).sendTicketEmail(Status.DRAFT, ticket);

        assertEquals(expected, actual);
    }

    @Test
    void getAllTickets() {
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(ticketDao.getEngineerAllTickets(any(User.class))).thenReturn(tickets);
        when(ticketSorterFactory.getSorter(any(String.class))).thenReturn(new TicketSorterByDefault());
        when(ticketConverter.fromEntity(any(Ticket.class))).thenReturn(ticketDtoWithoutId);

        Pageable pageable = PageRequest.of(0, 1);
        Page<TicketDto> expected = new PageImpl<>(ticketsDto, pageable, ticketsDto.size());

        Page<TicketDto> actual = ticketService.getAllTickets(0, 1, "", "", "");

        assertEquals(expected, actual);
    }

    @Test
    void getMyTickets() {
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(ticketDao.getEngineerMyTickets(any(User.class))).thenReturn(tickets);
        when(ticketSorterFactory.getSorter(any(String.class))).thenReturn(new TicketSorterByDefault());
        when(ticketConverter.fromEntity(any(Ticket.class))).thenReturn(ticketDtoWithoutId);

        Pageable pageable = PageRequest.of(0, 1);
        Page<TicketDto> expected = new PageImpl<>(ticketsDto, pageable, ticketsDto.size());

        Page<TicketDto> actual = ticketService.getMyTickets(0, 1, "", "", "");

        assertEquals(expected, actual);
    }

    @Test
    void getTicketDto() {
        when(ticketDao.findOne(any(Long.class))).thenReturn(Optional.ofNullable(ticket));
        when(ticketConverter.fromEntity(any(Ticket.class))).thenReturn(ticketDtoWithoutId);

        TicketDto expected = new TicketDto();

        TicketDto actual = ticketService.getTicketDto(1L);

        assertEquals(expected, actual);
    }

    @Test
    void doActionWithTicket() {
        ticket.setStatus(Status.NEW);
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(ticketDao.findOne(any(Long.class))).thenReturn(Optional.ofNullable(ticket));
        when(historyService.getChangeStatusHistory(any(Ticket.class), any(Status.class))).thenReturn(history);
        doNothing().when(ticketDao).update(any(Ticket.class));
        doNothing().when(emailService).sendTicketEmail(any(Status.class), any(Ticket.class));

        ticketService.doActionWithTicket(1L, "Approve");

        verify(ticketDao, times(1)).update(ticket);
        verify(emailService, times(1)).sendTicketEmail(Status.NEW, ticket);

    }

    @Test
    void findTicket() {
        when(ticketDao.findOne(any(Long.class))).thenReturn(Optional.ofNullable(ticketWithoutId));

        Ticket expected = new Ticket();

        Ticket actual = ticketService.findTicket(1L);

        assertEquals(expected, actual);
    }

    @Test
    void deleteTicket() {
        doNothing().when(ticketDao).deleteById(any(Long.class));

        ticketService.deleteTicket(1L);

        verify(ticketDao, times(1)).deleteById(1L);
    }
}