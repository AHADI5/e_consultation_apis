package com.example.tele_consult_apis.Auth.Services;

import com.example.tele_consult_apis.Auth.Dtos.AuthRequest;
import com.example.tele_consult_apis.Auth.Dtos.AuthResponse;
import com.example.tele_consult_apis.Auth.Dtos.NewAccount;
import com.example.tele_consult_apis.Auth.Dtos.NewPatientRequest;
import com.example.tele_consult_apis.Auth.Model.Patient;
import com.example.tele_consult_apis.Auth.Model.User;
import com.example.tele_consult_apis.Auth.Repository.UserRepository;
import com.example.tele_consult_apis.Auth.config.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                .gender(newPatientRequest.gender())
                .password(passwordEncoder.encode(newPatientRequest.passWord()))
                .build();

        userRepository.save(patient);

        return  new NewAccount(
                patient.getEmail() ,
                patient.getPassword()
        );
    }




}
