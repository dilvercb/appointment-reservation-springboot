package com.dilvercb.appointment_reservation_backend.repository;

import com.dilvercb.appointment_reservation_backend.model.Doctor;
import com.dilvercb.appointment_reservation_backend.model.Schedule;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findByDoctor(Doctor doctor);
    void deleteByDoctor(Doctor doctor);

//    @Modifying
//    @Transactional
//    @Query("DELETE FROM Schedule s WHERE s.doctor = :doctor")
//    void deleteByDoctor(@Param("doctor") Doctor doctor);

}
