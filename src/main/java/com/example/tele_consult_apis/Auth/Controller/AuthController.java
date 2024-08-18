package com.example.tele_consult_apis.Auth.Controller;

import com.example.tele_consult_apis.Auth.Dtos.*;
import com.example.tele_consult_apis.Auth.Model.ProfilePic;
import com.example.tele_consult_apis.Auth.Services.AuthenticationService;
import com.example.tele_consult_apis.Auth.Services.ImgStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/auth")
@Slf4j
public record AuthController(
        AuthenticationService authenticationService ,
        ImgStorage imageStorage
) {
    @PostMapping("/")
    public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
        return authenticationService.authenticate(authRequest);

    }
    @PostMapping("/doctor")
    public NewAccount createNewDoctorAccount(@RequestBody NewDoctorRequest doctorRequest) throws IOException {
        return  authenticationService.createDoctorAccount(doctorRequest);

    }
    @PostMapping("/patient")
    public NewAccount createNewPatientAccount(@RequestBody NewPatientRequest newPatientRequest) throws IOException {
        return authenticationService.createAccount(newPatientRequest);
    }
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("email") String email
    ) {
        String message = "";
        try {
            // Store the file (you can modify the storage logic to associate it with the user's email)
            imageStorage.Store(file, email);

            message = "Uploaded the file successfully: " + file.getOriginalFilename() + " for user: " + email;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + " for user: " + email + "!";
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files/{email}")
    public ResponseEntity<byte[]> getFile(@PathVariable String email) {
        ProfilePic profilePic = imageStorage.getImgByUser(email);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profilePic.getName() + "\"")
                .body(profilePic.getData());
    }

}
