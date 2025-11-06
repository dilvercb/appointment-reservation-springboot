package com.dilvercb.appointment_reservation_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorRequest {
    @NotBlank(message = "LicenseNumber is required")
    private String licenseNumber;
    @NotNull(message = "IdSpecialty is required")
    @Positive(message = "IdSpecialty must be a positive number")
    private Long idSpecialty;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "LastName is required")
    private String lastName;
    @NotBlank(message = "Username is required")
    @Email(message = "Email must have a valid format")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
}
