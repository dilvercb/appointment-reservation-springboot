package com.dilvercb.appointment_reservation_backend.repository;

import com.dilvercb.appointment_reservation_backend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}
