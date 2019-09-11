package com.aem.community.core.service;

import com.aem.community.core.models.Pair;

import java.util.List;

public interface AllUsers {
    List<String> getUsers();
    List<Pair> getSignUpUsers();
}
