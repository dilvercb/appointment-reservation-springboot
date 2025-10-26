package com.dilvercb.appointment_reservation_backend.exception;

public class ElementNotFoundException extends RuntimeException{
    public ElementNotFoundException(String message) {
        super(message);
    }
}
