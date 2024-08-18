package com.example.tele_consult_apis.Auth.Repository;

import com.example.tele_consult_apis.Appointements.Model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule , Long> {
}
