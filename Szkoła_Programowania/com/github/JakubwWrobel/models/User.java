package com.github.JakubwWrobel.models;

import com.github.JakubwWrobel.dao.UserGroupDAO;
import jbcrypt.BCrypt;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private UserGroup userGroup;
    private int is_admin;


    public User(String username, String email, String password) {

        this.username = username;
        this.email = email;
        setPassword(password);
    }

    public User(String username, String email, String password, UserGroup userGroup) {
        this.username = username;
        this.email = email;
        setPassword(password);
        this.userGroup = userGroup;
    }

    public User() {
    }

    public boolean checkPassword(String plain) {
        return BCrypt.checkpw(plain, this.password);
    }


    //GETTER
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserGroup getUserGroup() {
        return this.userGroup;
    }

    public int getIs_admin() {
        return is_admin;
    }

    //SETTER
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }
}
