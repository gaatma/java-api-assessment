package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.file.FileBaseResponse;
import com.cbfacademy.apiassessment.file.FileModel;
import com.cbfacademy.apiassessment.file.FileService;
import com.cbfacademy.apiassessment.file.FileUser;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FileServiceTest {

    @Autowired
    FileService fileService;

    private FileModel testFile;
    private FileModel testFileUpdateNotFound;
    private MultipartFile mockFile;
    private MultipartFile imageMockFile;
    private String uploadedFileId;

    // Set up the test environment
    @BeforeEach
    public void setUp() throws Exception {
        // Create a mock file for testing
        mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "This is the content of the mock file".getBytes()
        );

        // Create a mock image file for testing
        imageMockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "image/jpeg",
                "This is the content of the mock file".getBytes()
        );

        // Upload a mock image file to use in tests
        ResponseEntity<FileBaseResponse> response = fileService.processUploadedFile(imageMockFile, "Gifty");
        uploadedFileId = ((FileModel) response.getBody().getData()).getId();

        // Create test file models
        testFile = new FileModel(uploadedFileId, "testFile.txt", "/uploads/file/testfile.txt", Timestamp.from(Instant.now()).toString(), "234343", "text/plain", new FileUser(UUID.randomUUID().toString(), "Gifty"));
        testFileUpdateNotFound =  new FileModel("UD", "testFile.txt", "/uploads/file/testfile.txt", Timestamp.from(Instant.now()).toString(), "234343", "text/plain", new FileUser(UUID.randomUUID().toString(), "Gifty"));
    }

    // Test case: Process uploaded file - Expected success response
    @Test
    @Description("processUploadedFile method returns a 200 response")
    public void processUploadFile_ExpectedSuccessResponse() {
        ResponseEntity<FileBaseResponse> response = fileService.processUploadedFile(imageMockFile, "Gifty");
        assertEquals(200, response.getStatusCode().value());
    }

    // Test case: Process uploaded file - Expected error response for invalid file
    @Test
    @Description("processUploadedFile method returns a 500 response for invalid file")
    public void processUploadFile_ExpectedErrorResponse() {
        ResponseEntity<FileBaseResponse> response = fileService.processUploadedFile(mockFile, "Gifty");
        assertEquals(500, response.getStatusCode().value());
    }

    // Test case: Retrieve all uploaded files - Expected success response
    @Test
    @Description("getAllUploadedFiles expecting a 200 response for success")
    public void getAllUploadedFiles_ExpectedSuccessResponse() {
        ResponseEntity<FileBaseResponse> response = fileService.getAllUploadedFiles();
        assertEquals(200, response.getStatusCode().value());
    }

    // Test case: Get uploaded file by ID - Expected success response
    @Test
    @Description("getUploadedFileById expecting a 200 response for success")
    public void getUploadedFileById_ExpectedSuccessResponse() {
        ResponseEntity<FileBaseResponse> response = fileService.getUploadedFileById(uploadedFileId);
        assertEquals(200, response.getStatusCode().value());
    }

    // Test case: Get uploaded file by ID - Expected error response for invalid file ID
    @Test
    @Description("getUploadedFileById method returns a 500 response for invalid file")
    public void getUploadedFileById_ExpectedErrorResponse() {
        ResponseEntity<FileBaseResponse> response = fileService.getUploadedFileById("AA");
        assertEquals(500, response.getStatusCode().value());
    }

    // Test case: Update uploaded file - Expected success response
    @Test
    @Description("updateUploadedFile expecting a 200 response for success")
    public void updateUploadedFile_ExpectedSuccessResponse() {
        ResponseEntity<FileBaseResponse> response = fileService.updateUploadedFile(testFile);
        assertEquals(200, response.getStatusCode().value());
    }

    // Test case: Update uploaded file - Expected error response for invalid file
    @Test
    @Description("updateUploadedFile method returns a 500 response for invalid file")
    public void updateUploadedFile_ExpectedErrorResponse() {
        ResponseEntity<FileBaseResponse> response = fileService.updateUploadedFile(testFileUpdateNotFound);
        assertEquals(500, response.getStatusCode().value());
    }

    // Test case: Delete uploaded file by ID - Expected success response
    @Test
    @Description("deleteUploadedFile expecting a 200 response for success")
    public void deleteUploadedFile_ExpectedSuccessResponse() {
        ResponseEntity<FileBaseResponse> response = fileService.deleteUploadedFile(uploadedFileId);
        assertEquals(200, response.getStatusCode().value());
    }

    // Test case: Delete uploaded file by ID - Expected error response for invalid file ID
    @Test
    @Description("deleteUploadedFile method returns a 500 response for invalid file")
    public void deleteUploadedFile_ExpectedErrorResponse() {
        ResponseEntity<FileBaseResponse> response = fileService.deleteUploadedFile("AA");
        assertEquals(500, response.getStatusCode().value());
    }
}
