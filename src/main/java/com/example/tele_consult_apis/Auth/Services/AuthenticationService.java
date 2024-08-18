package com.example.tele_consult_apis.Auth.Services;

import com.example.tele_consult_apis.Appointements.Model.Schedule;
import com.example.tele_consult_apis.Appointements.Model.TimePeriod;
import com.example.tele_consult_apis.Appointements.Model.TimeSlot;
import com.example.tele_consult_apis.Auth.Dtos.*;
import com.example.tele_consult_apis.Auth.Model.Doctor;
import com.example.tele_consult_apis.Auth.Model.Patient;
import com.example.tele_consult_apis.Auth.Model.Role;
import com.example.tele_consult_apis.Auth.Model.User;
import com.example.tele_consult_apis.Auth.Repository.ScheduleRepository;
import com.example.tele_consult_apis.Auth.Repository.TimePeriodRepository;
import com.example.tele_consult_apis.Auth.Repository.TimeSlotRepository;
import com.example.tele_consult_apis.Auth.Repository.UserRepository;
import com.example.tele_consult_apis.Auth.config.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;
    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final ScheduleRepository scheduleRepository ;
    final TimeSlotRepository timeSlotRepository ;
    final TimePeriodRepository timePeriodRepository;
    final  ImgStorage imageStorage;

    public AuthenticationService(PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository, ScheduleRepository scheduleRepository, TimeSlotRepository timeSlotRepository, TimePeriodRepository timePeriodRepository, ImgStorage imageStorage) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.timePeriodRepository = timePeriodRepository;
        this.imageStorage = imageStorage;
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
    public NewAccount createAccount (NewPatientRequest newPatientRequest) throws IOException {
        Patient patient = Patient
                .builder()
                .file(imageStorage.Store(newPatientRequest.patientDto().file()))
                .first_name(newPatientRequest.patientDto().first_name())
                .last_name(newPatientRequest.patientDto().last_name())
                .phone_number(newPatientRequest.patientDto().phone_number())
                .birth_date(newPatientRequest.patientDto().birth_date())
                .enabled(true)
                .role(Role.PATIENT)
                .gender(newPatientRequest.patientDto().gender())
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
    public NewAccount createDoctorAccount(NewDoctorRequest newDoctorRequest) throws IOException {
        Doctor doctor = Doctor
                .builder()
                .file(imageStorage.Store(newDoctorRequest.doctorDto().file()))
                .role(Role.DOCTOR)
                .first_name(newDoctorRequest.doctorDto().first_name())
                .last_name(newDoctorRequest.doctorDto().last_name())
                .phone_number(newDoctorRequest.doctorDto().phone_number())
                .email(newDoctorRequest.newAccount().email())
                .password(passwordEncoder.encode(newDoctorRequest.newAccount().password()))
                .build();


        List<Schedule> schedules = new ArrayList<>() ;
        List<TimeSlot>timeSlots  = new ArrayList<>() ;
        List<TimePeriod> timePeriods  = new ArrayList<>() ;
        for (ScheduleRequest schedule : newDoctorRequest.scheduleList()) {
            for (TimeSlotRequest timeSlot : schedule.timeSlots()) {
                for ( TimePeriodRequest timePeriod : timeSlot.timePeriods()) {
                    TimePeriod timePeriodItem = TimePeriod
                            .builder()
                            .startTime(timePeriod.start())
                            .endTime(timePeriod.end())
                            .build();
                    timePeriods.add(timePeriodItem);
                }
                TimeSlot timeSlotItem = TimeSlot
                        .builder()
                        .date(timeSlot.date())
                        .isFree(true)
                        .timePeriods(timePeriods)
                        .build() ;
                timeSlots.add(timeSlotItem);

            }
            Schedule scheduleItem = Schedule
                    .builder()
                    .timeSlots(timeSlots)
                    .build();
            schedules.add(scheduleItem);
        }

        doctor.setSchedules(schedules);
        userRepository.save(doctor);


        return  new NewAccount(
                doctor.getEmail(),
                doctor.getPassword()
        );

    }




}
