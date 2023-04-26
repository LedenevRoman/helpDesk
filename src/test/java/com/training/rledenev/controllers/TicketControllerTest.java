package com.training.rledenev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.rledenev.dto.CreateTicketDto;
import com.training.rledenev.dto.TicketDto;
import com.training.rledenev.factory.CreateTicketDtoFactory;
import com.training.rledenev.factory.TicketDtoFactory;
import com.training.rledenev.factory.TicketFactory;
import com.training.rledenev.model.Ticket;
import org.eclipse.jetty.util.ajax.JSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DirtiesContext
    @WithUserDetails(value = "user1_mogilev@yopmail.com")
    void saveAsDraft() throws Exception {
        CreateTicketDto createTicketDto = CreateTicketDtoFactory.getCreateTicket();
        Ticket ticket = TicketFactory.getDraftTicket();
        MvcResult result = mockMvc.perform(post("/ticket/draft")
                .content(objectMapper.writeValueAsString(createTicketDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String expected = ticket.getId().toString();

        String actual = result.getResponse().getContentAsString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    @WithUserDetails(value = "user1_mogilev@yopmail.com")
    void saveAsNew() throws Exception {
        CreateTicketDto createTicketDto = CreateTicketDtoFactory.getCreateTicket();
        Ticket ticket = TicketFactory.getDraftTicket();
        MvcResult result = mockMvc.perform(post("/ticket/new")
                .content(objectMapper.writeValueAsString(createTicketDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String expected = ticket.getId().toString();

        String actual = result.getResponse().getContentAsString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithUserDetails(value = "user1_mogilev@yopmail.com")
    void editAsDraft() throws Exception {
        CreateTicketDto createTicketDto = CreateTicketDtoFactory.getCreateTicket();
        String id = String.valueOf(1L);
        MvcResult result = mockMvc.perform(put("/ticket/edit-draft")
                .param("ticketId", id)
                .content(objectMapper.writeValueAsString(createTicketDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actual = result.getResponse().getContentAsString();

        Assertions.assertEquals(id, actual);
    }

    @Test
    @WithUserDetails(value = "user1_mogilev@yopmail.com")
    void editAsNew() throws Exception {
        CreateTicketDto createTicketDto = CreateTicketDtoFactory.getCreateTicket();
        String id = String.valueOf(1L);
        MvcResult result = mockMvc.perform(put("/ticket/edit-new")
                .param("ticketId", id)
                .content(objectMapper.writeValueAsString(createTicketDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actual = result.getResponse().getContentAsString();

        Assertions.assertEquals(id, actual);
    }

    @Test
    @WithUserDetails(value = "user1_mogilev@yopmail.com")
    void getAllTickets() throws Exception {
        String pageNumber = String.valueOf(0);
        String pageSize = String.valueOf(5);
        String orderBy = "default";
        String order = "asc";
        String filter = "";
        mockMvc.perform(get("/ticket/all")
                .param("pageNumber", pageNumber)
                .param("pageSize", pageSize)
                .param("orderBy", orderBy)
                .param("order", order)
                .param("filter", filter))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    @WithUserDetails(value = "user1_mogilev@yopmail.com")
    void getMyTickets() throws Exception {
        String pageNumber = String.valueOf(0);
        String pageSize = String.valueOf(5);
        String orderBy = "default";
        String order = "asc";
        String filter = "";
        mockMvc.perform(get("/ticket/my")
                .param("pageNumber", pageNumber)
                .param("pageSize", pageSize)
                .param("orderBy", orderBy)
                .param("order", order)
                .param("filter", filter))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithUserDetails(value = "user1_mogilev@yopmail.com")
    void getTicketDto() throws Exception {
        TicketDto ticketDto = TicketDtoFactory.getTicketDto();
        String id = String.valueOf(7L);
        MvcResult result = mockMvc.perform(get("/ticket/{ticketId}", id))
                .andExpect(status().isOk())
                .andReturn();
        String expected = objectMapper.writeValueAsString(ticketDto);

        String actual = result.getResponse().getContentAsString();

        Assertions.assertEquals(expected, actual);

    }

    @Test
    @DirtiesContext
    @WithUserDetails(value = "manager1_mogilev@yopmail.com")
    void doActionWithTicket() throws Exception {
        String ticketId = String.valueOf(3L);
        String actionName = "Approve";
        mockMvc.perform(put("/ticket/action")
                .param("ticketId", ticketId)
                .param("actionName", actionName))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DirtiesContext
    @WithUserDetails(value = "user1_mogilev@yopmail.com")
    void deleteTicket() throws Exception {
        String id = String.valueOf(7L);
        mockMvc.perform(delete("/ticket/{ticketId}", id))
                .andExpect(status().isOk())
                .andReturn();
    }
}