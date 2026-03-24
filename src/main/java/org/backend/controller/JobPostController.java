package org.backend.controller;

import org.backend.entity.JobPost;
import org.backend.repository.JobPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/sargweb/api/jobs")
public class JobPostController {

    @Autowired
    private JobPostRepository jobPostRepository;

    @GetMapping("/all")
    public ResponseEntity<List<JobPost>> getAllJobs() {
        return ResponseEntity.ok(jobPostRepository.findByDeletedFalse());
    }

    @GetMapping("/archived")
    public ResponseEntity<List<JobPost>> getArchivedJobs() {
        return ResponseEntity.ok(jobPostRepository.findByDeletedTrue());
    }

    @PostMapping("/create")
    public ResponseEntity<JobPost> createJob(@RequestBody JobPost job) {
        job.setDeleted(false);
        return ResponseEntity.ok(jobPostRepository.save(job));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JobPost> updateJob(@PathVariable Long id, @RequestBody JobPost job) {
        job.setId(id);
        return ResponseEntity.ok(jobPostRepository.save(job));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteJob(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        return jobPostRepository.findById(id).map(job -> {
            job.setDeleted(true);
            jobPostRepository.save(job);
            response.put("status", "success");
            response.put("message", "Job archived successfully");
            return ResponseEntity.ok(response);
        }).orElseGet(() -> {
            response.put("status", "error");
            response.put("message", "Job not found");
            return ResponseEntity.status(404).body(response);
        });
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<Map<String, String>> restoreJob(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        return jobPostRepository.findById(id).map(job -> {
            job.setDeleted(false);
            jobPostRepository.save(job);
            response.put("status", "success");
            response.put("message", "Job restored successfully");
            return ResponseEntity.ok(response);
        }).orElseGet(() -> {
            response.put("status", "error");
            response.put("message", "Job not found");
            return ResponseEntity.status(404).body(response);
        });
    }
}
