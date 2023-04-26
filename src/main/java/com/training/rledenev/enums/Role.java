package com.training.rledenev.enums;

import com.training.rledenev.dao.TicketDao;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;
import com.training.rledenev.provider.UserProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.training.rledenev.enums.Action.*;
import static com.training.rledenev.enums.Status.*;
import static com.training.rledenev.enums.Status.DONE;
import static java.util.List.of;

public enum Role {
    EMPLOYEE {
        @Override
        public List<String> getActions(Ticket ticket) {
            if (!employeeStatusActionsMap.containsKey(ticket.getStatus()) || isHaveFeedback(ticket)) {
                return new ArrayList<>();
            }
            return employeeStatusActionsMap.get(ticket.getStatus());
        }

        @Override
        public List<Ticket> getAllTickets(User user, TicketDao ticketDao) {
            return ticketDao.getEmployeeTickets(user);
        }

        @Override
        public List<Ticket> getMyTickets(User user, TicketDao ticketDao) {
            return ticketDao.getEmployeeTickets(user);
        }
    },

    MANAGER {
        @Override
        public List<String> getActions(Ticket ticket) {
            Status currentStatus = ticket.getStatus();
            if ((!managerStatusActionsMap.containsKey(currentStatus))
                    || isInvalidOwner(ticket, currentStatus)
                    || isHaveFeedback(ticket)) {
                return new ArrayList<>();
            }

            return managerStatusActionsMap.get(ticket.getStatus());
        }

        @Override
        public List<Ticket> getAllTickets(User user, TicketDao ticketDao) {
            return ticketDao.getManagerAllTickets(user);
        }

        @Override
        public List<Ticket> getMyTickets(User user, TicketDao ticketDao) {
            return ticketDao.getManagerMyTickets(user);
        }

        private boolean isInvalidOwner(Ticket ticket, Status currentStatus) {
            User user = new UserProvider().getCurrentUser();
            return (isOwner(ticket, user) && isStatusNew(currentStatus))
                    || (!isOwner(ticket, user) && !isStatusNew(currentStatus));
        }

        private boolean isStatusNew(Status currentStatus) {
            return currentStatus.equals(NEW);
        }

        private boolean isOwner(Ticket ticket, User user) {
            return ticket.getOwner().equals(user);
        }
    },

    ENGINEER {
        @Override
        public List<String> getActions(Ticket ticket) {
            if (!engineerStatusActionsMap.containsKey(ticket.getStatus())) {
                return new ArrayList<>();
            }
            return engineerStatusActionsMap.get(ticket.getStatus());
        }

        @Override
        public List<Ticket> getAllTickets(User user, TicketDao ticketDao) {
            return ticketDao.getEngineerAllTickets(user);
        }

        @Override
        public List<Ticket> getMyTickets(User user, TicketDao ticketDao) {
            return ticketDao.getEngineerMyTickets(user);
        }
    };

    private static final Map<Status, List<String>> employeeStatusActionsMap = new HashMap<>();
    private static final Map<Status, List<String>> managerStatusActionsMap = new HashMap<>();
    private static final Map<Status, List<String>> engineerStatusActionsMap = new HashMap<>();

    static {
        employeeStatusActionsMap.put(DONE, of(LEAVE_FEEDBACK.toString()));
        employeeStatusActionsMap.put(DRAFT, of(SUBMIT.toString(), CANCEL.toString()));
        employeeStatusActionsMap.put(DECLINED, of(SUBMIT.toString(), CANCEL.toString()));

        managerStatusActionsMap.put(DONE, of(LEAVE_FEEDBACK.toString()));
        managerStatusActionsMap.put(DRAFT, of(SUBMIT.toString(), CANCEL.toString()));
        managerStatusActionsMap.put(DECLINED, of(SUBMIT.toString(), CANCEL.toString()));
        managerStatusActionsMap.put(NEW, of(APPROVE.toString(), DECLINE.toString(), CANCEL.toString()));

        engineerStatusActionsMap.put(IN_PROGRESS, of(Action.DONE.toString()));
        engineerStatusActionsMap.put(APPROVED, of(ASSIGN_TO_ME.toString(), CANCEL.toString()));
    }

    public abstract List<String> getActions(Ticket ticket);

    public abstract List<Ticket> getAllTickets(User user, TicketDao ticketDao);

    public abstract List<Ticket> getMyTickets(User user, TicketDao ticketDao);

    boolean isHaveFeedback(Ticket ticket) {
        return ticket.getStatus().equals(DONE) && ticket.getFeedback() != null;
    }
}
