package com.training.rledenev.services;

import com.training.rledenev.dto.HistoryDto;
import com.training.rledenev.enums.Status;
import com.training.rledenev.model.History;
import com.training.rledenev.model.Ticket;

import java.util.List;

public interface HistoryService {

    List<HistoryDto> getHistoryOfTicket(Long ticketId);

    History getCreationHistory(Ticket ticket);

    History getChangeStatusHistory(Ticket ticket, Status previousStatus);

    History getEditionHistory(Ticket ticket);

    History getFileAttachedHistory(Ticket ticket, String name);

    History getFileRemovedHistory(Ticket ticket, String name);
}
