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
    public CareerApplication submitApplication(CareerApplication application, MultipartFile file) throws Exception{
        System.out.println("=== Submitting Application ===");
        System.out.println("Candidate Name: " + application.getCandidateName());
        System.out.println("Email: " + application.getEmail());
        System.out.println("Position: " + application.getPosition());
        System.out.println("Job ID: " + application.getJobId());
        
        // 1. Save the application data to the database
        CareerApplication savedApplication = repository.save(application);
        System.out.println("Application saved with ID: " + savedApplication.getId());

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
        existing.setCandidateName(newDetails.getCandidateName());
        existing.setEmail(newDetails.getEmail());
        existing.setPhone(newDetails.getPhone());
        existing.setPosition(newDetails.getPosition());
        existing.setCoverLetter(newDetails.getCoverLetter());
        if (newDetails.getJobId() != null) {
            existing.setJobId(newDetails.getJobId());
        }
        if (newDetails.getStatus() != null) {
            existing.setStatus(newDetails.getStatus());
        }
        
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