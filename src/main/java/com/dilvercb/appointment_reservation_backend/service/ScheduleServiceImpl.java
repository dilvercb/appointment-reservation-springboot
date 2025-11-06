package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.dto.ScheduleRequest;
import com.dilvercb.appointment_reservation_backend.dto.ScheduleResponse;
import com.dilvercb.appointment_reservation_backend.model.AvailableSlot;
import com.dilvercb.appointment_reservation_backend.model.Doctor;
import com.dilvercb.appointment_reservation_backend.model.Schedule;
import com.dilvercb.appointment_reservation_backend.model.UserEntity;
import com.dilvercb.appointment_reservation_backend.repository.AvailableSlotRepository;
import com.dilvercb.appointment_reservation_backend.repository.ScheduleRepository;
import com.dilvercb.appointment_reservation_backend.util.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private AvailableSlotRepository availableSlotRepository;

    @Override
    @Transactional
    public List<ScheduleResponse> save(List<ScheduleRequest> scheduleRequestList) {
        UserEntity userEntity = authService.getCurrentUser();
        this.deleteByDoctor(userEntity.getDoctor());

        List<Schedule> scheduleList = scheduleRequestList.stream().map(scheduleRequest ->
                Schedule.builder()
                        .dayOfWeek(scheduleRequest.getDayOfWeek())
                        .startTime(scheduleRequest.getStartTime())
                        .endTime(scheduleRequest.getEndTime())
                        .doctor(userEntity.getDoctor())
                        .build()).toList();
        List<Schedule> scheduleListCreated = scheduleRepository.saveAll(scheduleList);
        this.saveAvailableSlots(scheduleListCreated);
        return scheduleListCreated.stream().map(schedule ->
                ScheduleResponse.builder()
                        .id(schedule.getId())
                        .dayOfWeek(schedule.getDayOfWeek())
                        .startTime(schedule.getStartTime())
                        .endTime(schedule.getEndTime())
                        .username(schedule.getDoctor().getUser().getUsername())
                        .build()
                ).toList();
    }
    private void saveAvailableSlots(List<Schedule> scheduleList) {

        scheduleList.stream().forEach(schedule ->{

            DayOfWeek day = schedule.getDayOfWeek();

            LocalDate today = LocalDate.now();

            LocalDate tomorrow = today.plusDays(1);

            while (tomorrow.getDayOfWeek() != day) {
                tomorrow = tomorrow.plusDays(1);
            }

            LocalTime startTime = schedule.getStartTime();
            LocalTime endTime = schedule.getEndTime();

            while(startTime.isBefore(endTime)){
                LocalTime nextTime = startTime.plusHours(1);
                AvailableSlot availableSlot = AvailableSlot.builder()
                        .date(tomorrow)
                        .startTime(startTime)
                        .endTime(nextTime)
                        .available(true)
                        .doctor(schedule.getDoctor())
                        .build();

                availableSlotRepository.save(availableSlot);
                startTime = nextTime;
            }
        });
    }
    private void deleteByDoctor(Doctor doctor) {
        scheduleRepository.deleteByDoctor(doctor);
    }
}
