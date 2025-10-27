package com.dilvercb.appointment_reservation_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
        @NotBlank(message = "Username is required")
        @Email(message = "Invalid email format")
        String username,
        @NotBlank(message = "Password is required")
        String password) {
        public AuthLoginRequest {
                if (username != null) {
                        username = username.trim();
                }
                if (password != null) {
                        password = password.trim();
                }
        }
}
