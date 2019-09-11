package com.aem.community.core.service;

import com.aem.community.core.models.Comment;

import java.util.List;

public interface CommentsService {
    List<Comment> getCommentsFromJson(String jsonString, String postId);
}
