package com.training.rledenev.controllers;

import com.training.rledenev.dto.HistoryDto;
import com.training.rledenev.services.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<List<HistoryDto>> getHistoryOfTicket(@PathVariable Long ticketId) {
        List<HistoryDto> historyDtoList = historyService.getHistoryOfTicket(ticketId);
        return ResponseEntity.ok(historyDtoList);
    }
}
