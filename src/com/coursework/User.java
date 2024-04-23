package com.coursework;

public class User {
    private String username;    //User's username
    private String password;    //User's password

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Default Constructor
    public User() {
    }

    //Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    //Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "(User)" +
                "\n Username: " + getUsername()+
                "\n Password: " + getPassword();
    }
}
