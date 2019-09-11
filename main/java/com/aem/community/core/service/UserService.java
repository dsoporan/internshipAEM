package com.aem.community.core.service;

import com.aem.community.core.models.Post;
import com.aem.community.core.models.ToDo;
import com.aem.community.core.models.User;

import java.util.List;

public interface UserService {
    User getUserfromJson(String jsonString);
    List<ToDo> getTodosfromJson(String jsonString);
}
