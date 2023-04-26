package com.training.rledenev.services.impl;

import com.training.rledenev.converter.FeedbackConverter;
import com.training.rledenev.dao.FeedbackDao;
import com.training.rledenev.dto.FeedbackDto;
import com.training.rledenev.enums.Status;
import com.training.rledenev.model.Feedback;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.services.FeedbackService;
import com.training.rledenev.services.TicketService;
import com.training.rledenev.services.mail.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackConverter feedbackConverter;
    private final FeedbackDao feedbackDao;
    private final TicketService ticketService;
    private final EmailService emailService;


    public FeedbackServiceImpl(FeedbackConverter feedbackConverter, FeedbackDao feedbackDao,
                               TicketService ticketService, EmailService emailService) {
        this.feedbackConverter = feedbackConverter;
        this.feedbackDao = feedbackDao;
        this.ticketService = ticketService;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public void save(FeedbackDto feedbackDto, Long ticketId) {
        Ticket ticket = ticketService.findTicket(ticketId);
        Feedback feedback = feedbackConverter.fromDto(feedbackDto);
        feedback.setTicket(ticket);
        emailService.sendTicketEmail(Status.DONE, ticket);
        feedbackDao.save(feedback);
    }
}
