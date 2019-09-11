package com.aem.community.core.servlets;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import java.io.IOException;
import java.rmi.ServerException;

@SlingServlet(
        methods = {"GET"},
        paths = {"/bin/commentServlet"},
        metatype = true)
public class CommentServlet extends SlingAllMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        String postId = request.getParameter("postId");
        System.out.println("Comment Servlet");
        response.sendRedirect("http://localhost:4502/content/AEM62App/en/comments-page.html?postId=" + postId);
    }
}
