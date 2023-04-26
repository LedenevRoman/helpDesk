package com.training.rledenev.services.mail.helper.creator.impl;

import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;
import com.training.rledenev.services.mail.helper.Mail;
import com.training.rledenev.services.mail.helper.creator.MailCreator;

import java.util.HashSet;
import java.util.Set;

public class DoneTicketMailCreator implements MailCreator {

    @Override
    public Mail createMail(Ticket ticket) {
        Set<User> recipients = new HashSet<>();
        recipients.add(ticket.getOwner());
        Mail mail = new Mail("Ticket was done", "ticketDone", getEmails(recipients));
        mail.addVariable("ticket", ticket);
        return mail;
    }
}
