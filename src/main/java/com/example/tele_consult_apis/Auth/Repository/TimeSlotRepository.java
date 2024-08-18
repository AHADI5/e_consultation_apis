package com.example.tele_consult_apis.Auth.Repository;

import com.example.tele_consult_apis.Appointements.Model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
}
