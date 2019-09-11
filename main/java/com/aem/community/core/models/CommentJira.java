package com.aem.community.core.models;

public class CommentJira {
    private String comment;
    private String datetime;
    private String user;

    public CommentJira() {
    }

    public String getComment() {
        return comment;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getUser() {
        return user;
    }

    public static class Builder{
        private String comment;
        private String datetime;
        private String user;


        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setDatetime(String datetime) {
            this.datetime = datetime;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public CommentJira build(){
            CommentJira commentJira = new CommentJira();

            commentJira.comment = this.comment;
            commentJira.datetime = this.datetime;
            commentJira.user = this.user;

            return commentJira;
        }
    }
}
