package org.backend.service;

import org.backend.entity.CareerApplication;
import org.backend.repository.CareerApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CareerService {
@Autowired
    private CareerApplicationRepository repository;

    @Autowired
    private EmailService emailService;

    // --- CREATE ---
    public CareerApplication submitApplication(CareerApplication application, MultipartFile file) {
        // 1. Save the application data to the database
        CareerApplication savedApplication = repository.save(application);

        // 2. Trigger Emails after successful database save
        try {
            // Send email notification to you (HR/Admin)
            emailService.sendApplicationNotificationToHR(savedApplication, file);
            
            // Send confirmation email to the Applicant
            emailService.sendConfirmationToCandidate(savedApplication);
        } catch (Exception e) {
            // We use a try-catch so that if the email server is down, 
            // the user doesn't get a 500 error after their data is already saved.
            System.err.println("Error sending emails: " + e.getMessage());
        }

        return savedApplication;
    }

    // --- READ (ALL) ---
    public List<CareerApplication> getAllApplications() {
        return repository.findAll();
    }

    // --- READ (SINGLE) ---
    public CareerApplication getApplicationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
    }

    // --- UPDATE ---
    public CareerApplication updateApplication(Long id, CareerApplication newDetails, MultipartFile file) {
        CareerApplication existing = getApplicationById(id);
        
        // Update fields
        existing.setFullName(newDetails.getFullName());
        existing.setEmail(newDetails.getEmail());
        existing.setPhone(newDetails.getPhone());
        existing.setPosition(newDetails.getPosition());
        existing.setCoverLetter(newDetails.getCoverLetter());
        
        // Logic for file update could go here if needed
        
        return repository.save(existing);
    }

    // --- DELETE ---
    public void deleteApplication(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Application not found with id: " + id);
        }
        repository.deleteById(id);
    }
}