package com.aem.community.core.models;

public class Entry {
    private String id;
    private String title;
    private String name;
    private String authorUri;
    private String published;
    private String updated;

    public Entry() {
    }

    public Entry(String id, String title, String authorUri, String published, String updated) {
        this.id = id;
        this.title = title;
        this.authorUri = authorUri;
        this.published = published;
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorUri() {
        return authorUri;
    }

    public void setAuthorUri(String authorUri) {
        this.authorUri = authorUri;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", authorUri='" + authorUri + '\'' +
                ", published='" + published + '\'' +
                ", updated='" + updated + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
