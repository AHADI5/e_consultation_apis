package com.example.tele_consult_apis.Auth.Dtos;

import com.example.tele_consult_apis.Appointements.Model.Schedule;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record DoctorDto(
        String first_name,
        String last_name,
        String phone_number,
        String specialty
       // MultipartFile file

) {
}
