package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.file.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FileUtilTest {

    @Autowired
    FileUtil fileUtil;

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


}
