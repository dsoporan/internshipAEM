package com.aem.community.core.models;

import com.adobe.cq.sightly.WCMUse;
import com.aem.community.core.service.DatesService;
import com.aem.community.core.service.DatesServiceImpl;
import com.aem.community.core.service.PopulateReservations;
import org.apache.sling.api.SlingHttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservationModel extends WCMUse {

    private List<Reservation> reservationList;
    private List<String> hourList = new ArrayList<String>();
    private String currentDate;
    private String user;

    @Override
    public void activate() {
        String dateParameter = getRequest().getParameter("date");
        if (dateParameter == null){
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            currentDate = formatter.format(calendar.getTime());
        }
        else
            currentDate = dateParameter;
        hourList.add("10:00");
        hourList.add("11:00");
        hourList.add("12:00");
        hourList.add("13:00");
        hourList.add("14:00");
        hourList.add("15:00");
        hourList.add("16:00");
        hourList.add("17:00");
        hourList.add("18:00");
        hourList.add("19:00");
        hourList.add("20:00");
        DatesService datesService = getSlingScriptHelper().getService(DatesService.class);
        reservationList = datesService.getReservations();
        user = datesService.getUser(getRequest());
        PopulateReservations populateReservations = new PopulateReservations();
        hourList = populateReservations.populate(reservationList, hourList, currentDate);
    }

    public List<String> getHourList() {
        return hourList;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String getUser() {
        return user;
    }
}
