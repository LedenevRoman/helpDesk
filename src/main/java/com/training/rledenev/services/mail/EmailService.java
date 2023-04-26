package com.training.rledenev.services.mail;

import com.training.rledenev.enums.Status;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.services.mail.helper.Mail;
import com.training.rledenev.services.mail.helper.creator.MailCreator;
import com.training.rledenev.services.mail.helper.factory.MailCreatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
public class EmailService {
    private static final Logger LOGGER = LogManager.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final MailCreatorFactory mailCreatorFactory;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine, MailCreatorFactory mailCreatorFactory) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.mailCreatorFactory = mailCreatorFactory;
    }

    @Async
    public void sendTicketEmail(Status previousStatus, Ticket ticket) {
        Optional<Mail> mailOptional = createMail(previousStatus, ticket);
        mailOptional.ifPresent(this::sendMail);

    }

    private Optional<Mail> createMail(Status previousStatus, Ticket ticket) {
        Optional<MailCreator> mailCreatorOptional = mailCreatorFactory.getMailCreator(previousStatus, ticket.getStatus());
        return mailCreatorOptional.map(mailCreator -> mailCreator.createMail(ticket));
    }

    private void sendMail(Mail mail) {
        final Context ctx = new Context();
        ctx.setVariables(mail.getTemplateVars());

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        try {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject(mail.getSubject());
            message.setTo(mail.getRecipientArray());

            final String htmlContent = this.templateEngine.process(mail.getTemplate(), ctx);
            message.setText(htmlContent, true);
        } catch (MessagingException messagingException) {
            LOGGER.error(messagingException);
        }
        this.mailSender.send(mimeMessage);
    }

}
