package com.example.tele_consult_apis.Auth.Repository;

import com.example.tele_consult_apis.Appointements.Model.Appointment;
import com.example.tele_consult_apis.Auth.Model.Doctor;
import com.example.tele_consult_apis.Auth.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointementRepository extends JpaRepository<Appointment , Long> {
    List<Appointment> getAppointmentsByPatient(Patient patient);
    List<Appointment> getAppointmentsByDoctor(Doctor doctor);
}
