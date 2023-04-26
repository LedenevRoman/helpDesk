package com.training.rledenev.dao.h2;

import com.training.rledenev.dao.HistoryDao;
import com.training.rledenev.model.History;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoryDaoH2 extends CrudDaoH2<History> implements HistoryDao {

    public HistoryDaoH2() {
        setClazz(History.class);
    }

    @Override
    public List<History> findHistoryOfTicket(Long ticketId) {
        return getEntityManager().createQuery("from History h " +
                        "where h.ticket.id = :id", History.class)
                .setParameter("id", ticketId)
                .getResultList();
    }
}
