package com.dilvercb.appointment_reservation_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponse {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String specialtyName;
    private String doctorName;
    private String patientName;
}
