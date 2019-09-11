package com.aem.community.core.servlets;


import com.aem.community.core.models.Reservation;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.jcr.*;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.ServerException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SlingServlet(
        methods = {"POST"},
        paths = {"/bin/addCommentServlet"},
        metatype = true)
public class AddCommentServlet extends SlingAllMethodsServlet {
    private static String PATH_FORMS = "/content/dam/AEM62App/tickets";
    private static String JCR_PRIMARY_TYPE = "jcr:primaryType";
    private static String SLING_ORDERED_FOLDER = "sling:OrderedFolder";

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        System.out.println("Add Comment");
        String ticketName = request.getParameter("ticketName");
        ResourceResolver adminResourceResolver = null;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");
            adminResourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
            Resource resource = adminResourceResolver.getResource(PATH_FORMS + "/ticket_" + ticketName);

            Map<String,Object> properties = new HashMap<String,Object>();
            properties.put(JCR_PRIMARY_TYPE, SLING_ORDERED_FOLDER);
            properties.put("Comment", request.getParameter("comment"));
            properties.put("User", request.getParameter("user"));
            Date date = new Date();
            properties.put("DateTime", date.toString());


            adminResourceResolver.create(resource, "comment_" + countComments(resource), properties);
            adminResourceResolver.commit();

        }
        catch (LoginException e) {
            e.printStackTrace();
        }
        response.sendRedirect("http://localhost:4502/content/AEM62App/en/jira-page.html?ticketName=" + ticketName);
    }

    private int countComments(Resource resource){
        Iterator<Resource> iterator = resource.listChildren();
        int count = 0;
        while(iterator.hasNext()) {
            if (iterator.next().getName().contains("comment_"))
                count++;
        }
        return count;
    }
}
