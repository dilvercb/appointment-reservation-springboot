package com.dilvercb.appointment_reservation_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleRequest {
    @NotNull(message = "DayOfWeek is required")
    private DayOfWeek dayOfWeek;
    @NotNull(message = "StartTime is required")
    private LocalTime startTime;
    @NotNull(message = "EndTime is required")
    private LocalTime endTime;
}
