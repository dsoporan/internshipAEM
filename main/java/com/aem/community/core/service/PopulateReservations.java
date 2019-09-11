package com.aem.community.core.service;

import com.aem.community.core.models.Reservation;

import java.util.List;

public class PopulateReservations {
    public List<String> populate(List<Reservation> reservations, List<String> hours, String currentDate){
        String curr;
        for(Reservation reservation: reservations){
            curr = reservation.getDate();
            if (curr.equals(currentDate.split(" ")[0])){
                int index = hours.indexOf(reservation.getHour());
                if (index != -1){
                    hours.set(index, reservation.getName() + " - " + reservation.getHour());
                }
            }
        }
        return hours;
    }
}
