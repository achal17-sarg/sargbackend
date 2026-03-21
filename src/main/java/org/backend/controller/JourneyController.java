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
@RequestMapping("/sargweb/api/journey")
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
            JourneyInquiry savedInquiry = journeyRepository.save(inquiry);
            try {
                sendEmails(inquiry);
            } catch (Exception emailError) {
                System.err.println("Email sending failed but data saved: " + emailError.getMessage());
            }
            response.put("status", "success");
            response.put("message", "Your Enquiry has been submitted  successfully! Our team will contact you.");
            response.put("id", String.valueOf(savedInquiry.getId()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Request failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @Async
    private void sendEmails(JourneyInquiry inquiry) {
        try {
            // EMAIL TO HR
            SimpleMailMessage hrMessage = new SimpleMailMessage();
            hrMessage.setFrom(fromEmail);
            hrMessage.setTo("test@sargsoftech.com");
            hrMessage.setSubject("New Journey Inquiry - " + companyName);
            hrMessage.setText(
                "Hello Team,\n\nA new Enquiry has been booked.\n\n" +
                "Name: " + inquiry.getFullName() + "\n" +
                "Email: " + inquiry.getEmailAddress() + "\n" +
                "Mobile: " + inquiry.getMobileNumber() + "\n" +
                "Service Needed: " + inquiry.getExpertiseNeeded() + "\n" +
                "Project Goals: " + inquiry.getProjectGoals() + "\n\n" +
                "Regards,\n" + companyName + " System"
            );
            mailSender.send(hrMessage);

            // EMAIL TO CUSTOMER
            SimpleMailMessage customerMessage = new SimpleMailMessage();
            customerMessage.setFrom(fromEmail);
            customerMessage.setTo(inquiry.getEmailAddress());
            customerMessage.setSubject("We Received Your Request - " + companyName);
            customerMessage.setText(
                "Dear " + inquiry.getFullName() + ",\n\n" +
                "Thank you for contacting " + companyName + ".\n\n" +
                "We have successfully received your Enquiry request for:\n" +
                "Service: " + inquiry.getExpertiseNeeded() + "\n\n" +
                "Our team will get back to you shortly.\n\n" +
                "Best Regards,\n" + companyName + " Team"
            );
            mailSender.send(customerMessage);

        } catch (Exception e) {
            System.err.println("Email sending failed: " + e.getMessage());
        }
    }
}
