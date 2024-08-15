package com.example.tele_consult_apis.Auth.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Doctor extends User {
    private String first_name;
    private String last_name;
    private String phone_number;
    private String specialty;
    @OneToMany(mappedBy = "doctor" , cascade = CascadeType.ALL)
    List<Schedule> schedules  = new ArrayList<>();



}
