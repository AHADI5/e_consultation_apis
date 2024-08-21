package com.example.tele_consult_apis.Appointements.Model;

import com.example.tele_consult_apis.Auth.Model.Doctor;
import com.example.tele_consult_apis.Auth.Model.Patient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter @Setter
public class Appointment {
    @Id
    @GeneratedValue
    private Long appointmentId;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "time_period_id")
    private TimePeriod timePeriod;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    private  Appointment_state appointment_state;
    private  Means means ;

}
