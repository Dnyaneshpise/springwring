package com.learn.demo.exception;

import com.learn.demo.exception.DuplicateUserException;
import com.learn.demo.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice       // = @ControllerAdvice + @ResponseBody (same pattern as RestController!)
public class GlobalExceptionHandler {

    // Handles validation failures (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    fieldErrors.put(fieldName, errorMessage);
                });

        ErrorResponse response = new ErrorResponse(
                400,
                "Validation failed",
                LocalDateTime.now(),
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Handles user not found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundException ex) {

        ErrorResponse response = new ErrorResponse(
                404,
                ex.getMessage(),
                LocalDateTime.now(),
                null            // no field-level errors for this case
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Handles duplicate user
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUser(
            DuplicateUserException ex) {

        ErrorResponse response = new ErrorResponse(
                409,
                ex.getMessage(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Catch-all — handles anything unexpected
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        ErrorResponse response = new ErrorResponse(
                500,
                "Something went wrong. Please try again later.",
                //  ↑ deliberately vague — don't expose internals to client
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}