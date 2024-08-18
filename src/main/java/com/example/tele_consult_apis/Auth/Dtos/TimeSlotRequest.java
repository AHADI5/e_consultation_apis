package com.example.tele_consult_apis.Auth.Dtos;

import java.util.Date;
import java.util.List;

public record TimeSlotRequest(
        Date date   ,
        List<TimePeriodRequest> timePeriods  ,
        boolean isFree
) {
}
