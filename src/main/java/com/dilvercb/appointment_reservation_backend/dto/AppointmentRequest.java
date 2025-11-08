package com.dilvercb.appointment_reservation_backend.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class AppointmentRequest {
    @NotNull(message = "Date is required")
    @Future(message = "The date must be in the future")
    private LocalDate date;
    @NotNull(message = "Time is required")
    private LocalTime time;
    private String reason;
    @NotNull(message = "IdDoctor is required")
    @Positive(message = "IdDoctor must be positive")
    private Long idDoctor;
}
