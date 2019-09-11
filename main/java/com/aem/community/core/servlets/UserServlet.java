package com.aem.community.core.servlets;


import com.aem.community.core.models.User;
import com.aem.community.core.service.UserService;
import com.aem.community.core.service.UserServiceImpl;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.IOException;
import java.rmi.ServerException;

@SlingServlet(
        methods = {"GET"},
        paths = {"/bin/userServlet"},
        metatype = true)
public class UserServlet extends SlingAllMethodsServlet {

    private String usernameId;
    private User user = null;

    public String getUsernameId() {
        return usernameId;
    }

    public User getUser() {
        return user;
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        usernameId = request.getParameter("usernameId");
        response.sendRedirect("http://localhost:4502/content/AEM62App/en/user-page.html?usernameId=" + usernameId);
    }

}
