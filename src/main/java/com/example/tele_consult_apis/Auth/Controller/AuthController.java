package com.example.tele_consult_apis.Auth.Controller;

import com.example.tele_consult_apis.Auth.Dtos.NewAccount;
import com.example.tele_consult_apis.Auth.Dtos.NewDoctorRequest;
import com.example.tele_consult_apis.Auth.Dtos.NewPatientRequest;
import com.example.tele_consult_apis.Auth.Services.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public record AuthController(
        AuthenticationService authenticationService
) {
    @PostMapping("/doctor")
    public NewAccount createNewDoctorAccount(@RequestBody NewDoctorRequest doctorRequest) {
        return  authenticationService.createDoctorAccount(doctorRequest);

    }
    @PostMapping("/patient")
    public NewAccount createNewPatientAccount(@RequestBody NewPatientRequest newPatientRequest) {
        return authenticationService.createAccount(newPatientRequest);
    }
}
