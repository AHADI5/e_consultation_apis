package com.example.tele_consult_apis.Appointements.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter @Setter
public class TimeSlot {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    @OneToMany(mappedBy = "timeslot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimePeriod> timePeriods = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @JsonIgnore
    private Schedule schedule;
    private boolean isFree ;

}
