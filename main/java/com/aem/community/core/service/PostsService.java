package com.aem.community.core.service;

import com.aem.community.core.models.Post;

import java.util.List;

public interface PostsService {
    List<Post> getPostsfromJson(String jsonString);
}
