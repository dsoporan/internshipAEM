package com.aem.community.core.service;


import com.aem.community.core.models.Reservation;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.resource.LoginException;


import javax.jcr.*;
import java.util.*;

@Component(immediate = true)
@Service(value = DatesService.class)
public class DatesServiceImpl implements DatesService {
    private static String PATH_FORMS = "/content/Dates";

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public List<Reservation> getReservations() {
        List<Reservation> reservationList = new ArrayList<Reservation>();
        ResourceResolver adminResourceResolver = null;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");
            adminResourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
            Resource resource = adminResourceResolver.getResource(PATH_FORMS);

            if(resource != null)
            {
                Node node = resource.adaptTo(Node.class);
                NodeIterator nodeItr = node.getNodes();
                while(nodeItr.hasNext())
                {
                    Node cNode = nodeItr.nextNode();
                    String name = cNode.getProperty("Name").getValue().getString();
                    String name2 = cNode.getProperty("SecondName").getValue().getString();
                    String name3 = cNode.getProperty("ThirdName").getValue().getString();
                    String name4 = cNode.getProperty("FourthName").getValue().getString();
                    String bigName = name;
                    if (!name2.equals("")) {
                        bigName += ", " + name2;
                        if (!name3.equals("")) {
                            bigName += ", " + name3;
                            if (!name4.equals(""))
                                bigName += ", " + name4;
                        }
                    }
                    String dateHour = cNode.getProperty("DateHour").getValue().getString();
                    String date = dateHour.split(" ")[0];
                    String hour = dateHour.split(" ")[1];
                    Reservation reservation = new Reservation.Builder()
                            .setName(bigName)
                            .setDate(date)
                            .setHour(hour)
                            .build();
                    reservationList.add(reservation);
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
        return reservationList;
    }

    public String getUser(SlingHttpServletRequest request){
        ResourceResolver resourceResolver = request.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
        return session.getUserID();
    }

    private Integer countForms(Resource resource) {
        Iterator<Resource> iterator = resource.listChildren();
        int count = 0;
        while(iterator.hasNext()) {
            count++;
            iterator.next();
        }
        return count;
    }
}
