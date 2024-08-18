package com.example.tele_consult_apis.Auth.Services;

import com.example.tele_consult_apis.Auth.Dtos.DoctorResponseRequest;
import com.example.tele_consult_apis.Auth.Dtos.ProfilePictureDto;
import com.example.tele_consult_apis.Auth.Model.Doctor;
import com.example.tele_consult_apis.Auth.Model.ProfilePic;
import com.example.tele_consult_apis.Auth.Model.User;
import com.example.tele_consult_apis.Auth.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


public record UserManagementService (
        UserRepository userRepository ,
        ProfilePic profilePic

) {
    public DoctorResponseRequest getDoctorResponseRequest(long userID) {
        Doctor doctor = (Doctor) userRepository.findDoctorByUserID(userID);
        return  new DoctorResponseRequest(
                doctor.getUserID(),
                doctor.getFirst_name(),
                doctor.getLast_name() ,
                doctor.getPhone_number(),
                doctor.getSpecialty(),
                new ProfilePictureDto(
                        doctor.getProfilePic().getName() ,
                        doctor.getProfilePic().getType() ,
                        doctor.getProfilePic().getData()
                ),
                doctor.getSchedules()
        ) ;

    }

    public List<DoctorResponseRequest> getAllDoctor (){
        List<User> doctors = userRepository.getAllDoctors() ;
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
                    new ProfilePictureDto(
                            doctor.getProfilePic().getName() ,
                            doctor.getProfilePic().getType() ,
                            doctor.getProfilePic().getData()
                    ) ,
                    doctor.getSchedules()
            ) ;

            doctorResponseRequestList.add(doctorResponseRequest);
        }

        return doctorResponseRequestList;

    }

}