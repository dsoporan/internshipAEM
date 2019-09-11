package com.aem.community.core.models;

import com.adobe.cq.sightly.WCMUse;
import com.aem.community.core.constants.MyConstants;
import com.aem.community.core.service.UserService;
import com.aem.community.core.service.UserServiceImpl;
import com.aem.community.core.utils.RetrieveJSON;

import java.util.List;

import static com.aem.community.core.constants.MyConstants.URL_TODOS;
import static com.aem.community.core.constants.MyConstants.URL_USERS;

public class UserModel extends WCMUse {


    private User user;
    private List<ToDo> todosList;


    public UserModel() {
    }

    public UserModel(User user) {
        this.user = user;
    }

    @Override
    public void activate(){
        String usernameId = getRequest().getParameter(MyConstants.USERNAME_ID);
        RetrieveJSON retrieveJSON = new RetrieveJSON(URL_USERS);
        UserService userService = new UserServiceImpl(usernameId);
        String content = retrieveJSON.getJsonFromUrl(usernameId);
        user = userService.getUserfromJson(content);
        retrieveJSON = new RetrieveJSON(URL_TODOS);
        String contentTodos = retrieveJSON.getJsonFromUrl();
        todosList = userService.getTodosfromJson(contentTodos);
    }


    public User getUser() {
        return user;
    }

    public List<ToDo> getTodosList() {
        return todosList;
    }
}
