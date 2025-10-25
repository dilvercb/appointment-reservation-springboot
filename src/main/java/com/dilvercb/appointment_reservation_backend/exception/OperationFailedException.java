package com.dilvercb.appointment_reservation_backend.exception;

public class OperationFailedException extends RuntimeException{
    public OperationFailedException(String message) {
        super(message);
    }
}
