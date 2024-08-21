package com.example.tele_consult_apis.Appointements.Controller;

import com.example.tele_consult_apis.Auth.Dtos.AppointementRequest;
import com.example.tele_consult_apis.Auth.Dtos.AppointementResponse;
import com.example.tele_consult_apis.Auth.Dtos.AppointmentRequest;
import com.example.tele_consult_apis.Auth.Dtos.AppointmentReviewRequest;

import com.example.tele_consult_apis.Auth.Services.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1/appointment")
public record AppointmentController(
        AppointmentService appointmentService
) {
    @PostMapping("/bookAppointment")
    public AppointementResponse bookAppointment(@RequestBody AppointementRequest appointementRequest) {
        return  appointmentService.appointementRequest(appointementRequest);

    }
    @GetMapping("/review")
    public  String appointmentReview( @RequestBody AppointmentReviewRequest request) {
        return appointmentService.appointmentReview(request) ;

    }

    @GetMapping("/appointment")
    public List<AppointementResponse> appointment(@RequestBody AppointmentRequest reequest) {
        return appointmentService.getAppontmentByUserEmail(reequest);
    }

}
