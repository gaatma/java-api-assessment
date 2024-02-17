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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @Description("validateFile method returns a 200 response")
    public void validateFile_ExpectedSuccessResponse() {
        ResponseEntity<FileBaseResponse> response = fileUtil.validateFile(imageMockFile, "Gifty");

        assertEquals(200, response.getStatusCode().value());

    }


}
