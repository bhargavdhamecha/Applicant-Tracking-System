package com.email_service.Controller;

import com.email_service.ApplicationURI;
import com.email_service.Model.EmailRequest;
import com.email_service.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping(path = ApplicationURI.EMAIL)
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping(path = ApplicationURI.SEND_EMAIL,consumes = "application/*",produces = "application/*")
    public ResponseEntity<String> sendEmail(@RequestParam(required = false) File file,@RequestBody EmailRequest emailRequest) {
        boolean status = false;

        String toEmail = emailRequest.getTo();
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if(!toEmail.matches(regex)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid Email address");
        }
        if (file != null && file.isFile()) {
            status = emailService.sendEmail(emailRequest.getMessage(), emailRequest.getSubject(), emailRequest.getTo(), file);
        } else {
            status = emailService.sendEmail(emailRequest.getMessage(), emailRequest.getSubject(), emailRequest.getTo(), null);
        }

        if (status) {
            return ResponseEntity.ok("Email sent successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email not sent");
        }

    }
}
