package org.backend.service;

import org.backend.entity.DemoRequest;
import org.backend.repository.DemoRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    @Autowired
    private DemoRequestRepository repository;

    @Autowired
    private EmailService emailService; // Reusing your existing EmailService

    public DemoRequest saveDemoRequest(DemoRequest request) {
        // 1. Save to Database
        DemoRequest saved = repository.save(request);
        
        // 2. Send Notification Email (Optional)
        try {
            emailService.sendSimpleMessage(
                "info@sargsoftech.com", 
                "New Demo Request: " + saved.getProject(),
                "Customer " + saved.getFullName() + " requested a demo for " + saved.getProject()
            );
        } catch (Exception e) {
            System.out.println("Email failed, but data was saved.");
        }
        
        return saved;
    }
}