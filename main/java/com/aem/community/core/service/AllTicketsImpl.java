package com.aem.community.core.service;

import com.aem.community.core.models.CommentJira;
import com.aem.community.core.models.Reservation;
import com.aem.community.core.models.Ticket;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.resource.LoginException;

import javax.jcr.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(immediate = true)
@Service(value = AllTickets.class)
public class AllTicketsImpl implements AllTickets {

    private String PATH_FORMS = "/content/dam/AEM62App/tickets";

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public List<Ticket> getTickets(String user) {
        List<Ticket> ticketList = new ArrayList<Ticket>();
        ResourceResolver adminResourceResolver = null;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");
            adminResourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
            Resource resource = adminResourceResolver.getResource(PATH_FORMS);

            if(resource != null)
            {
                Node node = resource.adaptTo(Node.class);
                NodeIterator nodeItr = node.getNodes();
                while(nodeItr.hasNext())
                {
                    Node cNode = nodeItr.nextNode();
                    String ticketName = cNode.getProperty("TicketName").getValue().getString();
                    String description = cNode.getProperty("Description").getValue().getString();
                    String status = cNode.getProperty("Status").getValue().getString();
                    String createdBy = cNode.getProperty("CreatedBy").getValue().getString();
                    String assignTo = cNode.getProperty("AssignTo").getValue().getString();
                    String attachments = "";

                    Resource resourceAtt = adminResourceResolver.getResource(PATH_FORMS + "/ticket_" + ticketName);

                    if(resourceAtt != null) {
                        Node nodeAtt = resourceAtt.adaptTo(Node.class);
                        NodeIterator nodeItrAtt = nodeAtt.getNodes();
                        while (nodeItrAtt.hasNext()) {
                            Node cNodeAtt = nodeItrAtt.nextNode();
                            String att = cNodeAtt.getName();
                            if (att.contains(".jpg"))
                                attachments += att;
                        }
                    }

                    if (user.equals("") || user.equals(assignTo)) {
                        Ticket ticket = new Ticket.Builder()
                                .setTicketName(ticketName)
                                .setDescription(description)
                                .setStatus(status)
                                .setCreatedBy(createdBy)
                                .setAssignTo(assignTo)
                                .setAttachments(attachments)
                                .build();
                        ticketList.add(ticket);
                    }
                }
            }

        } catch (LoginException e) {
            e.printStackTrace();
        } catch (ValueFormatException e) {
            e.printStackTrace();
        } catch (PathNotFoundException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return ticketList;
    }

    @Override
    public Ticket getTicket(String ticketName) {
        ResourceResolver adminResourceResolver = null;
        Ticket ticket = null;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");
            adminResourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
            Resource resource = adminResourceResolver.getResource(PATH_FORMS + "/ticket_" + ticketName);
            Node node = resource.adaptTo(Node.class);
            Node cNode = node.getNode("");
            String description = cNode.getProperty("Description").getValue().getString();
            String status = cNode.getProperty("Status").getValue().getString();
            String createdBy = cNode.getProperty("CreatedBy").getValue().getString();
            String assignTo = cNode.getProperty("AssignTo").getValue().getString();
            String attachments = "";

            Resource resourceAtt = adminResourceResolver.getResource(PATH_FORMS + "/ticket_" + ticketName);

            if(resourceAtt != null) {
                Node nodeAtt = resource.adaptTo(Node.class);
                NodeIterator nodeItrAtt = nodeAtt.getNodes();
                while (nodeItrAtt.hasNext()) {
                    Node cNodeAtt = nodeItrAtt.nextNode();
                    String att = cNodeAtt.getName();
                    if (att.contains(".jpg"))
                        attachments += att;
                }
            }

            ticket = new Ticket.Builder()
                    .setTicketName(ticketName)
                    .setDescription(description)
                    .setStatus(status)
                    .setCreatedBy(createdBy)
                    .setAssignTo(assignTo)
                    .setAttachments(attachments)
                    .build();

        } catch (LoginException e) {
            e.printStackTrace();
        } catch (PathNotFoundException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public List<CommentJira> getComments(String ticketName) {
        ResourceResolver adminResourceResolver = null;
        CommentJira commentJira = null;
        List<CommentJira> commentJiraList = new ArrayList<CommentJira>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");
            adminResourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
            Resource resource = adminResourceResolver.getResource(PATH_FORMS + "/ticket_" + ticketName);

            if(resource != null) {
                Node node = resource.adaptTo(Node.class);
                NodeIterator nodeItr = node.getNodes();
                while (nodeItr.hasNext()) {
                    Node cNode = nodeItr.nextNode();

                    if (cNode.getName().contains("comment_")) {
                        String comment = cNode.getProperty("Comment").getValue().getString();
                        String dateTime = cNode.getProperty("DateTime").getValue().getString();
                        String user = cNode.getProperty("User").getValue().getString();

                        commentJira = new CommentJira.Builder()
                                .setComment(comment)
                                .setDatetime(dateTime)
                                .setUser(user)
                                .build();

                        commentJiraList.add(commentJira);
                    }
                }
            }

        } catch (ValueFormatException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        return commentJiraList;
    }

    @Override
    public String getRole(String username) {
        ResourceResolver adminResourceResolver = null;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");
            adminResourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
            Resource resource = adminResourceResolver.getResource("/home/users/test");

            if(resource != null)
            {
                Node node = resource.adaptTo(Node.class);
                NodeIterator nodeItr = node.getNodes();
                while(nodeItr.hasNext())
                {
                    Node cNode = nodeItr.nextNode();
                    String name = cNode.getProperty("rep:principalName").getValue().getString();
                    if (name.equals(username)){
                        String role = cNode.getProperty("Role").getValue().getString();
                        adminResourceResolver.close();
                        return role;
                    }
                }
            }


        } catch (LoginException e) {
            e.printStackTrace();
        } catch (ValueFormatException e) {
            e.printStackTrace();
        } catch (PathNotFoundException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return null;
    }
}
