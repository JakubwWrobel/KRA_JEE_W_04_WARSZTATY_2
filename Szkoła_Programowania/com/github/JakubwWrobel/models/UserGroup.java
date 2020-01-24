package com.github.JakubwWrobel.models;

public class UserGroup {
    private int id;
    private String userGroupName;

    public UserGroup(String name){
        this.userGroupName = name;
    }
    public UserGroup(){};

    //GETTER
    public String getUserGroupName() {
        return userGroupName;
    }
    public int getUserGroupId() {
        return id;
    }
    //SETTER
    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }
    public void setId(int id) {
        this.id = id;
    }
}
