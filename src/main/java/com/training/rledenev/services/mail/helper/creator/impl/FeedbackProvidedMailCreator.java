package com.training.rledenev.services.mail.helper.creator.impl;

import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;
import com.training.rledenev.services.mail.helper.Mail;
import com.training.rledenev.services.mail.helper.creator.MailCreator;

import java.util.HashSet;
import java.util.Set;

public class FeedbackProvidedMailCreator implements MailCreator {

    @Override
    public Mail createMail(Ticket ticket) {
        Set<User> recipients = new HashSet<>();
        recipients.add(ticket.getAssignee());
        Mail mail = new Mail("Feedback was provided", "feedbackProvided", getEmails(recipients));
        mail.addVariable("ticket", ticket);
        return mail;
    }
}
