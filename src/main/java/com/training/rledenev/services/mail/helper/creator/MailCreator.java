package com.training.rledenev.services.mail.helper.creator;

import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;
import com.training.rledenev.services.mail.helper.Mail;

import java.util.Set;

public interface MailCreator {

    Mail createMail(Ticket ticket);

    default String[] getEmails(Set<User> users) { // is it ok to place this method here?
        return users.stream().map(User::getEmail).toArray(String[]::new);
    }
}
