package com.aem.community.core.models;

import com.adobe.cq.sightly.WCMUse;
import com.aem.community.core.service.FeedFillService;
import com.aem.community.core.service.FeedFillServiceImpl;

import java.util.List;

public class FeedComponentModel extends WCMUse{

    private FeedFillService feedFillService = new FeedFillServiceImpl();
    private String valueXml;
    private List<Entry> entriesList;

    @Override
    public void activate(){
        valueXml = feedFillService.getHtmlFromUrl();
        entriesList = feedFillService.getEntriesFromXml(valueXml);

    }

    public void printEntries(List<Entry> entries){
        for(Entry entry: entries){
            System.out.println(entry);
        }
    }

    public String getValueXml() {
        return valueXml;
    }

    public void setValueXml(String valueXml) {
        this.valueXml = valueXml;
    }

    public List<Entry> getEntriesList() {
        return entriesList;
    }
}
