package com.training.rledenev.dao;

import com.training.rledenev.model.History;

import java.util.List;

public interface HistoryDao extends CrudDao<History> {

    List<History> findHistoryOfTicket(Long ticketId);
}
