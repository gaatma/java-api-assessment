package com.cbfacademy.apiassessment.file;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {


    // Directory where files will be uploaded
    private static final String RESOURCES_DIRECTORY = "src/main/resources";
    private static final String UPLOADS_DIRECTORY = "uploads";
    public final String UPLOAD_DIRECTORY;


    // File utility class for file operations
    public final FileUtil fileUtil;

    // Constructor with dependency injection for FileUtil
    public FileService(FileUtil fileUtil) throws IOException {

        this.fileUtil = fileUtil;

        UPLOAD_DIRECTORY = RESOURCES_DIRECTORY + "/" + UPLOADS_DIRECTORY;
    }

        // Method to process uploaded file
    public ResponseEntity<FileBaseResponse> processUploadedFile(MultipartFile file, String userInfo) {
        FileBaseResponse fileBaseResponse = new FileBaseResponse();
        HttpStatus httpStatus;



        try {
            // Validate the uploaded file
            fileUtil.validateFile(file);

            // Save the file to the local disk
            String fileName = file.getOriginalFilename();
            String filePath = fileUtil.saveFileToLocalDisk(file, UPLOAD_DIRECTORY);

            // Create file metadata
            FileModel fileInfo = new FileModel();
            fileInfo.setFileName(fileName);
            fileInfo.setFilePath(filePath);
            fileInfo.setTimeStamp(LocalDateTime.now().toString());
            fileInfo.setUser(new FileUser(UUID.randomUUID().toString(), userInfo));
            fileInfo.setId(UUID.randomUUID().toString());
            fileInfo.setFileSize(String.valueOf(file.getSize()));
            fileInfo.setFileType(file.getContentType());

            // Append file metadata to JSON file
            List<FileModel> existingFiles = fileUtil.readFileInfoFromJsonFile();
            existingFiles.add(fileInfo);
            fileUtil.saveFileInfoToJsonFile(existingFiles);

            // Prepare success response
            fileBaseResponse.setStatus(HttpStatus.OK.value());
            fileBaseResponse.setData(fileInfo);
            fileBaseResponse.setMessage("File uploaded successfully");
            httpStatus = HttpStatus.OK;
        } catch (IOException e) {
            // Handle file processing errors
            fileBaseResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            fileBaseResponse.setData(e.getMessage());
            fileBaseResponse.setMessage("File uploaded failed");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        catch (ValidateFileException e) {
            // Handle file validation errors
            fileBaseResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            fileBaseResponse.setData(e.getMessage());
            fileBaseResponse.setMessage("Not a valid file");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        }

        return new ResponseEntity<>(fileBaseResponse, httpStatus);
    }



    // Method to retrieve all uploaded files
    public ResponseEntity<FileBaseResponse> getAllUploadedFiles() {
        // Create a new instance of FileBaseResponse to store the response data
        FileBaseResponse response = new FileBaseResponse();

        // Declare an HttpStatus variable to store the HTTP status code
        HttpStatus httpStatus;
        try {
            // Attempt to read the file information from the JSON file using the fileUtil
            List<FileModel> uploadedFiles = fileUtil.readFileInfoFromJsonFile();

            // Sort the list in descending order based on timestamp
            Collections.sort(uploadedFiles, Comparator.comparing(FileModel::getTimeStamp).reversed());


            // If successful, populate the response with the retrieved files
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("All uploaded files retrieved successfully");
            response.setData(uploadedFiles);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            // If an exception occurs during file retrieval, handle it
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("All uploaded files retrieval failed");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // Return a ResponseEntity containing the response object and HTTP status
        return new ResponseEntity<>(response, httpStatus);
    }


    // Method to retrieve uploaded file by ID
    public ResponseEntity<FileBaseResponse> getUploadedFileById(String id) {
        // Create a new instance of FileBaseResponse to store the response data
        FileBaseResponse response = new FileBaseResponse();

        // Declare an HttpStatus variable to store the HTTP status code
        HttpStatus httpStatus;
        try {
            // Attempt to retrieve the uploaded file by ID using the fileUtil
            FileModel file = fileUtil.getUploadedFileById(id);

            // If successful, populate the response with the retrieved file
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Uploaded file retrieved successfully");
            response.setData(file);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            // If an exception occurs during file retrieval, handle it
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Failed to retrieve uploaded file");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            System.out.println(e.getMessage());
        }

        // Return a ResponseEntity containing the response object and HTTP status
        return new ResponseEntity<>(response, httpStatus);
    }

    // Method to update uploaded file
    public ResponseEntity<FileBaseResponse> updateUploadedFile(FileModel updatedFile) {
        // Create a new instance of FileBaseResponse to store the response data
        FileBaseResponse response = new FileBaseResponse();

        // Declare an HttpStatus variable to store the HTTP status code
        HttpStatus httpStatus;
        try {
            // Attempt to update the uploaded file using the fileUtil
            FileModel updatedFileInfo = fileUtil.updateUploadedFile(updatedFile);

            // If successful, populate the response with the updated file information
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Uploaded file updated successfully");
            response.setData(updatedFileInfo);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            // If an exception occurs during file update, handle it
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Failed to update uploaded file");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // Return a ResponseEntity containing the response object and HTTP status
        return new ResponseEntity<>(response, httpStatus);
    }

    // Method to delete uploaded file by ID
    public ResponseEntity<FileBaseResponse> deleteUploadedFile(String id) {
        // Create a new instance of FileBaseResponse to store the response data
        FileBaseResponse response = new FileBaseResponse();

        // Declare an HttpStatus variable to store the HTTP status code
        HttpStatus httpStatus;
        try {
            // Attempt to delete the uploaded file by ID using the fileUtil
            fileUtil.deleteUploadedFile(id);

            // If successful, set success response
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Uploaded file deleted successfully");
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            // If an exception occurs during file deletion, handle it
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Failed to delete uploaded file");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // Return a ResponseEntity containing the response object and HTTP status
        return new ResponseEntity<>(response, httpStatus);
    }

}
