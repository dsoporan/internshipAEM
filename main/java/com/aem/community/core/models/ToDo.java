package com.aem.community.core.models;

public class ToDo {
    private String id;
    private String title;
    private String completed;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", completed='" + completed + '\'' +
                '}';
    }

    public static class Builder{
        private String id;
        private String title;
        private String completed;


        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setCompleted(String completed) {
            this.completed = completed;
            return this;
        }

        public ToDo build(){
            ToDo toDo = new ToDo();

            toDo.id = this.id;
            toDo.title = this.title;
            toDo.completed = this.completed;

            return toDo;
        }
    }
}
