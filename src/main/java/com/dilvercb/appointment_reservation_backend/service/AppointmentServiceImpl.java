package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.dto.AppointmentRequest;
import com.dilvercb.appointment_reservation_backend.dto.AppointmentResponse;
import com.dilvercb.appointment_reservation_backend.exception.ElementNotFoundException;
import com.dilvercb.appointment_reservation_backend.exception.OperationFailedException;
import com.dilvercb.appointment_reservation_backend.model.Appointment;
import com.dilvercb.appointment_reservation_backend.model.AvailableSlot;
import com.dilvercb.appointment_reservation_backend.model.Doctor;
import com.dilvercb.appointment_reservation_backend.model.UserEntity;
import com.dilvercb.appointment_reservation_backend.repository.AppointmentRepository;
import com.dilvercb.appointment_reservation_backend.repository.AvailableSlotRepository;
import com.dilvercb.appointment_reservation_backend.repository.DoctorRepository;
import com.dilvercb.appointment_reservation_backend.util.AuthService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService{
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AvailableSlotRepository availableSlotRepository;
    @Override
    public AppointmentResponse save(AppointmentRequest appointmentRequest) {

        UserEntity userEntity = authService.getCurrentUser();

        Doctor doctor = doctorRepository.findById(appointmentRequest.getIdDoctor())
                .orElseThrow(() -> new ElementNotFoundException("Doctor not found"));

        AvailableSlot availableSlot = availableSlotRepository.findByDateAndStartTimeAndDoctor(appointmentRequest.getDate(),appointmentRequest.getTime(),doctor)
                .orElseThrow(()-> new ElementNotFoundException("AvailableSlot not found"));

        if(availableSlot.isAvailable()){
            availableSlot.setAvailable(false);
            availableSlotRepository.save(availableSlot);

            Appointment appointment = Appointment.builder()
                    .date(appointmentRequest.getDate())
                    .time(appointmentRequest.getTime())
                    .reason(appointmentRequest.getReason())
                    .status("PENDING")
                    .patient(userEntity.getPatient())
                    .doctor(doctor)
                    .build();

            Appointment appointmentCreated = appointmentRepository.save(appointment);

            return AppointmentResponse.builder()
                    .id(appointmentCreated.getId())
                    .date(appointmentCreated.getDate())
                    .time(appointmentCreated.getTime())
                    .specialtyName(appointmentCreated.getDoctor().getSpecialty().getName())
                    .doctorName(appointmentCreated.getDoctor().getUser().getName()+appointmentCreated.getDoctor().getUser().getLastName())
                    .patientName(appointmentCreated.getPatient().getUser().getName()+appointmentCreated.getPatient().getUser().getLastName())
                    .build();
        }else {
            throw new OperationFailedException("Slot is not available.");
        }
    }
}
