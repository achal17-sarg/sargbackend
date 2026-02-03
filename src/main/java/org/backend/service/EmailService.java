package org.backend.service;

import org.backend.entity.CareerApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    
    // Using the authenticated email as the default sender
    @Value("${spring.mail.username}")
    private String senderEmail;
    
    @Value("${app.company.name:Sarg Softech}")
    private String companyName;

    /**
     * Sends notification to the internal team (HR/Admin)
     */
    public void sendApplicationNotificationToHR(CareerApplication application, MultipartFile file) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setFrom(senderEmail);
        // Sending to your specific internal email as requested
        message.setTo("achal.shinde@sargsoftech.com"); 
        message.setSubject("New Career Application Received - " + application.getPosition());
        
        message.setText(String.format(
            "Hello Admin,\n\nA new application has been submitted.\n\n" +
            "Position: %s\n" +
            "Candidate Name: %s\n" +
            "Candidate Email: %s\n" +
            "Phone: %s\n" +
            "Applied Date: %s\n\n" +
            "Cover Letter:\n%s",
            application.getPosition(),
            application.getFullName(),
            application.getEmail(),
            application.getPhone(),
            application.getAppliedAt(),
            application.getCoverLetter()
        ));
        
        mailSender.send(message);
    }
    
    /**
     * Sends confirmation to the Applicant
     */
    public void sendConfirmationToCandidate(CareerApplication application) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setFrom(senderEmail);
        message.setTo(application.getEmail());
        message.setSubject("Application Received - " + application.getPosition());
        
        message.setText(String.format(
            "Dear %s,\n\n" +
            "Thank you for applying for the %s position at %s.\n\n" +
            "We have received your application successfully and our team will review it shortly. " +
            "If your profile matches our requirements, we will reach out to you for the next steps.\n\n" +
            "Best regards,\n" +
            "HR Team - %s",
            application.getFullName(),
            application.getPosition(),
            companyName,
            companyName
        ));
        
        mailSender.send(message);
    }
}