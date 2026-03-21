package org.backend.controller;

import org.backend.entity.JourneyInquiry;
import org.backend.repository.JourneyInquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sargweb/api/journey")
public class JourneyAdminController {

    @Autowired
    private JourneyInquiryRepository journeyRepository;

    @GetMapping("/all")
    public ResponseEntity<List<JourneyInquiry>> getAllJourneyInquiries() {
        List<JourneyInquiry> inquiries = journeyRepository.findAll();
        return ResponseEntity.ok(inquiries);
    }

    @GetMapping("/new-enquiries")
    public ResponseEntity<Map<String, Object>> getNewJourneyEnquiries() {
        List<JourneyInquiry> inquiries = journeyRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("count", inquiries.size());
        response.put("enquiries", inquiries);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJourneyInquiryById(@PathVariable Long id) {
        return journeyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteJourneyInquiry(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            if (journeyRepository.existsById(id)) {
                journeyRepository.deleteById(id);
                response.put("status", "success");
                response.put("message", "Journey inquiry deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Journey inquiry not found");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to delete: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping("/hide/{id}")
    public ResponseEntity<Map<String, String>> hideJourneyInquiry(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Journey inquiry marked as hidden");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getJourneyInquiryCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("count", journeyRepository.count());
        return ResponseEntity.ok(response);
    }
}
