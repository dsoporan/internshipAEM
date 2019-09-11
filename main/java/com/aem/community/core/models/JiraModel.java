package com.aem.community.core.models;

import com.adobe.cq.sightly.WCMUse;
import com.aem.community.core.constants.MyConstants;
import com.aem.community.core.service.AllTickets;
import com.aem.community.core.service.AllUsers;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.Session;
import java.util.ArrayList;
import java.util.List;

public class JiraModel extends WCMUse {

    private Ticket ticket = new Ticket.Builder().setTicketName("").setDescription("").setStatus("").setAssignTo("").setCreatedBy("").setAttachments("").build();;;;;
    private List<Ticket> allTicketsUser = new ArrayList<Ticket>();
    private List<CommentJira> commentJiraList = new ArrayList<CommentJira>();
    private List<Pair> allSignUpList = new ArrayList<Pair>();
    private String username;
    private String role;

    @Override
    public void activate() throws Exception {
        System.out.println("Jira");
        ResourceResolver resourceResolver = getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
        username = session.getUserID();
        AllTickets allTickets = getSlingScriptHelper().getService(AllTickets.class);
        allTicketsUser = allTickets.getTickets(session.getUserID());
        role = allTickets.getRole(username);
        System.out.println(role);
        System.out.println("Tickets" + allTicketsUser);
        String ticketNameParameter = getRequest().getParameter("ticketName");
        if (ticketNameParameter != null){
            ticket = allTickets.getTicket(ticketNameParameter);
            commentJiraList = allTickets.getComments(ticketNameParameter);
            System.out.println(commentJiraList);
            AllUsers allUsers = getSlingScriptHelper().getService(AllUsers.class);
            allSignUpList = allUsers.getSignUpUsers();
        }
    }

    public List<Ticket> getAllTicketsUser() {
        return allTicketsUser;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public String getUsername() {
        return username;
    }

    public List<CommentJira> getCommentJiraList() {
        return commentJiraList;
    }

    public String getRole() {
        return role;
    }

    public List<Pair> getAllSignUpList() {
        return allSignUpList;
    }
}
