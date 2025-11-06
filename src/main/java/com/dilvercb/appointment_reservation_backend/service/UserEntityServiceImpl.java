package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.dto.UserEntityResponse;
import com.dilvercb.appointment_reservation_backend.model.UserEntity;
import com.dilvercb.appointment_reservation_backend.util.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService{

    private final AuthService authService;
    @Override
    public UserEntityResponse getAdmin() {
        UserEntity userEntity = authService.getCurrentUser();
        return UserEntityResponse.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();
    }
}
