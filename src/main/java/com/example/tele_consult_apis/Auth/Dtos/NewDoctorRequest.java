package com.example.tele_consult_apis.Auth.Dtos;

import com.example.tele_consult_apis.Appointements.Model.Schedule;

import java.util.List;

public record NewDoctorRequest(
        DoctorDto doctorDto,
        List<ScheduleRequest> scheduleList ,
        NewAccount newAccount
) {
}
