package com.example.tele_consult_apis.Auth.Dtos;

public record NewPatientRequest(
      String first_name,
      String last_name,
      String gender,
      String birth_date,
      String phone_number ,
      String passWord  ,
      String email
) {

}
