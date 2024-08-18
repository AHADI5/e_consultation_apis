package com.example.tele_consult_apis.Auth.Dtos;

public record NewPatientRequest(
      PatientDto patientDto,
      NewAccount newAccount
) {

}
