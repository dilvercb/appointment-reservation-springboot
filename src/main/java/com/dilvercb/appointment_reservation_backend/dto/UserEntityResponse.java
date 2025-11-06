package com.dilvercb.appointment_reservation_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntityResponse {
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
}
