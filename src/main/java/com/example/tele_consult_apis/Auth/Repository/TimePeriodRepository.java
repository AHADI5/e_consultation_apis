package com.example.tele_consult_apis.Auth.Repository;

import com.example.tele_consult_apis.Appointements.Model.TimePeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimePeriodRepository extends JpaRepository<TimePeriod , Long> {
}

