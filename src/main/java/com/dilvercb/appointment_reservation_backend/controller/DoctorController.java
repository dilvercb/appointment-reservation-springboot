package com.dilvercb.appointment_reservation_backend.controller;

import com.dilvercb.appointment_reservation_backend.dto.DoctorRequest;
import com.dilvercb.appointment_reservation_backend.dto.DoctorResponse;
import com.dilvercb.appointment_reservation_backend.model.Doctor;
import com.dilvercb.appointment_reservation_backend.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/findBySpecialty/{idSpecialty}")
    public List<DoctorResponse> findBySpecialty(@PathVariable Long idSpecialty){
        return doctorService.findBySpecialty(idSpecialty);
    }
    @GetMapping("/findAll")
    public List<DoctorResponse> findAll(){
        return doctorService.findAll();
    }
    @PostMapping("/save")
    public DoctorResponse save(@Valid @RequestBody DoctorRequest doctorRequest){
        return doctorService.save(doctorRequest);
    }
}
