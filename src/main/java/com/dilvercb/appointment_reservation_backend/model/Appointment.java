package com.dilvercb.appointment_reservation_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String reason;
    private String status;
    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;
}
