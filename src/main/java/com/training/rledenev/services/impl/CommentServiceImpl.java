package com.training.rledenev.services.impl;

import com.training.rledenev.converter.CommentConverter;
import com.training.rledenev.dao.CommentDao;
import com.training.rledenev.dao.TicketDao;
import com.training.rledenev.dto.CommentDto;
import com.training.rledenev.model.Comment;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.services.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentConverter commentConverter;
    private final TicketDao ticketDao;
    private final CommentDao commentDao;

    public CommentServiceImpl(CommentConverter commentConverter, TicketDao ticketDao,
                              CommentDao commentDao) {
        this.commentConverter = commentConverter;
        this.ticketDao = ticketDao;
        this.commentDao = commentDao;
    }

    @Transactional
    @Override
    public CommentDto save(CommentDto commentDto, Long ticketId) {
        Comment comment = commentConverter.fromDto(commentDto);
        Optional<Ticket> ticket = ticketDao.findOne(ticketId);
        ticket.ifPresent(comment::setTicket);
        commentDao.save(comment);
        return commentConverter.fromEntity(comment);
    }

    @Transactional
    @Override
    public List<CommentDto> getAll(Long ticketId) {
        Optional<Ticket> ticket = ticketDao.findOne(ticketId);
        return ticket.map(value -> value.getComments().stream()
                .map(commentConverter::fromEntity)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }
}
