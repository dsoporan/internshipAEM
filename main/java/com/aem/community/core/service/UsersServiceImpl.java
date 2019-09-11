package com.aem.community.core.service;

import com.aem.community.core.models.Pair;
import com.aem.community.core.models.Reservation;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import javax.jcr.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(immediate = true)
@Service(value = UsersService.class)
public class UsersServiceImpl implements UsersService {

    private static String PATH_USERS = "/content/Users";

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public List<Pair> getUsers() {
        List<Pair> pairs = new ArrayList<Pair>();
        ResourceResolver adminResourceResolver = null;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");
            adminResourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
            Resource resource = adminResourceResolver.getResource(PATH_USERS);

            if (resource != null) {
                Node node = resource.adaptTo(Node.class);
                NodeIterator nodeItr = node.getNodes();
                while (nodeItr.hasNext()) {
                    Node cNode = nodeItr.nextNode();
                    String username = cNode.getProperty("Username").getValue().getString();
                    String email = cNode.getProperty("Email").getValue().getString();
                    Pair pair = new Pair(username, email);
                    pairs.add(pair);
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
        return pairs;
    }

    @Override
    public String getUser(String username) {
        ResourceResolver adminResourceResolver = null;
        String password = null;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");
            adminResourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
            Resource resource = adminResourceResolver.getResource(PATH_USERS + "/" + username);

            if (resource != null) {
                Node node = resource.adaptTo(Node.class);
                Node userNode = node.getNode("");
                password = userNode.getProperty("Password").getValue().getString();
            }
        } catch (PathNotFoundException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (ValueFormatException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return password;
    }
}
