package com.example.tele_consult_apis.Auth.Model;

import com.example.tele_consult_apis.Appointements.Model.Appointment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor

@EqualsAndHashCode(callSuper = true)
public class Patient extends User {
    private String first_name;
    private String last_name;
    private String gender;
    private String birth_date;
    private String phone_number;
    @OneToMany(mappedBy = "patient" ,cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    @Builder
    public Patient(String first_name, String last_name, Address address ,
                   String gender, String birth_date, ProfilePic file ,
                   String phone_number, String email, String password, boolean enabled , Role role
    ) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.birth_date = birth_date;
        this.phone_number = phone_number;
        this.setEmail(email);
        this.setPassword(password);
        this.setEnabled(enabled);
        this.setRole(role);
        this.setProfilePic(file);
        this.setAddress(address);



    }
}
