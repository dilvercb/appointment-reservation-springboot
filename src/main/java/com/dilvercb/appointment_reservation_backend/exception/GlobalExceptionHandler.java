package com.dilvercb.appointment_reservation_backend.exception;

import com.dilvercb.appointment_reservation_backend.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(OperationFailedException.class)
    public ResponseEntity<ErrorMessage> handleOperationFailedException(OperationFailedException exception){
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }
}
