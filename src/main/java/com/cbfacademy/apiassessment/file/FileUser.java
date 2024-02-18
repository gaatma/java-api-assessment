package com.cbfacademy.apiassessment.file;

import java.util.Objects;

public class FileUser{

    private String id;
    private String userName;


    // Constructor with parameters
    public FileUser(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }


    // Default constructor
    public FileUser() {
    }


    // Getter and setter methods for each field
    public String getId() {

        return id;
    }


    public String getUserName() {
        return userName;
    }


    public void setId(String id) {

        this.id = id;
    }


    public void setUserName(String userName) {

        this.userName = userName;
    }


    // toString method to represent the object as a string
    @Override
    public String toString()
    {
        return "FileUser [id=" + id + ", userName=" + userName + "]";
    }


    // equals and hashCode methods for comparing objects based on their attributes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileUser fileUser = (FileUser) o;
        return Objects.equals(id, fileUser.id) && Objects.equals(userName, fileUser.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName);
    }
}
