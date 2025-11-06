package com.dilvercb.appointment_reservation_backend.controller;

import com.dilvercb.appointment_reservation_backend.dto.ScheduleRequest;
import com.dilvercb.appointment_reservation_backend.dto.ScheduleResponse;
import com.dilvercb.appointment_reservation_backend.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @PostMapping("/save")
    public List<ScheduleResponse> save(@RequestBody List<@Valid ScheduleRequest> scheduleRequestList){
        return scheduleService.save(scheduleRequestList);
    }
}
