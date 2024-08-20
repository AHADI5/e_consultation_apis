package com.example.tele_consult_apis.Appointements.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter @Setter
public class TimePeriod {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "timeslot_id")
    private TimeSlot timeslot;
    private boolean isTaken;
    @OneToOne(mappedBy = "timePeriod", cascade = CascadeType.ALL)
    private Appointment appointment;



}
