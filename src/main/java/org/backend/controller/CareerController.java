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
@RequestMapping("/sargweb/api/career")
@CrossOrigin(origins = "*")
public class CareerController {

    @Autowired
    private CareerService careerService;

    // --- CREATE ---
    @PostMapping(value = "/apply", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> submitApplication(
            @RequestPart("application") CareerApplication application,
            @RequestPart("resume") MultipartFile file) {
        try {
            careerService.submitApplication(application, file);
            return ResponseEntity.status(HttpStatus.CREATED).body("Application submitted successfully!");
        } catch (Exception e) {
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