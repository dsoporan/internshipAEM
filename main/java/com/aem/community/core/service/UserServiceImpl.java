package com.aem.community.core.service;


import aQute.bnd.annotation.component.Component;
import com.aem.community.core.constants.MyConstants;
import com.aem.community.core.models.*;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.aem.community.core.constants.MyConstants.URL_USERS;

@Component
@Service
public class UserServiceImpl implements UserService {

    private static String usernameId;
    private User userS = null;

    public UserServiceImpl(String userId){
        usernameId = userId;
    }

    public UserServiceImpl() {
    }

    public static String getUsernameId() {
        return usernameId;
    }

    public User getUserS() {
        return userS;
    }

    @Override
    public User getUserfromJson(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject addressJsonObject = new JSONObject(jsonObject.getString(MyConstants.ADDRESS));
            JSONObject geoJsonObject = new JSONObject(addressJsonObject.getString(MyConstants.GEO));

            Address address = new Address.Builder()
                        .setStreet(addressJsonObject.getString(MyConstants.STREET))
                        .setSuite(addressJsonObject.getString(MyConstants.SUITE))
                        .setCity(addressJsonObject.getString(MyConstants.CITY))
                        .setLat(geoJsonObject.getString(MyConstants.LAT))
                        .setLng(geoJsonObject.getString(MyConstants.LNG))
                        .build();

            JSONObject companyJsonObject = new JSONObject(jsonObject.getString(MyConstants.COMPANY));
            User user = new User.Builder()
                        .setId(usernameId)
                        .setNume(jsonObject.getString(MyConstants.NAME))
                        .setMail(jsonObject.getString(MyConstants.EMAIL))
                        .setAddress(address)
                        .setPhone(jsonObject.getString(MyConstants.PHONE))
                        .setWebsite(jsonObject.getString(MyConstants.WEBSITE))
                        .setCompanyName(companyJsonObject.getString(MyConstants.NAME))
                        .setCompanyCatchPhrase(companyJsonObject.getString(MyConstants.CATCH_PHRASE))
                        .build();

            this.userS = user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return this.userS;
    }

    @Override
    public List<ToDo> getTodosfromJson(String jsonString) {
        List<ToDo> todosList = new ArrayList<ToDo>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int index = 0; index < jsonArray.length(); index++){
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                if (jsonObject.getString(MyConstants.USER_ID).equals(usernameId)) {
                    ToDo toDo = new ToDo.Builder()
                            .setId(jsonObject.getString(MyConstants.ID))
                            .setTitle(jsonObject.getString(MyConstants.TITLE))
                            .setCompleted(jsonObject.getString(MyConstants.COMPLETED))
                            .build();
                    todosList.add(toDo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return todosList;
    }
}
