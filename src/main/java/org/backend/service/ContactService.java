package org.backend.service;

import org.backend.entity.ContactForm;
import org.backend.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
        message.setFrom("achal.shinde@sargsoftech.com");
        message.setTo(hrEmail);
        message.setSubject("New Contact Form - " + contact.getSubject());
        message.setText("New contact form submission:\n\n" +
                "Name: " + contact.getName() + "\n" +
                "Email: " + contact.getEmail() + "\n" +
                "Phone: " + contact.getPhone() + "\n" +
                "Subject: " + contact.getSubject() + "\n" +
                "Message: " + contact.getMessage());
        mailSender.send(message);
    }
}