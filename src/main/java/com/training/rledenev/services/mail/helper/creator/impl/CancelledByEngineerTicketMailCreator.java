package com.training.rledenev.services.mail.helper.creator.impl;

import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;
import com.training.rledenev.services.mail.helper.Mail;
import com.training.rledenev.services.mail.helper.creator.MailCreator;

import java.util.HashSet;
import java.util.Set;

public class CancelledByEngineerTicketMailCreator implements MailCreator {

    @Override
    public Mail createMail(Ticket ticket) {
        Set<User> recipients = new HashSet<>();
        recipients.add(ticket.getOwner());
        recipients.add(ticket.getApprover());
        Mail mail = new Mail("Ticket was cancelled", "ticketCancelledByEngineer", getEmails(recipients));
        mail.addVariable("ticket", ticket);
        return mail;
    }
}
