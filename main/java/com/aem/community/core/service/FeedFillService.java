package com.aem.community.core.service;

import com.aem.community.core.models.Entry;

import java.util.List;

public interface FeedFillService {
    String getHtmlFromUrl();
    List<Entry> getEntriesFromXml(String xmlString);
}
