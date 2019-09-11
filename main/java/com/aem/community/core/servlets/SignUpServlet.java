package com.aem.community.core.servlets;


import com.aem.community.core.constants.MyConstants;
import com.aem.community.core.models.Pair;
import com.aem.community.core.models.Reservation;
import com.aem.community.core.service.UsersService;
import com.day.cq.security.AccountManager;
import com.day.cq.security.AccountManagerFactory;
import com.mysql.cj.util.StringUtils;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.jcr.*;
import java.io.IOException;
import java.rmi.ServerException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SlingServlet(
        methods = {"POST"},
        paths = {"/bin/signUpServlet"},
        metatype = true)
public class SignUpServlet extends SlingAllMethodsServlet {
    private static String JCR_PRIMARY_TYPE = "jcr:primaryType";
    private static String SLING_ORDERED_FOLDER = "sling:OrderedFolder";
    private static String PATH_USERS = "/content/Users";

    @Reference
    UsersService usersService;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        System.out.println("Sign Up");
        String username = request.getParameter(MyConstants.USERNAME);
        String email = request.getParameter(MyConstants.EMAIL);
        if (!checkUserExist(username, email)) {
            saveDate(request);
            response.sendRedirect("http://localhost:4502/content/AEM62App/en/login-jira.html");
        }
        else
        {
            response.sendRedirect("http://localhost:4502/content/AEM62App/en/first.html");
        }
    }

    private boolean checkUserExist(String username, String email){
        List<Pair> pairs = new ArrayList<Pair>();
        pairs = usersService.getUsers();
        for(Pair pair: pairs){
            if (pair.getUsername().equals(username) || pair.getEmail().equals(email))
                return true;
        }
        return false;
    }

    private void saveDate(SlingHttpServletRequest request){
        ResourceResolver adminResourceResolver = null;
        try {

            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");
            adminResourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
            final UserManager userManager = adminResourceResolver.adaptTo(UserManager.class);


            if(userManager.getAuthorizable(request.getParameter(MyConstants.USERNAME))==null){
                User user = userManager.createUser(request.getParameter(MyConstants.USERNAME), request.getParameter(MyConstants.PASSWORD), new SimplePrincipal(request.getParameter(MyConstants.USERNAME)), "/home/users/test");
            }
            else
            {
                System.out.println("User already exist..");
            }
//            Resource resource = adminResourceResolver.getResource(PATH_USERS);
//            Map<String,Object> properties = new HashMap<String,Object>();
//            properties.put(JCR_PRIMARY_TYPE, SLING_ORDERED_FOLDER);
//            properties.put("Email", request.getParameter(MyConstants.EMAIL));
//            properties.put("Username", request.getParameter(MyConstants.USERNAME));
//            properties.put("FirstName", request.getParameter(MyConstants.FIRST_NAME));
//            properties.put("LastName", request.getParameter(MyConstants.LAST_NAME));
//            properties.put("Password", request.getParameter(MyConstants.PASSWORD));
//            adminResourceResolver.create(resource, request.getParameter(MyConstants.USERNAME), properties);

            Group group = (Group)(userManager.getAuthorizable("administrators"));
            group.addMember(userManager.getAuthorizable(request.getParameter(MyConstants.USERNAME)));
            adminResourceResolver.commit();
        }
        catch (final Exception e)
        {
            System.out.println("ERROR: " + e.getMessage() + " , " + e.getClass());
        }
        finally {
            adminResourceResolver.close();
            addProperty(request.getParameter(MyConstants.USERNAME));
        }
    }

    private void addProperty(String username){
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
                    if (name.equals(username)){
                        cNode.setProperty("Role", "Junior").getValue().getString();
                        adminResourceResolver.commit();
                        adminResourceResolver.close();
                        return;
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
    }

    private static class SimplePrincipal implements Principal {
        protected final String name;

        public SimplePrincipal(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Principal) {
                return name.equals(((Principal) obj).getName());
            }
            return false;
        }
    }
}
