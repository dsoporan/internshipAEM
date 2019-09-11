package com.aem.community.core.service;

import com.aem.community.core.models.Pair;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.resource.LoginException;

import javax.jcr.*;
import java.util.*;

@Component(immediate = true)
@Service(value = AllUsers.class)
public class AllUsersImpl implements AllUsers {

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public List<String> getUsers() {
        List<String> allUsers = new ArrayList<String>();
        ResourceResolver resourceResolver = null;
        Session session = null;
        try {
            resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
            session = resourceResolver.adaptTo(Session.class);
            //Create a UserManager instance from the session object
            UserManager userManager = ((JackrabbitSession) session).getUserManager();

            Iterator<Authorizable> users = null ;
            users = userManager.findAuthorizables("jcr:primaryType", "rep:User");

            while (users.hasNext()) {

                Authorizable auth = users.next();
                if (!auth.isGroup()) {

                    //Get the ID of the user
                    String id = auth.getID();
                    allUsers.add(id);
                }
            }
            // Log out
            session.logout();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (UnsupportedRepositoryOperationException e) {
            e.printStackTrace();
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

        return allUsers;
    }

    @Override
    public List<Pair> getSignUpUsers() {
        List<Pair> signUpUsers = new ArrayList<Pair>();
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
                    String role = cNode.getProperty("Role").getValue().getString();
                    System.out.println(name);
                    signUpUsers.add(new Pair(name,role));
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
        return signUpUsers;
    }
}
