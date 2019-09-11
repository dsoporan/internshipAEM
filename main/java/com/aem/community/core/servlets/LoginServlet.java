package com.aem.community.core.servlets;


import com.aem.community.core.constants.MyConstants;
import com.aem.community.core.service.UserService;
import com.aem.community.core.service.UsersService;
import com.aem.community.core.service.UsersServiceImpl;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import java.io.IOException;
import java.rmi.ServerException;


@SlingServlet(
        methods = {"POST"},
        paths = {"/bin/loginServlet"},
        metatype = true)
public class LoginServlet extends SlingAllMethodsServlet {
    @Reference
    UsersService usersService;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        System.out.println("Login");
        String inputUsername = request.getParameter(MyConstants.USERNAME);
        String inputPassword = request.getParameter(MyConstants.PASSWORD);
        String password = null;
        password = usersService.getUser(inputUsername);
        if (password.equals(inputPassword)) {
            response.sendRedirect("http://localhost:4502/content/AEM62App/en/ping-pong-page.html");
        }
        else
            response.sendRedirect("http://localhost:4502/content/AEM62App/en/login.html");
    }
}
