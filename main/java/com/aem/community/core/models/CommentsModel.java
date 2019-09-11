package com.aem.community.core.models;

import com.adobe.cq.sightly.WCMUse;
import com.aem.community.core.constants.MyConstants;
import com.aem.community.core.service.CommentsService;
import com.aem.community.core.service.CommentsServiceImpl;
import com.aem.community.core.utils.RetrieveJSON;

import java.util.List;

import static com.aem.community.core.constants.MyConstants.URL_COMMENTS;

public class CommentsModel extends WCMUse {

    private List<Comment> commentsList;

    @Override
    public void activate() throws Exception {
        String postId = getRequest().getParameter(MyConstants.POST_ID);
        RetrieveJSON retrieveJSON = new RetrieveJSON(URL_COMMENTS);
        CommentsService commentsService = new CommentsServiceImpl();
        String content = retrieveJSON.getJsonFromUrl();
        commentsList = commentsService.getCommentsFromJson(content, postId);
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }
}
