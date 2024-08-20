package com.example.tele_consult_apis.Auth.Repository;

import com.example.tele_consult_apis.Appointements.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointementRepository extends JpaRepository<Appointment , Long> {
}
