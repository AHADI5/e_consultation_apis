package com.example.tele_consult_apis.Auth.Dtos;

import com.example.tele_consult_apis.Appointements.Model.Appointment_state;
import com.example.tele_consult_apis.Appointements.Model.Means;

import java.time.LocalDateTime;

public record AppointementResponse(
        long appointmentID ,
        LocalDateTime startTime ,
        LocalDateTime endTime ,
        Appointment_state appointmentState  ,
        Means means
) {

}
