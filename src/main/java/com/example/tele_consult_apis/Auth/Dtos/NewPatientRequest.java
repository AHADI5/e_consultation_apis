package com.example.tele_consult_apis.Auth.Dtos;

import com.example.tele_consult_apis.Auth.Model.Address;

public record NewPatientRequest(
      PatientDto patientDto,
      AddressDto address,
      NewAccount newAccount
) {

}
