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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/journey")
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
        Map<String, String> response = new HashMap<>();
        try {
            // 1️⃣ Save to Database
            System.out.println("Saving journey inquiry: " + inquiry.getFullName());
            JourneyInquiry savedInquiry = journeyRepository.save(inquiry);
            System.out.println("Journey inquiry saved with ID: " + savedInquiry.getId());

            // 2️⃣ Send Emails (async - won't block response)
            try {
                sendEmails(inquiry);
            } catch (Exception emailError) {
                System.err.println("Email sending failed but data saved: " + emailError.getMessage());
            }

            response.put("status", "success");
            response.put("message", "Consultation booked successfully! Our team will contact you.");
            response.put("id", String.valueOf(savedInquiry.getId()));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Request failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<JourneyInquiry>> getAllJourneyInquiries() {
        List<JourneyInquiry> inquiries = journeyRepository.findAll();
        return ResponseEntity.ok(inquiries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJourneyInquiryById(@PathVariable Long id) {
        return journeyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteJourneyInquiry(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            if (journeyRepository.existsById(id)) {
                journeyRepository.deleteById(id);
                response.put("status", "success");
                response.put("message", "Journey inquiry deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Journey inquiry not found");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to delete: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getJourneyInquiryCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("count", journeyRepository.count());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "working");
        response.put("totalRecords", String.valueOf(journeyRepository.count()));
        response.put("message", "Journey API is working fine");
        return ResponseEntity.ok(response);
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
