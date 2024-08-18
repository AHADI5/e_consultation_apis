package com.example.tele_consult_apis.Auth.Dtos;

import com.example.tele_consult_apis.Appointements.Model.Schedule;

import java.util.List;

public record DoctorResponseRequest(
        long doctorId,
        String first_name,
        String last_name,
        String phone_number,
        String specialty ,
        ProfilePictureDto  profilePictureDto ,
        List<Schedule> schedules
){
}
