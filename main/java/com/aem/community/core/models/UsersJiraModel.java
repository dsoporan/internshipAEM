package com.aem.community.core.models;

import com.adobe.cq.sightly.WCMUse;
import com.aem.community.core.service.AllTickets;
import com.aem.community.core.service.AllUsers;

import java.util.ArrayList;
import java.util.List;

public class UsersJiraModel extends WCMUse {

    private List<String> allUsersList = new ArrayList<String>();
    private List<Pair> allSignUpList = new ArrayList<Pair>();

    @Override
    public void activate() throws Exception {
        System.out.println("User Jira");
        AllUsers allUsers = getSlingScriptHelper().getService(AllUsers.class);
        allUsersList = allUsers.getUsers();
        allSignUpList = allUsers.getSignUpUsers();
    }

    public List<String> getAllUsersList() {
        return allUsersList;
    }

    public List<Pair> getAllSignUpList() {
        return allSignUpList;
    }
}
