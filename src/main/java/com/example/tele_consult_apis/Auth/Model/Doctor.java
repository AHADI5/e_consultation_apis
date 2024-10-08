package com.example.tele_consult_apis.Auth.Model;

import com.example.tele_consult_apis.Appointements.Model.Appointment;
import com.example.tele_consult_apis.Appointements.Model.Schedule;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Doctor extends User {
    private String first_name;
    private String last_name;
    private String phone_number;
    private String specialty;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();
    @OneToMany(mappedBy = "doctor" ,cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();


    @Builder
    public Doctor(String first_name, String last_name, ProfilePic file , Address address ,
                  String phone_number, String specialty, List<Schedule> schedules ,
                  String email, String password , boolean enabled , Role role


    ) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.specialty = specialty;
        this.schedules = schedules;
        this.setEmail(email);
        this.setPassword(password);
        this.setEnabled(enabled);
        this.setRole(role);
        this.setProfilePic(file);
        this.setAddress(address);


    }



}
