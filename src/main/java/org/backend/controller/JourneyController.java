package org.backend.controller;

import org.backend.entity.JourneyInquiry;
import org.backend.repository.JourneyInquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("sargweb/api/journey")
@CrossOrigin(origins = "*")
public class JourneyController {

    @Autowired
    private JourneyInquiryRepository journeyRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.hr.email}")
    private String adminEmail;

    @Value("${app.company.name}")
    private String companyName;

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitJourney(@RequestBody JourneyInquiry inquiry) {
        try {
            // 1️⃣ Save to Database
            journeyRepository.save(inquiry);

            // 2️⃣ Send Emails
            sendEmails(inquiry);

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Consultation booked successfully! Our team will contact you.");

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Request failed: " + e.getMessage());

            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @Async
    private void sendEmails(JourneyInquiry inquiry) {
        try {

            // =========================
            // 📧 1. EMAIL TO HR
            // =========================
            SimpleMailMessage hrMessage = new SimpleMailMessage();
            hrMessage.setFrom(fromEmail);
            hrMessage.setTo(adminEmail);
            hrMessage.setSubject("New Journey Inquiry - " + companyName);

            String hrContent =
                    "Hello Team,\n\n" +
                    "A new consultation has been booked.\n\n" +
                    "--- CLIENT DETAILS ---\n" +
                    "Name: " + inquiry.getFullName() + "\n" +
                    "Email: " + inquiry.getEmailAddress() + "\n" +
                    "Service Needed: " + inquiry.getExpertiseNeeded() + "\n" +
                    "Project Goals: " + inquiry.getProjectGoals() + "\n\n" +
                    "Regards,\n" +
                    companyName + " System";

            hrMessage.setText(hrContent);
            mailSender.send(hrMessage);


            // =========================
            // 📧 2. EMAIL TO CUSTOMER
            // =========================
            SimpleMailMessage customerMessage = new SimpleMailMessage();
            customerMessage.setFrom(fromEmail);
            customerMessage.setTo(inquiry.getEmailAddress());
            customerMessage.setSubject("We Received Your Request - " + companyName);

            String customerContent =
                    "Dear " + inquiry.getFullName() + ",\n\n" +
                    "Thank you for contacting " + companyName + ".\n\n" +
                    "We have successfully received your consultation request for:\n" +
                    "Service: " + inquiry.getExpertiseNeeded() + "\n\n" +
                    "Our team will get back to you shortly.\n\n" +
                    "Best Regards,\n" +
                    companyName + " Team";

            customerMessage.setText(customerContent);
            mailSender.send(customerMessage);

        } catch (Exception e) {
            System.err.println("Email sending failed: " + e.getMessage());
        }
    }
}
