package com.aem.community.core.service;

import com.aem.community.core.models.Pair;

import java.util.List;

public interface UsersService {
    List<Pair> getUsers();
    String getUser(String username);
}
