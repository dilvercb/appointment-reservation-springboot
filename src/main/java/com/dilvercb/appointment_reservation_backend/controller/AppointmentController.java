package com.dilvercb.appointment_reservation_backend.controller;

import com.dilvercb.appointment_reservation_backend.dto.AppointmentRequest;
import com.dilvercb.appointment_reservation_backend.dto.AppointmentResponse;
import com.dilvercb.appointment_reservation_backend.model.Appointment;
import com.dilvercb.appointment_reservation_backend.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @PostMapping("/save")
    public AppointmentResponse save(@Valid @RequestBody AppointmentRequest appointmentRequest){
        return appointmentService.save(appointmentRequest);
    }
}
