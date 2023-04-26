package com.training.rledenev.dao.h2;

import com.training.rledenev.dao.TicketDao;
import com.training.rledenev.enums.Status;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketDaoH2 extends CrudDaoH2<Ticket> implements TicketDao {


    public TicketDaoH2() {
        setClazz(Ticket.class);
    }

    @Override
    public List<Ticket> getEmployeeTickets(User user) {
        return getEntityManager().createQuery("from Ticket t " +
                "where t.owner.id = :id", Ticket.class)
                .setParameter("id", user.getId())
                .getResultList();
    }

    @Override
    public List<Ticket> getManagerAllTickets(User user) {
        return getEntityManager().createQuery("from Ticket t " +
                        "where t.owner.id = :id or t.approver.id = :id or t.status = :status", Ticket.class)
                .setParameter("id", user.getId())
                .setParameter("status", Status.NEW)
                .getResultList();

    }

    @Override
    public List<Ticket> getEngineerAllTickets(User user) {
        return getEntityManager().createQuery("from Ticket t " +
                        "where t.assignee.id = :id or t.assignee.id = null and t.status = :status", Ticket.class)
                .setParameter("id", user.getId())
                .setParameter("status", Status.APPROVED)
                .getResultList();
    }

    @Override
    public List<Ticket> getManagerMyTickets(User user) {
        return getEntityManager().createQuery("from Ticket t " +
                        "where t.owner.id = :id or t.approver.id = :id", Ticket.class)
                .setParameter("id", user.getId())
                .getResultList();
    }

    @Override
    public List<Ticket> getEngineerMyTickets(User user) {
        return getEntityManager().createQuery("from Ticket t " +
                        "where t.assignee.id = :id", Ticket.class)
                .setParameter("id", user.getId())
                .getResultList();
    }
}
