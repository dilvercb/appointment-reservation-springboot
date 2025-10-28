package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.dto.PatientResponse;
import com.dilvercb.appointment_reservation_backend.model.Patient;
import com.dilvercb.appointment_reservation_backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService{
    @Autowired
    private PatientRepository patientRepository;
    @Override
    public List<PatientResponse> findAll() {
        List<Patient> patientList = patientRepository.findAll();
        return patientList.stream().map(patient -> new PatientResponse(
                patient.getId(),
                patient.getUser().getName(),
                patient.getUser().getLastName(),
                patient.getUser().getUsername(),
                patient.getDateOfBirth(),
                patient.getMedicalHistory(),
                patient.getPhone()
        )).toList();
    }
}
