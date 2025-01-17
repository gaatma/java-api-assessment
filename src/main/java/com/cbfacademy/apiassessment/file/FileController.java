package com.cbfacademy.apiassessment.file;

import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    // Constructor injection for FileService
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // Upload Endpoint
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileBaseResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("uploaderName") String uploaderName
    ) {
        // Delegate processing to the service layer
        return fileService.processUploadedFile(file, uploaderName);
    }

    // Retrieve all uploaded files endpoint
    @GetMapping("/all")
    public ResponseEntity<FileBaseResponse> getAllUploadedFiles() {
        return fileService.getAllUploadedFiles();
    }

    // Get uploaded file by ID endpoint
    @GetMapping("/{id}")
    public ResponseEntity<FileBaseResponse> getUploadedFileById(@PathVariable String id) {
        return fileService.getUploadedFileById(id);
    }

    // Update uploaded file endpoint
    @PutMapping("/update")
    public ResponseEntity<FileBaseResponse> updateUploadedFile(@RequestBody FileModel updatedFile) {
        return fileService.updateUploadedFile(updatedFile);
    }

    // Delete uploaded file by ID endpoint
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FileBaseResponse> deleteUploadedFile(@PathVariable String id) {
        return fileService.deleteUploadedFile(id);
    }
}
