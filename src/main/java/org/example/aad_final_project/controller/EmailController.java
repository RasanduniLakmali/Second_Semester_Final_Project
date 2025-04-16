package org.example.aad_final_project.controller;

import org.example.aad_final_project.dto.EmailRequestDTO;
import org.example.aad_final_project.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/email")
public class EmailController {

    @Autowired
    private EmailService emailService;



    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDTO emailRequestDTO) {

        boolean isSend = emailService.sendEmail(emailRequestDTO.getTo(), emailRequestDTO.getSubject(), emailRequestDTO.getMessage());
        if (isSend) {
            return ResponseEntity.ok("Email sent successfully");
        }else {
            return ResponseEntity.ofNullable("Email not sent");
        }

    }

}
