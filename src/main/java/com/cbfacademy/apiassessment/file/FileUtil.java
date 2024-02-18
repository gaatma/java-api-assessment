package com.cbfacademy.apiassessment.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FileUtil {

    public final String JSON_FILE = "uploadfiles\\uploaded_files.json";

        public String generateFileId() {
        // Generate a random UUID
        UUID randomUUID = UUID.randomUUID();
        // Convert the UUID to a string
        String generatedID = randomUUID.toString();

        return generatedID;

    }



    public String fileTimeStamp(){

        // Time stamp
        LocalDateTime localDateTime = LocalDateTime.now();

        // DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format the LocalDateTime using the specified formatter
        String formattedTimestamp = localDateTime.format(formatter);
        
        return formattedTimestamp;
    }

     public void validateFile(MultipartFile file) throws ValidateFileException {
        // Implement file validation logic (e.g., file type, size, etc.)
        if (file.isEmpty() || !file.getContentType().startsWith("image")) {

                throw new ValidateFileException("Invalid file. Please upload an image.");

        }
    }

    
    public String saveFileToLocalDisk(MultipartFile file, String uploadsDir) throws IOException {
        Path filePath = Path.of(uploadsDir, file.getOriginalFilename());
        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }

    public void saveFileInfoToJsonFile(List<FileModel> fileInfoList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(JSON_FILE), fileInfoList);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file info to JSON file.", e);
        }
    }

    public List<FileModel> readFileInfoFromJsonFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(JSON_FILE);
    
            if (!file.exists()) {
                return new ArrayList<>();
            }
    
            return objectMapper.readValue(file, new TypeReference<List<FileModel>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file info from JSON file.", e);
        }
    }


    // Update uploaded file information
    public FileModel updateUploadedFile(FileModel updatedFile) {
        List<FileModel> fileModels = readFileInfoFromJsonFile();
        for (int i = 0; i < fileModels.size(); i++) {
            if (fileModels.get(i).getId().equals(updatedFile.getId())) {
                fileModels.set(i, updatedFile);
                saveFileInfoToJsonFile(fileModels);
                return updatedFile;
            }
        }
        throw new RuntimeException("Uploaded file with id " + updatedFile.getId() + " not found.");
    }


    // Delete uploaded file by ID
    public void deleteUploadedFile(String id) {
        List<FileModel> fileModels = readFileInfoFromJsonFile();
        for (int i = 0; i < fileModels.size(); i++) {
            if (fileModels.get(i).getId().equals(id)) {
                fileModels.remove(i);
                saveFileInfoToJsonFile(fileModels);
                return;
            }
        }
        throw new RuntimeException("Uploaded file with id " + id + " not found.");
    }

    // Get uploaded file by ID
    public FileModel getUploadedFileById(String id) {
        List<FileModel> fileModels = readFileInfoFromJsonFile();
        for (FileModel fileModel : fileModels) {
            if (fileModel.getId().equals(id)) {
                return fileModel;
            }
        }
        throw new RuntimeException("Uploaded file with id " + id + " not found.");
    }
}
