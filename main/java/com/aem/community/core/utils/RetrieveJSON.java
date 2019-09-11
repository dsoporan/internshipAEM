package com.aem.community.core.utils;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class RetrieveJSON {

    private String URL;

    public RetrieveJSON() {
    }

    public RetrieveJSON(String URL) {
        this.URL = URL;
    }

    public String getJsonFromUrl() {
        String content = null;
        URLConnection connection = null;
        try {
            connection =  new URL(URL).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        }catch ( Exception ex ) {
            ex.printStackTrace();
        }
        return content;
    }

    public String getJsonFromUrl(String id) {
        String content = null;
        URLConnection connection = null;
        try {
            connection =  new URL(URL + "/" + id).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        }catch ( Exception ex ) {
            ex.printStackTrace();
        }
        return content;
    }
}
