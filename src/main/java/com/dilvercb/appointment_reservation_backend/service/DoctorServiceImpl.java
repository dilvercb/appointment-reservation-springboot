package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.dto.DoctorRequest;
import com.dilvercb.appointment_reservation_backend.dto.DoctorResponse;
import com.dilvercb.appointment_reservation_backend.enums.RoleEnum;
import com.dilvercb.appointment_reservation_backend.exception.ElementNotFoundException;
import com.dilvercb.appointment_reservation_backend.exception.OperationFailedException;
import com.dilvercb.appointment_reservation_backend.model.Doctor;
import com.dilvercb.appointment_reservation_backend.model.Role;
import com.dilvercb.appointment_reservation_backend.model.Specialty;
import com.dilvercb.appointment_reservation_backend.model.UserEntity;
import com.dilvercb.appointment_reservation_backend.repository.DoctorRepository;
import com.dilvercb.appointment_reservation_backend.repository.RoleRepository;
import com.dilvercb.appointment_reservation_backend.repository.SpecialtyRepository;
import com.dilvercb.appointment_reservation_backend.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DoctorServiceImpl implements DoctorService{
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Override
    public List<DoctorResponse> findBySpecialty(Long idSpecialty) {
        Specialty specialty = specialtyRepository.findById(idSpecialty)
                .orElseThrow(() -> new ElementNotFoundException("Specialty with id " + idSpecialty + " not found"));
        List<Doctor> doctors = doctorRepository.findBySpecialty(specialty);
        return doctors.stream().map(doctor -> new DoctorResponse(
                doctor.getId(),
                doctor.getLicenseNumber(),
                doctor.getUser().getName(),
                doctor.getUser().getLastName(),
                doctor.getUser().getUsername(),
                doctor.getSpecialty().getName()
        )).toList();
    }

    @Override
    public List<DoctorResponse> findAll() {
        List<Doctor> doctorList = doctorRepository.findAll();
        return doctorList.stream().map(doctor -> new DoctorResponse(
                doctor.getId(),
                doctor.getLicenseNumber(),
                doctor.getUser().getName(),
                doctor.getUser().getLastName(),
                doctor.getUser().getUsername(),
                doctor.getSpecialty().getName()
        )).toList();
    }

    @Override
    public DoctorResponse save(DoctorRequest doctorRequest) {
        if(userEntityRepository.existsByUsername(doctorRequest.getUsername().trim())){
            throw new OperationFailedException("User "+ doctorRequest.getUsername() + " ya existe");
        }
        Role role = roleRepository.findByRoleEnum(RoleEnum.DOCTOR)
                .orElseThrow(()-> new ElementNotFoundException("Role not found"));
        Specialty specialty = specialtyRepository.findById(doctorRequest.getIdSpecialty())
                .orElseThrow(()-> new ElementNotFoundException("Specialty not found"));
        UserEntity user = UserEntity.builder()
                .name(doctorRequest.getName().trim())
                .lastName(doctorRequest.getLastName().trim())
                .username(doctorRequest.getUsername().trim())
                .password(passwordEncoder.encode(doctorRequest.getPassword().trim()))
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .roles(Set.of(role))
                .build();
        Doctor doctor = Doctor.builder()
                .licenseNumber(doctorRequest.getLicenseNumber().trim())
                .user(user)
                .specialty(specialty)
                .build();
        Doctor doctorCreated = doctorRepository.save(doctor);
        return DoctorResponse.builder()
                .id(doctorCreated.getId())
                .licenseNumber(doctorCreated.getLicenseNumber())
                .name(doctorCreated.getUser().getName())
                .lastName(doctorCreated.getUser().getLastName())
                .username(doctorCreated.getUser().getUsername())
                .specialty(doctorCreated.getSpecialty().getName())
                .build();
    }
}
