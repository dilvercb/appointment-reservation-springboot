package com.dilvercb.appointment_reservation_backend.service;

import com.dilvercb.appointment_reservation_backend.exception.OperationFailedException;
import com.dilvercb.appointment_reservation_backend.model.Patient;
import com.dilvercb.appointment_reservation_backend.repository.PatientRepository;
import com.dilvercb.appointment_reservation_backend.repository.RoleRepository;
import com.dilvercb.appointment_reservation_backend.repository.UserEntityRepository;
import com.dilvercb.appointment_reservation_backend.dto.AuthCreateUserRequest;
import com.dilvercb.appointment_reservation_backend.dto.AuthLoginRequest;
import com.dilvercb.appointment_reservation_backend.dto.AuthResponse;
import com.dilvercb.appointment_reservation_backend.enums.RoleEnum;
import com.dilvercb.appointment_reservation_backend.model.Role;
import com.dilvercb.appointment_reservation_backend.model.UserEntity;
import com.dilvercb.appointment_reservation_backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User "+ username + " not found."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));
        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                userEntity.isAccountNonLocked(),
                authorityList);
    }
    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username().trim();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponse(username,"User loged successfully",accessToken,true);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username or password.");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password.");
        }
        return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    }

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {

        String username = authCreateUserRequest.username().trim();
        if(userEntityRepository.existsByUsername(username)){
            throw new OperationFailedException("Username " + username + " already exists.");
        }
        Role role = roleRepository.findByRoleEnum(RoleEnum.PATIENT).orElseThrow();

        UserEntity userEntity = UserEntity.builder()
                .name(authCreateUserRequest.name())
                .lastName(authCreateUserRequest.lastName())
                .username(username)
                .password(passwordEncoder.encode(authCreateUserRequest.password()))
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .roles(Set.of(role))
                .build();

        Patient patient = Patient.builder()
                .dateOfBirth(authCreateUserRequest.dateOfBirth())
                .medicalHistory(authCreateUserRequest.medicalHistory())
                .phone(authCreateUserRequest.phone())
                .user(userEntity)
                .build();
        Patient patientCreated = patientRepository.save(patient);
//        UserEntity userDb = userEntityRepository.save(userEntity);
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        patientCreated.getUser().getRoles()
                .forEach(role1 -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role1.getRoleEnum().name()))));

        patientCreated.getUser().getRoles().stream()
                .flatMap(role1 -> role.getPermissions().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(patientCreated.getUser().getUsername(), null, authorityList);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponse(patientCreated.getUser().getUsername(),"User loged successfully",accessToken,true);
    }
}
