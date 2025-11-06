package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.dto.DoctorRequest;
import com.dilvercb.appointment_reservation_backend.dto.DoctorResponse;
import com.dilvercb.appointment_reservation_backend.model.Doctor;

import java.util.List;

public interface DoctorService {
    List<DoctorResponse> findBySpecialty(Long idSpecialty);
    List<DoctorResponse> findAll();
    DoctorResponse save(DoctorRequest doctorRequest);
}
