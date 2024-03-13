package com.tiia.buy_01.controller;

import com.tiia.buy_01.exceptions.DataNotValidatedException;
import com.tiia.buy_01.exceptions.ExistingEmailException;
import com.tiia.buy_01.exceptions.InstanceUndefinedException;
import com.tiia.buy_01.exceptions.InvalidUserException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
public class ErrorsController {

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<String> handleInvalidUserException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user has not been found!");
    }

    @ExceptionHandler(DataNotValidatedException.class)
    public ResponseEntity<String> handleDataNotValidatedException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data not validated.");
    }

    @ExceptionHandler(InstanceUndefinedException.class)
    public ResponseEntity<String> handleInstanceUndefinedException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The instance is undefined!");
    }

    @ExceptionHandler(ExistingEmailException.class)
    public ResponseEntity<String> handleExistingEmailException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email exists already!");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public  ResponseEntity<String> handleMaxUploadExceededException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Maximum upload size exceeded");
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public  ResponseEntity<String> handleMissingServletRequestPartException(HttpServletRequest request, Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errors: " + exception.getMessage());
    }

}
