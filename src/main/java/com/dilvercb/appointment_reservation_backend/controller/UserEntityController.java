package com.dilvercb.appointment_reservation_backend.controller;

import com.dilvercb.appointment_reservation_backend.dto.UserEntityResponse;
import com.dilvercb.appointment_reservation_backend.model.UserEntity;
import com.dilvercb.appointment_reservation_backend.repository.UserEntityRepository;
import com.dilvercb.appointment_reservation_backend.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntityController {
    @Autowired
    private UserEntityService userEntityService;
    @GetMapping("/admin")
    public UserEntityResponse getAdmin(){
        return userEntityService.getAdmin();
    }
}
