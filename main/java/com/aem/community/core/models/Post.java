package com.aem.community.core.models;

public class Post {
    private String username;
    private String usernameId;
    private String id;
    private String title;
    private String body;
    public Post(){}

    public String getUsername() {
        return username;
    }

    public String getUsernameId() {
        return usernameId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public static class Builder {
        private String username;
        private String usernameId;
        private String id;
        private String title;
        private String body;

        public Builder() {
        }

        public String getUsername() {
            return username;
        }

        public Builder setUsername(String username) {
            this.username = username;

            return this;
        }

        public String getId() {
            return id;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getBody() {
            return body;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public String getUsernameId() {
            return usernameId;
        }

        public Builder setUsernameId(String usernameId) {
            this.usernameId = usernameId;
            return this;
        }

        public Post build(){
            Post post = new Post();
            post.username = this.username;
            post.usernameId = this.usernameId;
            post.id = this.id;
            post.title = this.title;
            post.body = this.body;

            return post;
        }

        @Override
        public String toString() {
            return "Post{" +
                    "username='" + username + '\'' +
                    ", usernameId='" + usernameId + '\'' +
                    ", id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", body='" + body + '\'' +
                    '}';
        }
    }
}
