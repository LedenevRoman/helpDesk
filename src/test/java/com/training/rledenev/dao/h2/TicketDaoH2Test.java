package com.training.rledenev.dao.h2;

import com.training.rledenev.dao.TicketDao;
import com.training.rledenev.dao.UserDao;
import com.training.rledenev.factory.TicketFactory;
import com.training.rledenev.factory.UserFactory;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class TicketDaoH2Test {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private UserDao userDao;

    @Test
    void getEngineerTickets() {
        //given
        final User user = UserFactory.getUser();
        Ticket ticket = TicketFactory.getTicket();
        ticket.setAssignee(user);

        userDao.save(user);
        ticketDao.save(ticket);

        //when
        List<Ticket> engineerTickets = ticketDao.getEngineerAllTickets(user);
    }
}