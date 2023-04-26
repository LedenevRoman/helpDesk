package com.training.rledenev.controllers;

import com.training.rledenev.dto.CreateTicketDto;
import com.training.rledenev.dto.TicketDto;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.services.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = "/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/draft")
    public ResponseEntity<Long> saveAsDraft(@Valid @RequestBody CreateTicketDto createTicketDto) {
        Ticket savedTicket = ticketService.saveAsDraft(createTicketDto);
        return ResponseEntity.created(URI.create("/" + savedTicket.getId())).body(savedTicket.getId());
    }

    @PostMapping("/new")
    public ResponseEntity<Long> saveAsNew(@Valid @RequestBody CreateTicketDto createTicketDto) {
        Ticket savedTicket = ticketService.saveAsNew(createTicketDto);
        return ResponseEntity.created(URI.create("/" + savedTicket.getId())).body(savedTicket.getId());
    }

    @PutMapping("/edit-draft")
    public ResponseEntity<Long> editAsDraft(@RequestParam(value = "ticketId") Long ticketId,
                                            @Valid @RequestBody CreateTicketDto createTicketDto) {
        Ticket updatedTicket = ticketService.updateAsDraft(ticketId, createTicketDto);
        return ResponseEntity.ok(updatedTicket.getId());
    }

    @PutMapping("/edit-new")
    public ResponseEntity<Long> editAsNew(@RequestParam(value = "ticketId") Long ticketId,
                                          @Valid @RequestBody CreateTicketDto createTicketDto) {
        Ticket updatedTicket = ticketService.updateAsNew(ticketId, createTicketDto);
        return ResponseEntity.ok(updatedTicket.getId());
    }

    @GetMapping("/all")
    public Page<TicketDto> getAllTickets(@RequestParam(name = "pageNumber") int pageNumber,
                                         @RequestParam(name = "pageSize") int pageSize,
                                         @RequestParam(name = "orderBy") String orderBy,
                                         @RequestParam(name = "order") String order,
                                         @RequestParam(name = "filter") String filter) {
        return ticketService.getAllTickets(pageNumber, pageSize, orderBy, order, filter);
    }

    @GetMapping("/my")
    public Page<TicketDto> getMyTickets(@RequestParam(name = "pageNumber") int pageNumber,
                                        @RequestParam(name = "pageSize") int pageSize,
                                        @RequestParam(name = "orderBy") String orderBy,
                                        @RequestParam(name = "order") String order,
                                        @RequestParam(name = "filter") String filter) {
        return ticketService.getMyTickets(pageNumber, pageSize, orderBy, order, filter);
    }

    @GetMapping("/{ticketId}")
    public TicketDto getTicketDto(@PathVariable(name = "ticketId") Long ticketId) {
        return ticketService.getTicketDto(ticketId);
    }

    @PutMapping("/action")
    public ResponseEntity<Void> doActionWithTicket(@RequestParam(name = "ticketId") Long ticketId,
                                                   @RequestParam(name = "actionName") String actionName) {
        ticketService.doActionWithTicket(ticketId, actionName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable(name = "ticketId") Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.ok().build();
    }
}
