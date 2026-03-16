package org.backend.controller;

import org.backend.entity.CareerApplication;
import org.backend.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/career")
@CrossOrigin(origins = "*")
public class CareerController {

    @Autowired
    private CareerService careerService;

    // --- CREATE ---
    @PostMapping(value = "/apply")
    public ResponseEntity<String> submitApplication(
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "coverLetter", required = false) String coverLetter,
            @RequestParam(value = "jobId", required = false) Long jobId,
            @RequestPart(value = "resume", required = false) MultipartFile file) {
        try {
            System.out.println("=== Received Application ===");
            System.out.println("fullName: " + fullName);
            System.out.println("email: " + email);
            System.out.println("phone: " + phone);
            System.out.println("position: " + position);
            System.out.println("coverLetter: " + coverLetter);
            System.out.println("jobId: " + jobId);
            System.out.println("file: " + (file != null ? file.getOriginalFilename() : "null"));
            
            if (fullName == null || fullName.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Full name is required");
            }
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }
            if (phone == null || phone.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Phone is required");
            }
            if (position == null || position.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Position is required");
            }
            
            CareerApplication application = new CareerApplication();
            application.setCandidateName(fullName);
            application.setEmail(email);
            application.setPhone(phone);
            application.setPosition(position);
            application.setCoverLetter(coverLetter);
            application.setJobId(jobId);
            
            careerService.submitApplication(application, file);
            return ResponseEntity.status(HttpStatus.CREATED).body("Application submitted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to submit: " + e.getMessage());
        }
    }

    // --- READ (ALL) ---
    @GetMapping("/all")
    public ResponseEntity<List<CareerApplication>> getAllApplications() {
        return ResponseEntity.ok(careerService.getAllApplications());
    }

    // --- READ (SINGLE) ---
    @GetMapping("/{id}")
    public ResponseEntity<CareerApplication> getApplicationById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(careerService.getApplicationById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- UPDATE ---
    @PutMapping(value = "/update/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> updateApplication(
            @PathVariable Long id,
            @RequestPart("application") CareerApplication application,
            @RequestPart(value = "resume", required = false) MultipartFile file) {
        try {
            careerService.updateApplication(id, application, file);
            return ResponseEntity.ok("Application updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Update failed: " + e.getMessage());
        }
    }

    // --- DELETE ---
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable Long id) {
        try {
            careerService.deleteApplication(id);
            return ResponseEntity.ok("Application deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed");
        }
    }
}