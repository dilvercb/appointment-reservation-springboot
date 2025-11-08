package com.dilvercb.appointment_reservation_backend.repository;

import com.dilvercb.appointment_reservation_backend.model.AvailableSlot;
import com.dilvercb.appointment_reservation_backend.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface AvailableSlotRepository extends JpaRepository<AvailableSlot,Long> {
    Optional<AvailableSlot> findByDateAndStartTimeAndDoctor(LocalDate date, LocalTime startTime, Doctor doctor);
}
