package com.example.tele_consult_apis.Auth.Dtos;

import org.springframework.web.multipart.MultipartFile;

public record PatientDto(
        String first_name,
        String last_name,
        String gender,
        String birth_date,
        String phone_number
        //MultipartFile file
) {
}
