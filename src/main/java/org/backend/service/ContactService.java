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
    
    public void submitContact(ContactForm contactForm) {
        repository.save(contactForm);
        sendContactEmailToHR(contactForm);
    }
    
    private void sendContactEmailToHR(ContactForm contact) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@sargsoftech.com");
        message.setTo(hrEmail);
        message.setSubject("New Enquiry Received  - " + contact.getBusiness_nm());
        message.setText("New contact form submission:\n\n" +
                "Name: " + contact.getName() + "\n" +
                "Email: " + contact.getEmail() + "\n" +
                "Phone: " + contact.getPhoneno() + "\n" +
                "Business: " + contact.getBusiness_nm() + "\n" +
                "Message: " + contact.getMessage());
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