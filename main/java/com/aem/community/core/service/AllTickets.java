package com.aem.community.core.service;

import com.aem.community.core.models.CommentJira;
import com.aem.community.core.models.Ticket;

import java.util.List;

public interface AllTickets {
    List<Ticket> getTickets(String user);
    Ticket getTicket(String ticketName);
    List<CommentJira> getComments(String ticketName);
    String getRole(String username);
}
