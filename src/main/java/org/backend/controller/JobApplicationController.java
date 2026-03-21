package org.backend.controller;

import org.backend.entity.CareerApplication;
import org.backend.repository.CareerApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sargweb/api/job-applications")
public class JobApplicationController {

    @Autowired
    private CareerApplicationRepository applicationRepository;

    @GetMapping("/job/{jobId}")
    public ResponseEntity<?> getApplicationsByJobId(@PathVariable Long jobId) {
        try {
            System.out.println("Fetching applications for job ID: " + jobId);
            List<CareerApplication> applications = applicationRepository.findByJobId(jobId);
            System.out.println("Found " + applications.size() + " applications");
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            System.err.println("Error fetching applications: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CareerApplication>> getAllApplications() {
        System.out.println("=== Fetching all applications ===");
        List<CareerApplication> applications = applicationRepository.findAll();
        System.out.println("Total applications found: " + applications.size());
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<?> getApplicationsByPosition(@PathVariable String position) {
        try {
            String trimmedPosition = position.trim();
            System.out.println("Fetching applications for position: '" + trimmedPosition + "'");
            List<CareerApplication> applications = applicationRepository.findByPositionIgnoreCase(trimmedPosition);
            System.out.println("Found " + applications.size() + " applications for position: " + trimmedPosition);
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            System.err.println("Error fetching applications: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable Long id) {
        return applicationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, String>> updateApplicationStatus(
            @PathVariable Long id, 
            @RequestBody Map<String, String> statusUpdate) {
        Map<String, String> response = new HashMap<>();
        try {
            CareerApplication application = applicationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Application not found"));
            
            application.setStatus(statusUpdate.get("status"));
            applicationRepository.save(application);
            
            response.put("status", "success");
            response.put("message", "Status updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteApplication(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            if (applicationRepository.existsById(id)) {
                applicationRepository.deleteById(id);
                response.put("status", "success");
                response.put("message", "Application deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Application not found");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to delete: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getApplicationCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("count", applicationRepository.count());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/fix-data")
    public ResponseEntity<Map<String, String>> fixExistingData() {
        Map<String, String> response = new HashMap<>();
        try {
            List<CareerApplication> all = applicationRepository.findAll();
            int fixed = 0;
            for (CareerApplication app : all) {
                boolean updated = false;
                if (app.getCandidateName() == null || app.getCandidateName().isEmpty()) {
                    app.setCandidateName("Unknown Candidate");
                    updated = true;
                }
                if (app.getStatus() == null || app.getStatus().isEmpty()) {
                    app.setStatus("Pending");
                    updated = true;
                }
                // Link MERN Stack applications to job ID 8
                if (app.getJobId() == null && "MERN Stack Developer".equalsIgnoreCase(app.getPosition())) {
                    app.setJobId(8L);
                    updated = true;
                }
                if (updated) {
                    applicationRepository.save(app);
                    fixed++;
                }
            }
            response.put("status", "success");
            response.put("message", "Fixed " + fixed + " applications");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
