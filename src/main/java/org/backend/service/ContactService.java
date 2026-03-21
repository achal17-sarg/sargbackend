package org.backend.service;

import org.backend.entity.ContactForm;
import org.backend.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {
    
    @Autowired
    private ContactRepository repository;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${app.hr.email}")
    private String hrEmail;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${app.company.name:Sarg Softech}")
    private String companyName;
    
    public void submitContact(ContactForm contactForm) {
        repository.save(contactForm);
        try {
            sendContactEmailToHR(contactForm);
            sendConfirmationToClient(contactForm);
        } catch (Exception e) {
            System.err.println("Email sending failed but data saved: " + e.getMessage());
        }
    }

    private void sendContactEmailToHR(ContactForm contact) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo("hr@sargsoftech.com");
        message.setSubject("New Enquiry Received - " + contact.getBusiness_nm());
        message.setText(
            "New contact form submission:\n\n" +
            "Name: " + contact.getName() + "\n" +
            "Email: " + contact.getEmail() + "\n" +
            "Phone: " + contact.getPhoneno() + "\n" +
            "Business: " + contact.getBusiness_nm() + "\n" +
            "Message: " + contact.getMessage()
        );
        mailSender.send(message);
    }

    private void sendConfirmationToClient(ContactForm contact) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(contact.getEmail());
        message.setSubject("We Received Your Enquiry - " + companyName);
        message.setText(
            "Dear " + contact.getName() + ",\n\n" +
            "Thank you for contacting " + companyName + ".\n\n" +
            "We have received your enquiry and our team will get back to you shortly.\n\n" +
            "Best Regards,\n" +
            companyName + " Team"
        );
        mailSender.send(message);
    }
    
    public List<ContactForm> getAllContacts() {
        return repository.findAll();
    }
    public List<ContactForm> getAllIncludingHidden() {
    return repository.findAll();
}
public List<ContactForm> getNewEnquiries() {
    // Use simple repository method
    try {
        return repository.findByStatusAndHiddenFalse("New");
    } catch (Exception e) {
        System.err.println("Repository query failed: " + e.getMessage());
        // Fallback to manual filtering
        return repository.findAll()
                .stream()
                .filter(form -> {
                    Boolean hidden = form.getHidden();
                    String status = form.getStatus();
                    return (hidden == null || !hidden) && "New".equals(status);
                })
                .collect(Collectors.toList());
    }
}
    
    // Inside ContactService.java

public List<ContactForm> getAllActiveContacts() {
    // Use simple repository method
    try {
        return repository.findByHiddenFalse();
    } catch (Exception e) {
        System.err.println("Repository query failed: " + e.getMessage());
        return repository.findAll()
                .stream()
                .filter(form -> {
                    Boolean hidden = form.getHidden();
                    return hidden == null || !hidden;
                })
                .collect(Collectors.toList());
    }
}
    public void hideEnquiry(Long id) {
        ContactForm enquiry = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Enquiry not found"));
        
        enquiry.setHidden(true);
        repository.save(enquiry);
    }
}