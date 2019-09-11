package com.aem.community.core.service;

import com.aem.community.core.models.Reservation;
import org.apache.sling.api.SlingHttpServletRequest;

import java.util.List;

public interface DatesService {
    List<Reservation> getReservations();
    String getUser(SlingHttpServletRequest request);
}
