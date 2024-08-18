package com.example.tele_consult_apis.Auth.Repository;

import com.example.tele_consult_apis.Auth.Model.Doctor;
import com.example.tele_consult_apis.Auth.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.tokens.DocumentStartToken;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer > {
    Optional<User> findByEmail(String username);

     User findDoctorByUserID(Long userID);
     List<User>  getAllDoctors();
}
