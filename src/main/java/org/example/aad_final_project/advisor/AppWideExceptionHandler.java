package org.example.aad_final_project.advisor;

import io.jsonwebtoken.io.IOException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.example.aad_final_project.dto.ResponseDTO;
import org.example.aad_final_project.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;


@RestControllerAdvice
@CrossOrigin
public class AppWideExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessage.append(violation.getPropertyPath()).append(": ")
                    .append(violation.getMessage()).append("; ");
        }

        return ResponseEntity.badRequest()
                .body(new ResponseDTO(400, "Validation Error: " + errorMessage.toString(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.append(error.getField()).append(": ")
                    .append(error.getDefaultMessage()).append("; ");
        });

        return ResponseEntity.badRequest()
                .body(new ResponseDTO(400, "Validation Error: " + errorMessage.toString(), null));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseDTO> handleIOException(IOException ex) {
        return ResponseEntity.status(500)
                .body(new ResponseDTO(500, "File Handling Error: " + ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleOtherExceptions(Exception ex) {
        return ResponseEntity.status(500)
                .body(new ResponseDTO(500, "Unexpected Error: " + ex.getMessage(), null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Unexpected Error: " + ex.getMessage()));
    }
}
