package com.example.tele_consult_apis.Auth.Dtos;

import com.example.tele_consult_apis.Appointements.Model.Schedule;
import com.example.tele_consult_apis.Auth.Model.Address;

import java.util.List;

public record NewDoctorRequest(
        DoctorDto doctorDto,
        AddressDto address,
        List<ScheduleRequest> scheduleList ,
        NewAccount newAccount
) {
}
