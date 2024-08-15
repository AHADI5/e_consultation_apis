package com.example.tele_consult_apis.Auth.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Schedule {

    @Id
    private Long scheduleId;
    @ManyToOne
    @JoinColumn(name = "doctorID")
    private Doctor doctor;
    @OneToMany(mappedBy = "schedule" ,cascade = CascadeType.ALL)
    private List<TimeSlot> timeSlots;

}
