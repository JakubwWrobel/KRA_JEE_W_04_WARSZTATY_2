package com.github.JakubwWrobel.models;

public class Exercise {
    private int id;
    private String title;
    private String description;

    public Exercise(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }




    //SETTER
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //GETTER
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
