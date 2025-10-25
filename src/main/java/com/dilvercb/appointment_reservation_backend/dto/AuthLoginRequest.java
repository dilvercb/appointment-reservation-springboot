package com.dilvercb.appointment_reservation_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
        @NotBlank
        @Email(message = "El correo debe tener un formato válido")
        String username,
        @NotBlank
        String password) {
}
