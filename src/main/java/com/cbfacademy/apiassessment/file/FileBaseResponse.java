package com.cbfacademy.apiassessment.file;

// Lombok annotations to generate getters, setters, equals, hashCode, and toString methods
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a generic response structure for file-related operations.
 * Contains status code, message, and optional data payload.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileBaseResponse {

    /** The status code of the response. */
    private int status;

    /** The message associated with the response. */
    private String message;

    /** The optional data payload associated with the response. */
    private Object data;
}
