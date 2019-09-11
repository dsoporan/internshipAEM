package com.aem.community.core.service;

import aQute.bnd.annotation.component.Component;
import com.aem.community.core.constants.MyConstants;
import com.aem.community.core.models.Post;
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
public class PostsServiceImpl implements PostsService {

    @Override
    public List<Post> getPostsfromJson(String jsonString) {
        List<Post> postList = new ArrayList<Post>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int index = 0; index < jsonArray.length(); index++){
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                Post post = new Post.Builder()
                                .setUsernameId(jsonObject.getString(MyConstants.USER_ID))
                                .setUsername(getUsernameFromId(jsonObject.getString(MyConstants.USER_ID)))
                                .setId(jsonObject.getString(MyConstants.ID))
                                .setTitle(jsonObject.getString(MyConstants.TITLE))
                                .setBody(jsonObject.getString(MyConstants.BODY))
                                .build();
                postList.add(post);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postList;
    }

    private String getUsernameFromId(String id){
        String content = null;
        URLConnection connection = null;
        String username = null;
        try {
            connection =  new URL(URL_USERS + "/" + id).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();

            JSONObject jsonObject = new JSONObject(content);
            username = jsonObject.getString(MyConstants.USERNAME);

        }catch ( Exception ex ) {
            ex.printStackTrace();
        }

        return username;
    }
}
