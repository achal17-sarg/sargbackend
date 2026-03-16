package org.backend.controller;

import org.backend.entity.ContactForm;
import org.backend.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sargweb/api/debug")
@CrossOrigin(origins = "*")
public class DebugController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/enquiry-debug")
    public ResponseEntity<Map<String, Object>> debugEnquiries() {
        Map<String, Object> debug = new HashMap<>();
        
        // Get all records
        List<ContactForm> allRecords = contactRepository.findAll();
        debug.put("totalRecords", allRecords.size());
        
        // Count by status
        Map<String, Long> statusCount = new HashMap<>();
        Map<String, Long> hiddenCount = new HashMap<>();
        
        for (ContactForm form : allRecords) {
            String status = form.getStatus();
            Boolean hidden = form.getHidden();
            
            statusCount.put(status == null ? "NULL" : status, 
                statusCount.getOrDefault(status == null ? "NULL" : status, 0L) + 1);
            
            hiddenCount.put(hidden == null ? "NULL" : hidden.toString(), 
                hiddenCount.getOrDefault(hidden == null ? "NULL" : hidden.toString(), 0L) + 1);
        }
        
        debug.put("statusBreakdown", statusCount);
        debug.put("hiddenBreakdown", hiddenCount);
        
        // Get recent records with full details
        List<ContactForm> recent = allRecords.stream()
            .sorted((a, b) -> b.getId().compareTo(a.getId()))
            .limit(5)
            .toList();
        
        debug.put("recentRecords", recent.stream().map(form -> {
            Map<String, Object> record = new HashMap<>();
            record.put("id", form.getId());
            record.put("name", form.getName());
            record.put("status", form.getStatus());
            record.put("hidden", form.getHidden());
            record.put("submittedAt", form.getSubmittedAt());
            return record;
        }).toList());
        
        return ResponseEntity.ok(debug);
    }
}