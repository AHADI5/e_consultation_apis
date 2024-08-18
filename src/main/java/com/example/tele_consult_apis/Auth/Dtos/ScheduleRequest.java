package com.example.tele_consult_apis.Auth.Dtos;

import java.util.List;

public record ScheduleRequest(
        List<TimeSlotRequest>  timeSlots
) {
}
