package com.aem.community.core.servlets;

import com.day.cq.dam.api.AssetManager;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.jcr.Session;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

@SlingServlet(
        methods = {"POST"},
        paths = {"/bin/addTicketServlet"},
        metatype = true)
public class AddTicketServlet extends SlingAllMethodsServlet {
    private static String PATH_FORMS = "/content/dam/AEM62App/tickets";
    private static String JCR_PRIMARY_TYPE = "jcr:primaryType";
    private static String SLING_ORDERED_FOLDER = "sling:OrderedFolder";

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        System.out.println("Add Ticket SERVLET");
        ResourceResolver adminResourceResolver = null;
        try {

            Map<String,Object> paramMap = new HashMap<String,Object>();
            //Mention the subServiceName you had used in the User Mapping
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");
            adminResourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);

            Resource resource = adminResourceResolver.getResource(PATH_FORMS);
            Map<String,Object> properties = new HashMap<String,Object>();
            properties.put(JCR_PRIMARY_TYPE, SLING_ORDERED_FOLDER);
            properties.put("TicketName", request.getParameter("ticketName"));
            properties.put("Description", request.getParameter("description"));
            properties.put("AssignTo", request.getParameter("assign"));
            properties.put("Status", request.getParameter("status"));

            ResourceResolver resourceResolver = request.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);

            properties.put("CreatedBy", session.getUserID());
            adminResourceResolver.create(resource, "ticket_" + request.getParameter("ticketName"), properties);
            adminResourceResolver.commit();

            final org.apache.sling.api.request.RequestParameter param = request.getRequestParameter("myFiles");
            final InputStream stream = param.getInputStream();
            writeToDam(stream, param.toString(), request.getParameter("ticketName"));

            session.logout();
        }
        catch (final Exception e)
        {
            System.out.println("ERROR: " + e.getMessage() + " , " + e.getClass());
        }
        finally {
            adminResourceResolver.close();
        }
        response.sendRedirect("http://localhost:4502/content/AEM62App/en/jira-page.html");
    }

    private String writeToDam(InputStream is, String fileName, String ticketname)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put(ResourceResolverFactory.SUBSERVICE, "writeService");
        ResourceResolver resolver = null;

        try {

            //Invoke the adaptTo method to create a Session used to create a QueryManager
            resolver = resourceResolverFactory.getServiceResourceResolver(param);

            //Use AssetManager to place the file into the AEM DAM
            AssetManager assetMgr = resolver.adaptTo(AssetManager.class);
            String newFile = PATH_FORMS + "/ticket_" + ticketname + "/" + fileName;
            assetMgr.createAsset(newFile, is, "image/jpeg",true);


            // Return the path to the file was stored
            return newFile;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
