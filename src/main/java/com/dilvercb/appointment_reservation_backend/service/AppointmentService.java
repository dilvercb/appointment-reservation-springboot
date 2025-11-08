package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.dto.AppointmentRequest;
import com.dilvercb.appointment_reservation_backend.dto.AppointmentResponse;
import com.dilvercb.appointment_reservation_backend.model.Appointment;

public interface AppointmentService {
    AppointmentResponse save(AppointmentRequest appointmentRequest);
}
