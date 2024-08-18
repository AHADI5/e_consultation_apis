package com.example.tele_consult_apis.Appointements.Model;

import com.example.tele_consult_apis.Auth.Model.Doctor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Schedule {

    @Id
    @GeneratedValue
    private Long scheduleId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "doctorID")
    private Doctor doctor;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeSlot> timeSlots = new ArrayList<>();

}
