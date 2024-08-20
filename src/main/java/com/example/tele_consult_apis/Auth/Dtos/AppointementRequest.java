package com.example.tele_consult_apis.Auth.Dtos;

public record AppointementRequest(
        int timePeriodID ,
        String doctorEmail ,
        String patientEmail ,
        String means
) {
}
