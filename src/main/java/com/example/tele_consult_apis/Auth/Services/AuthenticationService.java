package com.example.tele_consult_apis.Auth.Services;

import com.example.tele_consult_apis.Auth.Dtos.*;
import com.example.tele_consult_apis.Auth.Model.Doctor;
import com.example.tele_consult_apis.Auth.Model.Patient;
import com.example.tele_consult_apis.Auth.Model.Role;
import com.example.tele_consult_apis.Auth.Model.User;
import com.example.tele_consult_apis.Auth.Repository.UserRepository;
import com.example.tele_consult_apis.Auth.config.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;
    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;

    public AuthenticationService(PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }


    public AuthResponse authenticate(AuthRequest authRequest) {
        /*
         * this method is about authenticating the user
         * it takes in parameter , an object "authRequest" which represents
         * user credentials.
         * if the users exists and password correct  , ok
         * if the user doesn't exist , forbidden , 503
         * */

        User user = userRepository.findByEmail(authRequest.username())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        return  new AuthResponse(
                jwToken
        );
    }

    @Transactional
    public NewAccount createAccount (NewPatientRequest newPatientRequest) {
        Patient patient = Patient
                .builder()
                .first_name(newPatientRequest.first_name())
                .last_name(newPatientRequest.last_name())
                .phone_number(newPatientRequest.phone_number())
                .birth_date(newPatientRequest.birth_date())
                .enabled(true)
                .role(Role.PATIENT)
                .gender(newPatientRequest.gender())
                .password(passwordEncoder.encode(newPatientRequest.newAccount().password()))
                .email(newPatientRequest.newAccount().email())
                .build();

        userRepository.save(patient);

        return  new NewAccount(
                patient.getEmail() ,
                patient.getPassword()
        );
    }
    @Transactional
    public NewAccount createDoctorAccount(NewDoctorRequest newDoctorRequest) {
        Doctor doctor = Doctor
                .builder()
                .first_name(newDoctorRequest.first_name())
                .last_name(newDoctorRequest.last_name())
                .phone_number(newDoctorRequest.phone_number())
                .email(newDoctorRequest.newAccount().email())
                .password(passwordEncoder.encode(newDoctorRequest.newAccount().password()))
                .schedules(newDoctorRequest.scheduleList())
                .build();



        Doctor savedDoctor = userRepository.save(doctor);


        return  new NewAccount(
                doctor.getEmail(),
                doctor.getPassword()
        );

    }




}
