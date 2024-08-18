package com.example.tele_consult_apis.Auth.Controller;

import com.example.tele_consult_apis.Auth.Dtos.DoctorResponseRequest;
import com.example.tele_consult_apis.Auth.Model.ProfilePic;
import com.example.tele_consult_apis.Auth.Repository.UserRepository;

public record UserManagementController (
        UserRepository userRepository ,
        ProfilePic profilePic

) {
    public DoctorResponseRequest getDoctorResponseRequest() {

    }

}
