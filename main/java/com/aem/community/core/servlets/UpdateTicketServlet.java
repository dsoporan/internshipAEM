package com.aem.community.core.servlets;

import com.aem.community.core.models.Ticket;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

@SlingServlet(
        methods = {"POST"},
        paths = {"/bin/updateTicketServlet"},
        metatype = true)
public class UpdateTicketServlet extends SlingAllMethodsServlet {
    private static String PATH_FORMS = "/content/dam/AEM62App/tickets";

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        ResourceResolver adminResourceResolver = null;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");
            adminResourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
            Resource resource = adminResourceResolver.getResource(PATH_FORMS + "/ticket_" + request.getParameter("ticketName"));
            Node node = resource.adaptTo(Node.class);
            Node cNode = node.getNode("");
            cNode.setProperty("Description", request.getParameter("description"));
            cNode.setProperty("Status", request.getParameter("status"));
            cNode.setProperty("AssignTo", request.getParameter("assignTo"));

            adminResourceResolver.commit();
            adminResourceResolver.close();

        } catch (LoginException e) {
            e.printStackTrace();
        } catch (PathNotFoundException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        response.sendRedirect("http://localhost:4502/content/AEM62App/en/jira-page.html?ticketName=" + request.getParameter("ticketName"));
    }
}
