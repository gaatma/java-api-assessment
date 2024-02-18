package com.cbfacademy.apiassessment.file;

// Importing Lombok annotations for getter and setter methods
import lombok.Getter;
import lombok.Setter;

// Annotation to generate getter and setter methods automatically
@Getter
@Setter
public class FileUser {

    // Fields to store user ID and username
    private String id;
    private String userName;

    // Constructor with parameters for user ID and username
    public FileUser(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    // Default constructor
    public FileUser() {
    }

    // Override toString() method to provide a string representation of the object
    @Override
    public String toString() {
        return "FileUser [id=" + id + ", userName=" + userName + "]";
    }
}
