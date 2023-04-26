package com.training.rledenev.dao;

import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;

import java.util.List;

public interface TicketDao extends CrudDao<Ticket> {

    List<Ticket> getEmployeeTickets(User user);

    List<Ticket> getManagerAllTickets(User user);

    List<Ticket> getEngineerAllTickets(User user);

    List<Ticket> getManagerMyTickets(User user);

    List<Ticket> getEngineerMyTickets(User user);
}
