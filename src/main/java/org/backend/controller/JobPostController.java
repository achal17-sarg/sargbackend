package org.backend.controller;

import org.backend.entity.JobPost;
import org.backend.repository.JobPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sargweb/api/jobs")
@CrossOrigin(origins = "*")
public class JobPostController {

    @Autowired
    private JobPostRepository jobPostRepository;

    @GetMapping("/all")
    public ResponseEntity<List<JobPost>> getAllJobs() {
        return ResponseEntity.ok(jobPostRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<JobPost> createJob(@RequestBody JobPost job) {
        JobPost savedJob = jobPostRepository.save(job);
        return ResponseEntity.ok(savedJob);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JobPost> updateJob(@PathVariable Long id, @RequestBody JobPost job) {
        job.setId(id);
        JobPost updatedJob = jobPostRepository.save(job);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobPostRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
