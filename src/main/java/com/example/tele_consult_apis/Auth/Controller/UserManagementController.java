package com.example.tele_consult_apis.Auth.Controller;

import com.example.tele_consult_apis.Auth.Dtos.DoctorDto;
import com.example.tele_consult_apis.Auth.Dtos.DoctorResponseRequest;
import com.example.tele_consult_apis.Auth.Model.ProfilePic;
import com.example.tele_consult_apis.Auth.Repository.UserRepository;
import com.example.tele_consult_apis.Auth.Services.UserManagementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public record UserManagementController (
        UserRepository userRepository ,
        UserManagementService userManagementService


) {
    @GetMapping("/doctor/{userEmail}")

    public DoctorResponseRequest getDoctorDetails( @PathVariable String userEmail) {
        return userManagementService.getDoctorRequest(userEmail);

    }
    @GetMapping("/doctor/doctors/")
    public List<DoctorResponseRequest> getAllDoctors() {
        return userManagementService.getAllDoctor();
    }


}
