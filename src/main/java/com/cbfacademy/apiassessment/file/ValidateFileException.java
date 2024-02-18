package com.cbfacademy.apiassessment.file;

// Custom exception class to handle file validation errors
public class ValidateFileException extends Exception {

    // Constructor to initialize the exception with a custom error message
    public ValidateFileException(String message) {
        super(message);
    }
}
