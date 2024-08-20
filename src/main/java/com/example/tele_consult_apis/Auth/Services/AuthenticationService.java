package com.example.tele_consult_apis.Auth.Services;

import com.example.tele_consult_apis.Appointements.Model.Schedule;
import com.example.tele_consult_apis.Appointements.Model.TimePeriod;
import com.example.tele_consult_apis.Appointements.Model.TimeSlot;
import com.example.tele_consult_apis.Auth.Dtos.*;
import com.example.tele_consult_apis.Auth.Model.*;
import com.example.tele_consult_apis.Auth.Repository.*;
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
    final AddressRepository addressRepository;

    public AuthenticationService(PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository, ScheduleRepository scheduleRepository, TimeSlotRepository timeSlotRepository, TimePeriodRepository timePeriodRepository, ImgStorage imageStorage, AddressRepository addressRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.timePeriodRepository = timePeriodRepository;
        this.imageStorage = imageStorage;
        this.addressRepository = addressRepository;
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
        Address address = Address
                .builder()
                .avenue(newPatientRequest.address().avenue())
                .quarter(newPatientRequest.address().quarter())
                .houseNumber(newPatientRequest.address().houseNumber())
                .build();
        Patient patient = Patient
                .builder()
                .address(addressRepository.save(address))
                //.file(imageStorage.Store(newPatientRequest.patientDto().file()))
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
        Address address = Address
                .builder()
                .avenue(newDoctorRequest.address().avenue())
                .quarter(newDoctorRequest.address().quarter())
                .houseNumber(newDoctorRequest.address().houseNumber())
                .build();
        Doctor doctor = Doctor
                .builder()
                .role(Role.DOCTOR)
                .address(addressRepository.save(address))
                .first_name(newDoctorRequest.doctorDto().first_name())
                .last_name(newDoctorRequest.doctorDto().last_name())
                .phone_number(newDoctorRequest.doctorDto().phone_number())
                .email(newDoctorRequest.newAccount().email())
                .specialty(newDoctorRequest.doctorDto().specialty())
                .password(passwordEncoder.encode(newDoctorRequest.newAccount().password()))
                .build();

        List<Schedule> schedules = new ArrayList<>();

        for (ScheduleRequest scheduleRequest : newDoctorRequest.scheduleList()) {
            List<TimeSlot> timeSlots = new ArrayList<>();  // Create a new list for each schedule

            for (TimeSlotRequest timeSlotRequest : scheduleRequest.timeSlots()) {
                List<TimePeriod> timePeriods = new ArrayList<>();  // Create a new list for each time slot

                for (TimePeriodRequest timePeriodRequest : timeSlotRequest.timePeriods()) {
                    TimePeriod timePeriod = TimePeriod
                            .builder()
                            .startTime(timePeriodRequest.start())
                            .endTime(timePeriodRequest.end())
                            .isTaken(false)  // Default value, can be changed based on logic
                            .build();


                    timePeriods.add(timePeriod);
                }

                TimeSlot timeSlot = TimeSlot
                        .builder()
                        .date(timeSlotRequest.date())
                        .isFree(true)  // Assuming all slots are free initially
                        .timePeriods(timePeriods)  // Associate time periods with the time slot
                        .build();
                for (TimePeriod timePeriod : timePeriods) {
                    timePeriod.setTimeslot(timeSlot);
                    timePeriodRepository.save(timePeriod);
                }

                timeSlots.add(timeSlot);
            }

            Schedule schedule = Schedule
                    .builder()
                    .timeSlots(timeSlots)  // Associate time slots with the schedule
                    .build();
            for(TimeSlot timeSlot : timeSlots){
                timeSlot.setSchedule(schedule);
                timeSlotRepository.save(timeSlot) ;
            }

            schedules.add(schedule);
        }

        // Set the schedules for the doctor and maintain the bidirectional relationship
        for(Schedule schedule : schedules){
            schedule.setDoctor(doctor);
            scheduleRepository.save(schedule);
        }
        doctor.setSchedules(schedules);


        // Save the doctor entity, which will cascade and save schedules, time slots, and time periods
        userRepository.save(doctor);

        return new NewAccount(
                doctor.getEmail(),
                doctor.getPassword()
        );
    }





}
