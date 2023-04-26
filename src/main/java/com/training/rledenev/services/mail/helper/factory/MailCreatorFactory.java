package com.training.rledenev.services.mail.helper.factory;

import com.training.rledenev.dao.UserDao;
import com.training.rledenev.enums.Status;
import com.training.rledenev.services.mail.helper.creator.MailCreator;
import com.training.rledenev.services.mail.helper.creator.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.training.rledenev.enums.Status.*;

@Component
public class MailCreatorFactory {
    private static final Logger LOGGER = LogManager.getLogger(MailCreatorFactory.class);
    private final Map<String, MailCreator> mailCreatorMap = new HashMap<>();
    private final UserDao userDao;

    public MailCreatorFactory(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostConstruct
    private void init() {
        mailCreatorMap.put(getStringStatusesKey(DONE, DONE), new FeedbackProvidedMailCreator());
        mailCreatorMap.put(getStringStatusesKey(DRAFT, NEW), new NewTicketMailCreator(userDao));
        mailCreatorMap.put(getStringStatusesKey(NEW, DECLINED), new DeclinedTicketMailCreator());
        mailCreatorMap.put(getStringStatusesKey(IN_PROGRESS, DONE), new DoneTicketMailCreator());
        mailCreatorMap.put(getStringStatusesKey(DECLINED, NEW), new NewTicketMailCreator(userDao));
        mailCreatorMap.put(getStringStatusesKey(NEW, APPROVED), new ApprovedTicketMailCreator(userDao));
        mailCreatorMap.put(getStringStatusesKey(NEW, CANCELED), new CancelledByManagerTicketMailCreator());
        mailCreatorMap.put(getStringStatusesKey(APPROVED, CANCELED), new CancelledByEngineerTicketMailCreator());
    }

    public Optional<MailCreator> getMailCreator(Status previousStatus, Status currentStatus) {
        String statuses = getStringStatusesKey(previousStatus, currentStatus);
        if (!mailCreatorMap.containsKey(statuses)) {
            LOGGER.info("Wrong sequence of status change: from {}, to {}", previousStatus, currentStatus);
            return Optional.empty();
        }
        return Optional.ofNullable(mailCreatorMap.get(statuses));
    }

    private String getStringStatusesKey(Status previousStatus, Status currentStatus) {
        return previousStatus.toString() + " " + currentStatus.toString();
    }
}
