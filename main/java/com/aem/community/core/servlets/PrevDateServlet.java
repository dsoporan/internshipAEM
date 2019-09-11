package com.aem.community.core.servlets;

import com.aem.community.core.validators.Validator;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import java.io.IOException;
import java.rmi.ServerException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SlingServlet(
        methods = {"GET"},
        paths = {"/bin/prevDateServlet"},
        metatype = true)
public class PrevDateServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        String fetchedDate = request.getParameter("date");
        fetchedDate += " 10:00";
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Calendar calendar = Calendar.getInstance();
//        try {
//            calendar.setTime(formatter.parse(fetchedDate));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        calendar.add(Calendar.DAY_OF_MONTH, -1);
//        String prevDate = formatter.format(calendar.getTime());
        response.sendRedirect("http://localhost:4502/content/AEM62App/en/ping-pong-page.html?date=" + fetchedDate);
    }
}
