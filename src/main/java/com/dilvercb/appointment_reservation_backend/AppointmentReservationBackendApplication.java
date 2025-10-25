package com.dilvercb.appointment_reservation_backend;

import com.dilvercb.appointment_reservation_backend.enums.RoleEnum;
import com.dilvercb.appointment_reservation_backend.model.*;
import com.dilvercb.appointment_reservation_backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class AppointmentReservationBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentReservationBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner initDoctor(RoleRepository roleRepository, UserEntityRepository userEntityRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, SpecialtyRepository specialtyRepository) {
		return  args -> {

			Permission createPermission = new Permission(null,"CREATE");
			Permission readPermission = new Permission(null,"READ");
			Role adminRole = new Role(null, RoleEnum.ADMIN, Set.of(createPermission,readPermission));
			UserEntity adminUser = new UserEntity(null,"John","Perez","admin@gmail.com","$2a$12$48PogxzEaEBSkleSfMwmN.1dKhnMLbWmyFciW9SaxMqarFRaUR94a",true,true,true,true,null,null,Set.of(adminRole));
			UserEntity adminDb = userEntityRepository.save(adminUser);

			Specialty specialty = new Specialty(null,"Pediatría");
			Specialty specialty1 = new Specialty(null,"Odontología");

			Specialty specialtyPediatria = specialtyRepository.save(specialty);
			Specialty specialtyOdontologia = specialtyRepository.save(specialty1);

			Specialty cardiologia = new Specialty(null,"Cardiología");
			Specialty dermatologia = new Specialty(null, "Dermatología");
			Specialty otorrinolaringologia = new Specialty(null, "Otorrinolaringología");
			Specialty traumatologiaOrtopedia = new Specialty(null, "Traumatología y Ortopedia");
			Specialty gastroenterologia = new Specialty(null, "Gastroenterología");

			specialtyRepository.saveAll(List.of(cardiologia,
					dermatologia,
					otorrinolaringologia,
					traumatologiaOrtopedia,
					gastroenterologia
			));


			Role roleDoctor = new Role(null,RoleEnum.DOCTOR,null);
			UserEntity user = new UserEntity(null,"Carlos","Ramirez","carlos@gmail.com","$2a$12$48PogxzEaEBSkleSfMwmN.1dKhnMLbWmyFciW9SaxMqarFRaUR94a",true,true,true,true,null,null,Set.of(roleDoctor));
			Doctor doctor = Doctor.builder()
					.licenseNumber("34DEAD")
					.user(user)
					.specialty(specialtyPediatria)
					.build();


			UserEntity user1 = new UserEntity(null,"Jose","Gomez","jose@gmail.com","$2a$12$48PogxzEaEBSkleSfMwmN.1dKhnMLbWmyFciW9SaxMqarFRaUR94a",true,true,true,true,null,null,Set.of(roleDoctor));
			Doctor doctor1 = Doctor.builder()
					.licenseNumber("78DEAD")
					.user(user1)
					.specialty(specialtyOdontologia)
					.build();

			doctorRepository.saveAll(List.of(doctor,doctor1));


			Role role = new Role(null, RoleEnum.PATIENT,null);
			roleRepository.save(role);
		};
	}

}
