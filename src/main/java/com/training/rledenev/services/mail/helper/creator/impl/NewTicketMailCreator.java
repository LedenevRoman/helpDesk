package com.training.rledenev.services.mail.helper.creator.impl;

import com.training.rledenev.dao.UserDao;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;
import com.training.rledenev.services.mail.helper.Mail;
import com.training.rledenev.services.mail.helper.creator.MailCreator;

import java.util.HashSet;
import java.util.Set;

public class NewTicketMailCreator implements MailCreator {
    private final UserDao userDao;

    public NewTicketMailCreator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Mail createMail(Ticket ticket) {
        Set<User> recipients = new HashSet<>(userDao.findAllManagers());
        Mail mail = new Mail("New ticket for approval", "newTicket", getEmails(recipients));
        mail.addVariable("ticket", ticket);
        return mail;
    }
}
