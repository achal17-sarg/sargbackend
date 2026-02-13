package org.backend.service;

import org.backend.entity.CareerApplication;
import org.backend.entity.DemoRequest;
import org.backend.entity.JourneyInquiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Objects;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String senderEmail;
    
    @Value("${app.hr.email}")
    private String adminEmail; // This now correctly pulls from your application.properties
    
    @Value("${app.company.name:Sarg Softech}")
    private String companyName;

    /**
     * Sends notification to the internal team for "Start Your Journey" form
     */
    @Async
    public void sendJourneyNotification(JourneyInquiry inquiry) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(adminEmail); 
        message.setSubject("New Consultation Request - " + companyName);
        
        String emailBody = String.format(
            "Hello Team,\n\nA new 'Start Your Journey' request has been received.\n\n" +
            "Client Name: %s\n" +
            "Client Email: %s\n" +
            "Expertise Needed: %s\n" +
            "Project Goals: %s\n\n" +
            "Regards,\n%s System",
            inquiry.getFullName(), 
            inquiry.getEmailAddress(), 
            inquiry.getExpertiseNeeded(), 
            inquiry.getProjectGoals(), 
            companyName
        );
        
        message.setText(emailBody);
        mailSender.send(message);
    }

    /**
     * Sends notification to the HR team with Resume Attached for Careers
     */
    @Async
    public void sendApplicationNotificationToHR(CareerApplication application, MultipartFile file) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom(senderEmail);
        helper.setTo(adminEmail); // Uses the admin email from your config
        helper.setSubject(" Application for " + application.getPosition() + " - " + application.getFullName());
        
        String emailBody = String.format(
            "Hello Admin,\n\nA new job application has been submitted.\n\n" +
            "Position: %s\n" +
            "Candidate Name: %s\n" +
            "Candidate Email: %s\n" +
            "Phone: %s\n" +
            "Applied Date: %s\n\n" +
            "Cover Letter:\n%s\n\n" +
            "Note: The candidate's resume is attached to this email.",
            application.getPosition(),
            application.getFullName(),
            application.getEmail(),
            application.getPhone(),
            application.getAppliedAt(),
            application.getCoverLetter()
        );
        
        helper.setText(emailBody);

        if (file != null && !file.isEmpty()) {
            String fileName = Objects.requireNonNullElse(file.getOriginalFilename(), "Resume.pdf");
            helper.addAttachment(fileName, file);
        }
        
        mailSender.send(message);
    }
    
    /**
     * Sends confirmation to the Applicant
     */
    @Async
    public void sendConfirmationToCandidate(CareerApplication application) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setFrom(senderEmail);
        message.setTo(application.getEmail());
        message.setSubject("Application Received - " + application.getPosition());
        
        message.setText(String.format(
            "Dear %s,\n\n" +
            "Thank you for applying for the %s position at %s.\n\n" +
            "We have received your application successfully and our team will review it shortly.\n\n" +
            "Best regards,\n" +
            "HR Team - %s",
            application.getFullName(),
            application.getPosition(),
            companyName,
            companyName
        ));
        
        mailSender.send(message);
    }
    public void sendSimpleMessage(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage(); 
    message.setFrom("achal.shinde65@gmail.com");
    message.setTo(to); 
    message.setSubject(subject); 
    message.setText(text);
    mailSender.send(message);
}
    @Async
public void sendJourneyConfirmationToCustomer(JourneyInquiry inquiry) {

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(senderEmail);
    message.setTo(inquiry.getEmailAddress());
    message.setSubject("We Received Your Consultation Request - " + companyName);

    String emailBody = String.format(
        "Dear %s,\n\n" +
        "Thank you for contacting %s.\n\n" +
        "We have received your consultation request for:\n" +
        "Service: %s\n\n" +
        "Our team will review your requirements and contact you shortly.\n\n" +
        "Best Regards,\n" +
        "%s Team",
        inquiry.getFullName(),
        companyName,
        inquiry.getExpertiseNeeded(),
        companyName
    );

    message.setText(emailBody);
    mailSender.send(message);
}
public void sendDemoNotification(DemoRequest request) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("hr@sargsoftech.com"); 
    message.setTo("info@sargsoftech.com"); // Your receiving email
    message.setSubject("New Demo Request: " + request.getProject());
    
    String content = "New Demo Request Details:\n" +
                     "Name: " + request.getFullName() + "\n" +
                     "Role: " + request.getRole() + "\n" +
                     "Email: " + request.getEmail() + "\n" +
                     "Phone: " + request.getPhoneNumber() + "\n" +
                     "Project: " + request.getProject() + "\n" +
                     "Plan: " + request.getPlan() + "\n" +
                     "Message: " + request.getMessage();
                     
    message.setText(content);
    mailSender.send(message);
}

    
}