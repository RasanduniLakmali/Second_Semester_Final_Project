package org.example.aad_final_project.service;

public interface EmailService {

    public boolean sendEmail(String to, String subject, String body);
}
