package com.example.tele_consult_apis.Auth.Dtos;

public record ProfilePictureDto(
        String fileName  ,
        String type  ,
        byte[]  data
) {
}
