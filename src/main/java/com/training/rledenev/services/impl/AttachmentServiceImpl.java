package com.training.rledenev.services.impl;

import com.training.rledenev.dao.AttachmentDao;
import com.training.rledenev.dao.TicketDao;
import com.training.rledenev.model.Attachment;
import com.training.rledenev.model.History;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.services.AttachmentService;
import com.training.rledenev.services.HistoryService;
import com.training.rledenev.services.TicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentDao attachmentDao;
    private final TicketDao ticketDao;
    private final TicketService ticketService;
    private final HistoryService historyService;

    public AttachmentServiceImpl(AttachmentDao attachmentDao, TicketDao ticketDao,
                                 TicketService ticketService, HistoryService historyService) {
        this.attachmentDao = attachmentDao;
        this.ticketDao = ticketDao;
        this.ticketService = ticketService;
        this.historyService = historyService;
    }

    @Override
    public Attachment findAttachmentById(Long id) {
        return attachmentDao.findOne(id).orElseThrow(() -> new EntityNotFoundException("No such attachment found"));
    }

    @Transactional
    @Override
    public void addAttachmentsList(Long ticketId, List<Attachment> attachments) {
        Ticket ticket = ticketService.findTicket(ticketId);
        for (Attachment attachment : attachments) {
            ticket.addAttachment(attachment);
            History history = historyService.getFileAttachedHistory(ticket, attachment.getName());
            ticket.addHistory(history);
        }
        ticketDao.update(ticket);
    }

    @Transactional
    @Override
    public void removeAttachment(Long ticketId, Long attachmentId) {
        Ticket ticket = ticketService.findTicket(ticketId);
        Attachment attachment = findAttachmentById(attachmentId);
        History history = historyService.getFileRemovedHistory(ticket, attachment.getName());
        ticket.addHistory(history);
        attachmentDao.deleteById(attachmentId);
        ticketDao.update(ticket);
    }
}
