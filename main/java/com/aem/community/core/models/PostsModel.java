package com.aem.community.core.models;

import com.adobe.cq.sightly.WCMUse;
import com.aem.community.core.constants.MyConstants;
import com.aem.community.core.service.PostsService;
import com.aem.community.core.service.PostsServiceImpl;
import com.aem.community.core.service.ServiceNodeJS;
import com.aem.community.core.utils.RetrieveJSON;

import java.util.List;

public class PostsModel extends WCMUse {

    private static PostsService postsService = new PostsServiceImpl();
    private static String jsonString;
    private static List<Post> postList;

    public static PostsService getPostsService() {
        return postsService;
    }

    public static String getJsonString() {
        return jsonString;
    }

    public static List<Post> getPostList() {
        return postList;
    }

    @Override
    public void activate(){
        RetrieveJSON retrieveJSON = new RetrieveJSON(MyConstants.URL_POSTS);
        jsonString = retrieveJSON.getJsonFromUrl();
        postList = postsService.getPostsfromJson(jsonString);
//        printPosts(postList);
        ServiceNodeJS serviceNodeJS = new ServiceNodeJS();
        System.out.println(serviceNodeJS.getFromNode());
    }

    private void printPosts(List<Post> posts){
        for(Post post: posts){
            System.out.println(post);
        }
    }
}
