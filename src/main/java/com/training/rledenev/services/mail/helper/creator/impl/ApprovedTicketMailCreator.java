package com.training.rledenev.services.mail.helper.creator.impl;

import com.training.rledenev.dao.UserDao;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;
import com.training.rledenev.services.mail.helper.Mail;
import com.training.rledenev.services.mail.helper.creator.MailCreator;

import java.util.HashSet;
import java.util.Set;

public class ApprovedTicketMailCreator implements MailCreator {
    private final UserDao userDao;

    public ApprovedTicketMailCreator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Mail createMail(Ticket ticket) {
        Set<User> recipients = new HashSet<>();
        recipients.add(ticket.getOwner());
        recipients.addAll(userDao.findAllEngineers());
        Mail mail = new Mail("Ticket was approved", "approvedTicket", getEmails(recipients));
        mail.addVariable("ticket", ticket);
        return mail;
    }
}
