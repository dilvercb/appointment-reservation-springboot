package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.model.Specialty;
import com.dilvercb.appointment_reservation_backend.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyServiceImpl implements SpecialtyService{
    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Override
    public List<Specialty> findAll() {
        return specialtyRepository.findAll();
    }
}
