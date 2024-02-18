package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.file.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FileUtilTest {

    @Autowired
    private FileUtil fileUtil;

    private MultipartFile mockFile;

    private MultipartFile imageMockFile;

    @BeforeEach
    public void setUp() throws Exception {
        mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "This is the content of the mock file".getBytes()

        );

        imageMockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "image/jpeg",
                "This is the content of the mock file".getBytes()

        );

    }

    @Test
    @Description("validateFile method throws a ValidateFileException")
    public void validateFile_ThrowsValidateFileException() {
        // Use assertThrows to check if the method throws the expected exception
        assertThrows(ValidateFileException.class, () -> {
            fileUtil.validateFile(mockFile);
        });
    }

    @Test
    @Description("validateFile method passes validation for an image file")
    public void validateFile_PassesImageValidation() {
        // Use assertDoesNotThrow to check if the method passes validation for an image file
        assertDoesNotThrow(() -> {
            fileUtil.validateFile(imageMockFile);
        });
    }

    @Test
    @Description("generateFileId method generates a non-null UUID")
    public void generateFileId_NotNull() {
        // Generate a file ID and check if it's not null
        String fileId = fileUtil.generateFileId();
        assertNotNull(fileId);
    }

    @Test
    @Description("fileTimeStamp method returns a non-null timestamp")
    public void fileTimeStamp_NotNull() {
        // Generate a timestamp and check if it's not null
        String timeStamp = fileUtil.fileTimeStamp();
        assertNotNull(timeStamp);
    }

    @Test
    @Description("saveFileInfoToJsonFile method saves file info to JSON file correctly")
    public void saveFileInfoToJsonFile_SavesFileInfoCorrectly() throws IOException {
        // Create a list of file info
        List<FileModel> fileInfoList = new ArrayList<>();
        fileInfoList.add(new FileModel("1", "file1.txt", "file1.txt", "2024-02-17 12:00:00", "1 KB", "text/plain", null));
        fileInfoList.add(new FileModel("2", "file2.jpg", "file2.jpg", "2024-02-17 13:00:00", "2 KB", "image/jpeg", null));

        // Save file info to JSON file
        fileUtil.saveFileInfoToJsonFile(fileInfoList);

        // Read the content of the JSON file
        File file = new File(fileUtil.JSON_FILE);
        assertTrue(file.exists());

        ObjectMapper objectMapper = new ObjectMapper();
        List<FileModel> savedFileInfoList = objectMapper.readValue(file, new TypeReference<List<FileModel>>() {});

        // Verify that the saved JSON file contains the expected data
        assertEquals(fileInfoList.size(), savedFileInfoList.size());
        assertTrue(savedFileInfoList.containsAll(fileInfoList));

        // Clean up: Delete the generated JSON file after the test
        file.delete();
    }

    @Test
    @Description("readFileInfoFromJsonFile method reads file info from JSON file correctly")
    public void readFileInfoFromJsonFile_ReadsFileInfoCorrectly() {
        // Create a list of file info
        List<FileModel> fileInfoList = new ArrayList<>();
        fileInfoList.add(new FileModel("1", "file1.txt", "file1.txt", "2024-02-17 12:00:00", "1 KB", "text/plain", null));
        fileInfoList.add(new FileModel("2", "file2.jpg", "file2.jpg", "2024-02-17 13:00:00", "2 KB", "image/jpeg", null));

        // Save file info to JSON file
        fileUtil.saveFileInfoToJsonFile(fileInfoList);

        // Read file info from JSON file
        List<FileModel> readFileInfoList = fileUtil.readFileInfoFromJsonFile();

        // Verify that the file info is read correctly from the JSON file
        assertNotNull(readFileInfoList);
        assertEquals(fileInfoList.size(), readFileInfoList.size());
        assertTrue(readFileInfoList.containsAll(fileInfoList));
    }
}