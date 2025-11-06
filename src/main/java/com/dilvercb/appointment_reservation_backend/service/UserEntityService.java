package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.dto.UserEntityResponse;
import com.dilvercb.appointment_reservation_backend.model.UserEntity;

public interface UserEntityService {
    UserEntityResponse getAdmin();
}
