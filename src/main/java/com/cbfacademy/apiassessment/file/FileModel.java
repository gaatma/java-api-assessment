package com.cbfacademy.apiassessment.file;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FileModel {

    // Unique identifier for the file
    private String id;

    // Name of the file
    private String fileName;

    // Path where the file is stored
    private String filePath;

    // Timestamp indicating when the file was uploaded
    private String timeStamp;

    // Size of the file
    private String fileSize;

    // Type of the file
    private String fileType;

    // User who uploaded the file
    private FileUser user;

    // Constructors
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

    public FileModel() {
    }
}
