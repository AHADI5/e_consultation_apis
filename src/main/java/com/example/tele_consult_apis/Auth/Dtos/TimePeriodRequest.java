package com.example.tele_consult_apis.Auth.Dtos;

import java.time.LocalDateTime;

public record TimePeriodRequest(
        LocalDateTime start ,
        LocalDateTime end ,
        boolean isTaken
) {
}
