package com.example.tele_consult_apis.Auth.Repository;

import com.example.tele_consult_apis.Auth.Model.ProfilePic;
import com.example.tele_consult_apis.Auth.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePicRepository extends JpaRepository<ProfilePic , String> {
    ProfilePic findProfilePicByUser(User user);
}
