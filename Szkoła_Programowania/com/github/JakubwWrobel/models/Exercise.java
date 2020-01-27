package com.github.JakubwWrobel.models;

public class Exercise {
    private int id;
    private String title;
    private String description;

    public Exercise(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public Exercise(){};




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
