package com.aem.community.core.servlets;


import com.aem.community.core.models.Reservation;
import com.aem.community.core.service.DatesService;
import com.aem.community.core.service.DatesServiceImpl;
import com.aem.community.core.validators.Validator;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.jackrabbit.core.util.db.ConnectionHelper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.swing.*;
import java.io.IOException;
import java.rmi.ServerException;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SlingServlet(
        methods = {"POST"},
        paths = {"/bin/pingPongServlet"},
        metatype = true)
public class PingPongServlet extends SlingAllMethodsServlet {

    private static String NAME = "name";
    private static String DATE_HOUR = "dateTime";
    private static String JCR_PRIMARY_TYPE = "jcr:primaryType";
    private static String SLING_ORDERED_FOLDER = "sling:OrderedFolder";
    private static String NT_UNSTRUCTURED =  "nt:unstructured";
    private static String PATH_FORMS = "/content/Dates";
    private static String JCR_CONTENT = "jcr:content";

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Reference
    DatesService datesService;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        List<Reservation> reservations = datesService.getReservations();
        Validator validator = new Validator();
        System.out.println(request.getParameter("dateTime"));
        if (!validator.validateDateHour(request.getParameter("dateTime"))){
            response.sendRedirect("https://www.google.com/search?sxsrf=ACYBGNTBEW1QIekjGpuep9jbapzOpJLj9w%3A1565334910595&ei=fh1NXe75I-6trgTbgI8o&q=todays+date+and+hour");
        }
        else if(validator.checkDateExist(reservations, request.getParameter("dateTime"))){
            response.sendRedirect("https://www.google.com/search?sxsrf=ACYBGNRmtr_oeGnq1u0SLKZzUiTkG23gKw%3A1565348176217&ei=UFFNXf36DLWD1fAPhrqh8AU&q=Check+existing+reservation");
        }
        else{
            saveDate(request);
            response.sendRedirect("http://localhost:4502/content/AEM62App/en/ping-pong-page.html");
        }
    }

    private void saveDate(SlingHttpServletRequest request){
        ResourceResolver adminResourceResolver = null;
        try {

            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");
            adminResourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);

            Resource resource = adminResourceResolver.getResource(PATH_FORMS);
            Map<String,Object> properties = new HashMap<String,Object>();
            properties.put(JCR_PRIMARY_TYPE, SLING_ORDERED_FOLDER);
            properties.put("Name", request.getParameter(NAME));
            String name2 = request.getParameter(NAME + "2");
            if (name2 == null)
                name2 = "";
            properties.put("SecondName", name2);
            String name3 = request.getParameter(NAME + "3");
            if (name3 == null)
                name3 = "";
            properties.put("ThirdName", name3);
            String name4 = request.getParameter(NAME + "4");
            if (name4 == null)
                name4 = "";
            properties.put("FourthName", name4);
            String dateHour = request.getParameter(DATE_HOUR);
            String newdateHour = dateHour.split(":")[0] + ":00";
            newdateHour = newdateHour.replace("T", " ");
            properties.put("DateHour", newdateHour);
            int count = countForms(resource);
            adminResourceResolver.create(resource, "date_" + count, properties);

            adminResourceResolver.commit();
            adminResourceResolver.close();
        }
        catch (final Exception e)
        {
            System.out.println("ERROR: " + e.getMessage() + " , " + e.getClass());
        }
        finally {
            adminResourceResolver.close();
        }
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
