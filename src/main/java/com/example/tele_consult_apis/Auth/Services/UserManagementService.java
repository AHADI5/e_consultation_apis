package com.example.tele_consult_apis.Auth.Services;

import com.example.tele_consult_apis.Auth.Dtos.DoctorResponseRequest;
import com.example.tele_consult_apis.Auth.Dtos.ProfilePictureDto;
import com.example.tele_consult_apis.Auth.Model.Doctor;
import com.example.tele_consult_apis.Auth.Model.Role;
import com.example.tele_consult_apis.Auth.Model.User;
import com.example.tele_consult_apis.Auth.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service

public record UserManagementService (
        UserRepository userRepository ,
        ImgStorage imgStorage


) {

    public DoctorResponseRequest getDoctorRequest(String email) {
        Doctor doctor = (Doctor) userRepository.findDoctorByEmail(email);

        return  new DoctorResponseRequest(
                doctor.getUserID(),
                doctor.getFirst_name(),
                doctor.getLast_name() ,
                doctor.getPhone_number(),
                doctor.getSpecialty(),
                imgStorage.getImgByUser(doctor.getEmail()),
                doctor.getSchedules()
        ) ;

    }

    public List<DoctorResponseRequest> getAllDoctor (){
        List<User> doctors = userRepository.findUserByRole(Role.DOCTOR) ;
        List<Doctor> doctorList = new ArrayList<>();
        List<DoctorResponseRequest> doctorResponseRequestList = new ArrayList<>();

        for (User user : doctors) {
            Doctor doctor = (Doctor) userRepository.findDoctorByUserID(user.getUserID());
            doctorList.add(doctor);
        }

        for (Doctor doctor : doctorList) {
            DoctorResponseRequest doctorResponseRequest = new DoctorResponseRequest(
                    doctor.getUserID()  ,
                    doctor.getFirst_name(),
                    doctor.getLast_name()  ,
                    doctor.getPhone_number() ,
                    doctor.getSpecialty() ,
                    imgStorage.getImgByUser(doctor.getEmail()),
                    doctor.getSchedules()
            ) ;

            doctorResponseRequestList.add(doctorResponseRequest);
        }
        return doctorResponseRequestList;
    }



}