package com.aem.community.core.servlets;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.jcr.*;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

@SlingServlet(
        methods = {"POST"},
        paths = {"/bin/updateRoleServlet"},
        metatype = true)
public class UpdateRoleServlet extends SlingAllMethodsServlet {
    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        System.out.println("Update Role");
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
                    System.out.println(cNode.getName());
                    String name = cNode.getProperty("rep:principalName").getValue().getString();
                    System.out.println(name);
                    if (name.equals(request.getParameter("user"))){
                        cNode.setProperty("Role", request.getParameter("role")).getValue().getString();
                        adminResourceResolver.commit();
                        adminResourceResolver.close();
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
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        response.sendRedirect("http://localhost:4502/content/AEM62App/en/user-administration.html");
    }
}
