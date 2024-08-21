package com.example.tele_consult_apis.Auth.Services;

import com.example.tele_consult_apis.Appointements.Model.Appointment;
import com.example.tele_consult_apis.Appointements.Model.Appointment_state;
import com.example.tele_consult_apis.Appointements.Model.Means;
import com.example.tele_consult_apis.Appointements.Model.TimePeriod;
import com.example.tele_consult_apis.Auth.Dtos.*;
import com.example.tele_consult_apis.Auth.Model.Doctor;
import com.example.tele_consult_apis.Auth.Model.Patient;
import com.example.tele_consult_apis.Auth.Model.User;
import com.example.tele_consult_apis.Auth.Repository.AppointementRepository;
import com.example.tele_consult_apis.Auth.Repository.TimePeriodRepository;
import com.example.tele_consult_apis.Auth.Repository.UserRepository;
import org.apache.logging.log4j.message.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public record AppointmentService(
        AppointementRepository appointementRepository ,
        UserRepository userRepository ,
        TimePeriodRepository timePeriodRepository

) {
    public AppointementResponse appointementRequest(AppointementRequest appointementRequest) {
        Doctor doctor = (Doctor) userRepository.findUserByEmail(appointementRequest.doctorEmail()) ;
        Patient patient  = (Patient) userRepository.findUserByEmail(appointementRequest.patientEmail()) ;
        Optional<TimePeriod> timePeriod = timePeriodRepository.findById((long) appointementRequest.timePeriodID());

        Means appointmentMeans = switch (appointementRequest.means()) {
            case "VIDEO" -> Means.VIDEO;
            case "MESSAGE" -> Means.MESSAGE;
            default -> Means.PHYSICAL;
        };

        Appointment appointment = Appointment
                .builder()
                .appointment_state(Appointment_state.PENDING)
                .doctor(doctor)
                .patient(patient)
                .means(appointmentMeans)
                .timePeriod(timePeriod.get())
                .build();

        Appointment savedAppointment  = appointementRepository.save(appointment);
        return new AppointementResponse(
                savedAppointment.getAppointmentId() ,
                savedAppointment.getTimePeriod().getStartTime() ,
                savedAppointment.getTimePeriod().getEndTime(),
                savedAppointment.getAppointment_state() ,
                appointment.getMeans()
        );


    }

    public String appointmentReview (AppointmentReviewRequest appointmentRequest) {
        Optional<Appointment> appointment = appointementRepository.findById((long) appointmentRequest.appointmentID()) ;
        Appointment_state appointmentState = switch (appointmentRequest.newAppointmentSate()) {
            case "REJECTED" -> Appointment_state.REJECTED;
            case "APPROVED" -> Appointment_state.ACCEPTED;
            default -> null;
        };
        if (appointment.isPresent()) {
            assert appointmentState != null;
            if (appointmentState.equals(Appointment_state.REJECTED)) {
                appointment.get().setAppointment_state(Appointment_state.REJECTED);
                return "REJECTED";
            }
            appointment.get().setAppointment_state(Appointment_state.ACCEPTED);
            return "APPROVED";
        }
        return null;
    }

    public List<AppointementResponse> getAppontmentByUserEmail(AppointmentRequest request) {
        // Find the user by email
        User user = userRepository.findUserByEmail(request.userEmail());

        // Declare variables
        Patient patient = null;
        Doctor doctor = null;
        List<Appointment> appointments = new ArrayList<>();

        // Check the role and fetch appointments accordingly
        if (request.role().equals("DOCTOR")) {
            doctor = (Doctor) user;
            // Fetch appointments by doctor
            appointments = appointementRepository.getAppointmentsByDoctor(doctor);
        } else {
            patient = (Patient) user;
            // Fetch appointments by patient
            appointments = appointementRepository.getAppointmentsByPatient(patient);
        }

        // Create response list
        List<AppointementResponse> appointementResponses = new ArrayList<>();
        for (Appointment appointment : appointments) {
            AppointementResponse appointementResponse = new AppointementResponse(
                    appointment.getAppointmentId(),
                    appointment.getTimePeriod().getStartTime(),
                    appointment.getTimePeriod().getEndTime(),
                    appointment.getAppointment_state(),
                    appointment.getMeans()
            );
            appointementResponses.add(appointementResponse);
        }

        return appointementResponses;
    }



}

