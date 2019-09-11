package com.aem.community.core.models;

import com.adobe.cq.sightly.WCMUse;
import com.aem.community.core.service.AllUsers;

import java.util.ArrayList;
import java.util.List;

public class UserAdminModel extends WCMUse {

    private List<Pair> signUpUsers = new ArrayList<Pair>();

    @Override
    public void activate() throws Exception {

        System.out.println("User Administration");
        AllUsers allUsers = getSlingScriptHelper().getService(AllUsers.class);
        signUpUsers = allUsers.getSignUpUsers();
    }

    public List<Pair> getSignUpUsers() {
        return signUpUsers;
    }
}
