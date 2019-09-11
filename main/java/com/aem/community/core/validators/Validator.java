package com.aem.community.core.validators;

import com.aem.community.core.models.Reservation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Validator {

    public boolean validateDateHour(String dateHour){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd;HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date).split(";")[0];
        String currentTime = formatter.format(date).split(";")[1];
        dateHour = dateHour.replace(" ", "T");
        String inputDate = dateHour.split("T")[0];
        String inputTime = dateHour.split("T")[1];

        int currentYear = Integer.parseInt(currentDate.split("-")[0]);
        int currentMonth = Integer.parseInt(currentDate.split("-")[1]);
        int currentDay = Integer.parseInt(currentDate.split("-")[2]);
        int currentHour = Integer.parseInt(currentTime.split(":")[0]);

        int inputYear = Integer.parseInt(inputDate.split("-")[0]);
        int inputMonth = Integer.parseInt(inputDate.split("-")[1]);
        int inputDay = Integer.parseInt(inputDate.split("-")[2]);
        int inputHour = Integer.parseInt(inputTime.split(":")[0]);

        if (currentYear > inputYear)
            return false;
        else if (currentYear < inputYear)
            return true;
        else{
            if (currentMonth > inputMonth)
                return false;
            else if (currentMonth < inputMonth)
                return true;
            else{
                if (currentDay > inputDay)
                    return false;
                else if (currentDay < inputDay)
                    return true;
                else{
                    if (currentHour > inputHour)
                        return false;
                    else if (currentHour < inputHour)
                        return true;
                    else return false;
                }
            }
        }

    }

    public boolean checkDateExist(List<Reservation> reservations, String date){
        date = date.replace("T", " ");
        for(Reservation reservation : reservations){
            String resDate = reservation.getDate() + " " + reservation.getHour();
            if (resDate.equals(date))
                return true;
        }
        return false;
    }
}
