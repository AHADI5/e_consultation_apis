package com.example.tele_consult_apis.Auth.Services;

import com.example.tele_consult_apis.Appointements.Model.Appointment;
import com.example.tele_consult_apis.Appointements.Model.Appointment_state;
import com.example.tele_consult_apis.Appointements.Model.Means;
import com.example.tele_consult_apis.Appointements.Model.TimePeriod;
import com.example.tele_consult_apis.Auth.Dtos.AppointementRequest;
import com.example.tele_consult_apis.Auth.Dtos.AppointementResponse;
import com.example.tele_consult_apis.Auth.Dtos.TimePeriodRequest;
import com.example.tele_consult_apis.Auth.Model.Doctor;
import com.example.tele_consult_apis.Auth.Model.Patient;
import com.example.tele_consult_apis.Auth.Repository.AppointementRepository;
import com.example.tele_consult_apis.Auth.Repository.TimePeriodRepository;
import com.example.tele_consult_apis.Auth.Repository.UserRepository;
import org.apache.logging.log4j.message.Message;
import org.springframework.stereotype.Service;

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
                "APPOINTMENT :" + savedAppointment.getAppointment_state().toString()
        );


    }

    public String appointmentReview (int appointmentID , String appointmentRequest) {
        Optional<Appointment> appointment = appointementRepository.findById((long) appointmentID) ;
        Appointment_state appointmentState = switch (appointmentRequest) {
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

}
