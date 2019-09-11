package com.aem.community.core.models;

import com.aem.community.core.listeners.SimpleResourceListener;

public class Comment {
    private String id;
    private String name;
    private String email;
    private String body;

    public Comment() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }

    public static class Builder {
        private String id;
        private String name;
        private String email;
        private String body;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Comment build(){
            Comment comment = new Comment();

            comment.id = this.id;
            comment.name = this.name;
            comment.email = this.email;
            comment.body = this.body;

            return comment;
        }
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
