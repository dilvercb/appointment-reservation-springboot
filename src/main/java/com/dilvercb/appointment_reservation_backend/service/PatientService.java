package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.dto.PatientResponse;

import java.util.List;

public interface PatientService {
    List<PatientResponse> findAll();
}
