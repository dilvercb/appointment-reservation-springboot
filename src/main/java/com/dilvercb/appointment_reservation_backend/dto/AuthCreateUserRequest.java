package com.dilvercb.appointment_reservation_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AuthCreateUserRequest(
        @NotBlank
        String name,
        @NotBlank
        String lastName,
        @NotBlank
        @Email(message = "El correo debe tener un formato válido")
        String username,
        @NotBlank
        String password,
        LocalDate dateOfBirth,
        String medicalHistory,
        String phone
) {
}
