package com.dilvercb.appointment_reservation_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponse {
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private LocalDate dateOfBirth;
    private String medicalHistory;
    private String phone;
}
