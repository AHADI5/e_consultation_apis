package com.example.tele_consult_apis.Appointements.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TimePeriod {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "timeslot_id")
    private TimeSlot timeslot;
    private boolean isTaken;


}
