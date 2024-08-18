package com.example.tele_consult_apis.Auth.Services;

import com.example.tele_consult_apis.Auth.Model.ProfilePic;
import com.example.tele_consult_apis.Auth.Model.User;
import com.example.tele_consult_apis.Auth.Repository.ProfilePicRepository;
import com.example.tele_consult_apis.Auth.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public record ImgStorage(
        ProfilePicRepository profilePicRepository ,
        UserRepository userRepository
) {
    public void Store (MultipartFile file , String email) throws IOException {
        String fileName = file.getOriginalFilename();
        User user = userRepository.findUserByEmail(email) ;
        ProfilePic profilePic =  ProfilePic
                .builder()
                .name(fileName)
                .type(file.getContentType())
                .data(file.getBytes())
                .user(user)
                .build();
        profilePicRepository.save(profilePic);
        user.setProfilePic(profilePic);
        userRepository.save(user);
    }

    public ProfilePic getImgByUser (String  email) {
        User user = userRepository.findUserByEmail(email) ;
       return   profilePicRepository.findProfilePicByUser(user);


    }




}
