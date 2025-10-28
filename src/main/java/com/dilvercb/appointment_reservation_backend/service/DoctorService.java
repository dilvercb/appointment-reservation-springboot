package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.dto.DoctorResponse;

import java.util.List;

public interface DoctorService {
    List<DoctorResponse> findBySpecialty(Long idSpecialty);
    List<DoctorResponse> findAll();
}
