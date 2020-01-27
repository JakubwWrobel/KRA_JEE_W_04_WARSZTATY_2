package com.github.JakubwWrobel.models;

import java.util.Date;

public class Solution {
    private int id;
    private Date created;
    private Date updated;
    private String description;
    private Exercise exercise_id;
    private User users_id;

    public Solution(Date created, Date updated, String description) {
        this.created = created;
        this.updated = updated;
        this.description = description;
    }
    public Solution(){};

    //GETTER

    public int getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getDescription() {
        return description;
    }

    public Exercise getExercise_id() {
        return exercise_id;
    }

    public User getUsers_id() {
        return users_id;
    }

    //SETTER

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExercise_id(Exercise exercise_id) {
        this.exercise_id = exercise_id;
    }

    public void setUsers_id(User users_id) {
        this.users_id = users_id;
    }
}
