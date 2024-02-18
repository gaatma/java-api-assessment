package com.cbfacademy.apiassessment.file;

import java.util.Objects;

public class FileModel {

    private String id;
    private String fileName;
    private String filePath;
    private String timeStamp;
    private String fileSize;
    private String fileType;
    private FileUser user;


    // Constructor with parameters
    public FileModel(String id, String fileName, String filePath, String timeStamp, String fileSize, String fileType,
            FileUser user) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.timeStamp = timeStamp;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.user = user;
    }




    // Default constructor
    public FileModel() {
    }




    // Getter and setter methods for each field
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getFileSize() {
        return fileSize;
    }
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public FileUser getUser() {
        return user;
    }
    public void setUser(FileUser user) {
        this.user = user;
    }




    // toString method to represent the object as a string
    @Override
    public String toString() {
        return "FileModel [id=" + id + ", fileName=" + fileName + ", filePath=" + filePath + ", timeStamp=" + timeStamp
                + ", fileSize=" + fileSize + ", fileType=" + fileType + ", user=" + user + "]";
    }

    // equals and hashCode methods for comparing objects based on their attributes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileModel fileModel = (FileModel) o;
        return Objects.equals(id, fileModel.id) && Objects.equals(fileName, fileModel.fileName) && Objects.equals(filePath, fileModel.filePath) && Objects.equals(timeStamp, fileModel.timeStamp) && Objects.equals(fileSize, fileModel.fileSize) && Objects.equals(fileType, fileModel.fileType) && Objects.equals(user, fileModel.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, filePath, timeStamp, fileSize, fileType, user);
    }
}
