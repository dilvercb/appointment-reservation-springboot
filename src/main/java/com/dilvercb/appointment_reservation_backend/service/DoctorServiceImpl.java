package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.dto.DoctorResponse;
import com.dilvercb.appointment_reservation_backend.exception.ElementNotFoundException;
import com.dilvercb.appointment_reservation_backend.model.Doctor;
import com.dilvercb.appointment_reservation_backend.model.Specialty;
import com.dilvercb.appointment_reservation_backend.repository.DoctorRepository;
import com.dilvercb.appointment_reservation_backend.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService{
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Override
    public List<DoctorResponse> findBySpecialty(Long idSpecialty) {
        Specialty specialty = specialtyRepository.findById(idSpecialty)
                .orElseThrow(() -> new ElementNotFoundException("Specialty with id " + idSpecialty + " not found"));
        List<Doctor> doctors = doctorRepository.findBySpecialty(specialty);
        return doctors.stream().map(doctor -> new DoctorResponse(
                doctor.getId(),
                doctor.getLicenseNumber(),
                doctor.getUser().getName(),
                doctor.getUser().getLastName(),
                doctor.getSpecialty().getName()
        )).toList();
    }
}
