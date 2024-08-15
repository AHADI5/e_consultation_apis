package com.example.tele_consult_apis.Appointements.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TimeSlot {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    @OneToMany(mappedBy = "timeslot" , cascade = CascadeType.ALL)
    private List<TimePeriod> timePeriods = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    private boolean isFree ;

}
