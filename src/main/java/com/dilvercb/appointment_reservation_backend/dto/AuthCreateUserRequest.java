package com.dilvercb.appointment_reservation_backend.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AuthCreateUserRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "LastName is required")
        String lastName,
        @NotBlank(message = "Username is required")
        @Email(message = "Email must have a valid format")
        String username,
        @NotBlank(message = "Password is required")
        String password,
        @NotNull(message = "DateOfBirth is required")
        @Past(message = "DateOfBirth must be in the past")
        LocalDate dateOfBirth,
        String medicalHistory,
        @NotBlank(message = "Phone is required")
        @Size(max = 9, message = "Phone must have at most 9 characters")
        String phone
) {
        public AuthCreateUserRequest {
                if (name != null) name = name.trim();
                if (lastName != null) lastName = lastName.trim();
                if (username != null) username = username.trim();
                if (password != null) password = password.trim();
                if (phone != null) phone = phone.trim();
                if (medicalHistory != null) medicalHistory = medicalHistory.trim();
        }
}
