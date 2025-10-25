package com.dilvercb.appointment_reservation_backend.repository;

import com.dilvercb.appointment_reservation_backend.enums.RoleEnum;
import com.dilvercb.appointment_reservation_backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleEnum(RoleEnum roleEnum);
}
