package com.Blood.Inventory_Service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAlreadyPresent.class)
    public ResponseEntity<String> handleDataAlreadyPresentException(DataAlreadyPresent ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }
//    @ExceptionHandler(IDNotFoundException.class)
//    public ResponseEntity<String> handleIDNotFoundException(Exception ex) {
//    	 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//    }
//    @ExceptionHandler(DonorNotFoundException.class)
//    public ResponseEntity<String> handledonorNotFoundException(Exception ex) {
//    	 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//    }
    
}

