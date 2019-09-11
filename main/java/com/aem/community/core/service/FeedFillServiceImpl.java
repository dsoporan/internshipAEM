package com.aem.community.core.service;

import aQute.bnd.annotation.component.Component;
import com.aem.community.core.models.Entry;
import com.aem.community.core.utils.RetrieveJSON;
import org.apache.felix.scr.annotations.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.ServiceMode;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Component
@Service
public class FeedFillServiceImpl implements FeedFillService {

    private static String URL = "https://stackoverflow.com/feeds";

    @Override
    public String getHtmlFromUrl() {
        RetrieveJSON retrieveJSON = new RetrieveJSON(URL);
        return retrieveJSON.getJsonFromUrl();
    }

    @Override
    public List<Entry> getEntriesFromXml(String xmlString) {
        List<Entry> entriesList = new ArrayList<Entry>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlString)));
//            Element rootElement = document.getDocumentElement();
            NodeList nodeList = document.getElementsByTagName("entry");
            for (int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                Element element = (Element) node;
                Entry entry = new Entry();
                entry.setId(element.getElementsByTagName("id").item(0).getTextContent());
                entry.setTitle(element.getElementsByTagName("title").item(0).getTextContent());
                Node nodeForAuthor = element.getElementsByTagName("author").item(0);
                Element elementForAuthor = (Element) nodeForAuthor;
                entry.setName(elementForAuthor.getElementsByTagName("name").item(0).getTextContent());
                entry.setAuthorUri(elementForAuthor.getElementsByTagName("uri").item(0).getTextContent());
                entry.setPublished(element.getElementsByTagName("published").item(0).getTextContent());
                entry.setUpdated(element.getElementsByTagName("updated").item(0).getTextContent());
                entriesList.add(entry);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entriesList;
    }
}
