package com.dilvercb.appointment_reservation_backend.util;

import com.dilvercb.appointment_reservation_backend.model.UserEntity;
import com.dilvercb.appointment_reservation_backend.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserEntityRepository userEntityRepository;

    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User "+ username + " not found"));
    }
}
