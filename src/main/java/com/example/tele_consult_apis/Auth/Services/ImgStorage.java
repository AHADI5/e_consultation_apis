package com.example.tele_consult_apis.Auth.Services;

import com.example.tele_consult_apis.Auth.Dtos.ProfilePictureDto;
import com.example.tele_consult_apis.Auth.Model.ProfilePic;
import com.example.tele_consult_apis.Auth.Model.User;
import com.example.tele_consult_apis.Auth.Repository.ProfilePicRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public record ImgStorage(
        ProfilePicRepository profilePicRepository
) {
    public ProfilePictureDto Store (MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        ProfilePic profilePic =  ProfilePic
                .builder()
                .name(fileName)
                .type(file.getContentType())
                .data(file.getBytes())
                .build();
        ProfilePic savedProfile =   profilePicRepository.save(profilePic);

        return  new ProfilePictureDto(
                savedProfile.getName() ,
                savedProfile.getType(),
                savedProfile.getData()
        ) ;
    }

    public ProfilePictureDto getImgByUser (User user) {
        ProfilePic profilePic =  profilePicRepository.findProfilePicByUser(user);
        return  new ProfilePictureDto(
                profilePic.getName() ,
                profilePic.getType(),
                profilePic.getData()
        ) ;
    }




}
