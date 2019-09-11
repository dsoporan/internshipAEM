package com.aem.community.core.models;

public class Pair {
    private String username;
    private String email;

    public Pair(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Pair setUsername(String username) {
        this.username = username;
        return this;
    }

    public Pair setEmail(String email) {
        this.email = email;
        return this;
    }
}
