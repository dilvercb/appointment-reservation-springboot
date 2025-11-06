package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.dto.ScheduleRequest;
import com.dilvercb.appointment_reservation_backend.dto.ScheduleResponse;

import java.util.List;

public interface ScheduleService {
    List<ScheduleResponse> save(List<ScheduleRequest> scheduleRequestList);
}
