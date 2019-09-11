package com.aem.community.core.servlets;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.jcr.Node;
import javax.jcr.Session;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SlingServlet(
        methods = {"POST"},
        resourceTypes = {"AEM62App/components/content/contactFormComponent"},
        metatype = true)
public class FormServlet extends SlingAllMethodsServlet {

    private static String FIRST_NAME = "firstname";
    private static String LAST_NAME = "lastname";
    private static String MESSAGE = "message";
    private static String EMAIL = "email";
    private static String PHONE = "phone";
    private static String JCR_PRIMARY_TYPE = "jcr:primaryType";
    private static String SLING_ORDERED_FOLDER = "sling:OrderedFolder";
    private static String NT_UNSTRUCTURED =  "nt:unstructured";
    private static String PATH_FORMS = "/content/Forms";
    private static String JCR_CONTENT = "jcr:content";

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        ResourceResolver adminResourceResolver = null;
        try {

            Map<String,Object> paramMap = new HashMap<String,Object>();
            //Mention the subServiceName you had used in the User Mapping
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");
            adminResourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);

//            adminResourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);

            if (!checkForNulls(request))
                return;
            Resource resource = adminResourceResolver.getResource(PATH_FORMS);
            Map<String,Object> properties = new HashMap<String,Object>();
            properties.put(JCR_PRIMARY_TYPE, SLING_ORDERED_FOLDER);
            int count = countForms(resource);
            adminResourceResolver.create(resource, "form_" + count, properties);

            Resource resourceProperties = adminResourceResolver.getResource(PATH_FORMS + "/form_" + count);
            properties = new HashMap<String,Object>();
            properties.put(JCR_PRIMARY_TYPE, NT_UNSTRUCTURED);
            properties.put("First Name", request.getParameter(FIRST_NAME));
            properties.put("Last Name", request.getParameter(LAST_NAME));
            properties.put("Message",  request.getParameter(MESSAGE));
            properties.put("Email",  request.getParameter(EMAIL));
            properties.put("Phone",  request.getParameter(PHONE));

            adminResourceResolver.create(resourceProperties, JCR_CONTENT , properties);
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
        Iterator<Resource> iterator= resource.listChildren();
        int count = 0;
        while(iterator.hasNext()) {
            count++;
            iterator.next();
        }
        return count;
    }

    private boolean checkForNulls(SlingHttpServletRequest request){
        if (request.getParameter("firstname") == null ||
                request.getParameter("lastname") == null ||
                request.getParameter("message") == null ||
                request.getParameter("email") == null ||
                request.getParameter("phone") == null)
            return false;
        return true;
    }
}
