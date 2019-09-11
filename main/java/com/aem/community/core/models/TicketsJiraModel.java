package com.aem.community.core.models;

import com.adobe.cq.sightly.WCMUse;
import com.aem.community.core.service.AllTickets;

import java.util.ArrayList;
import java.util.List;

public class TicketsJiraModel extends WCMUse {

    private List<Ticket> tickets = new ArrayList<Ticket>();

    @Override
    public void activate() throws Exception {
        System.out.println("Tickets Jira");
        AllTickets allTickets = getSlingScriptHelper().getService(AllTickets.class);
        tickets = allTickets.getTickets("");
        System.out.println("Tickets" + tickets);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}
