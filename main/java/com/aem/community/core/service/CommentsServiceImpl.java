package com.aem.community.core.service;

import com.aem.community.core.constants.MyConstants;
import com.aem.community.core.models.Comment;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import java.util.ArrayList;
import java.util.List;



public class CommentsServiceImpl implements CommentsService {

    @Override
    public List<Comment> getCommentsFromJson(String jsonString, String postId) {
        List<Comment> commentsList = new ArrayList<Comment>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int index = 0; index < jsonArray.length(); index++){
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                if (jsonObject.getString(MyConstants.POST_ID).equals(postId)) {
                    Comment comment = new Comment.Builder()
                            .setId(jsonObject.getString(MyConstants.ID))
                            .setName(jsonObject.getString(MyConstants.NAME))
                            .setEmail(jsonObject.getString(MyConstants.EMAIL))
                            .setBody(jsonObject.getString(MyConstants.BODY))
                            .build();
                    commentsList.add(comment);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commentsList;
    }
}
