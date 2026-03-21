package org.backend.controller;

import org.backend.entity.ContactForm;
import org.backend.repository.ContactRepository;
import org.backend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("sargweb/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitForm(@RequestBody ContactForm contactForm) {
        Map<String, String> response = new HashMap<>();
        try {
            contactService.submitContact(contactForm);
            response.put("status", "success");
            response.put("message", "Contact request submitted successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to send request: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    @PutMapping("/hide/{id}")
public ResponseEntity<Map<String, String>> hideEnquiry(@PathVariable Long id) {
    Map<String, String> response = new HashMap<>();
    try {
        contactService.hideEnquiry(id);
        response.put("status", "success");
        response.put("message", "Enquiry marked as hidden successfully");
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        response.put("status", "error");
        response.put("message", "Error hiding enquiry: " + e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }
}
@GetMapping("/all")
public ResponseEntity<List<ContactForm>> getAllEnquiries() {
    // Use the service to get the filtered list
    List<ContactForm> enquiries = contactService.getAllActiveContacts();
    return ResponseEntity.ok(enquiries);
}
    @GetMapping("/new-enquiries")
    public ResponseEntity<Map<String, Object>> getNewEnquiries() {
        List<ContactForm> newEnquiries = contactService.getNewEnquiries();
        Map<String, Object> response = new HashMap<>();
        response.put("count", newEnquiries.size());
        response.put("enquiries", newEnquiries);
        return ResponseEntity.ok(response);
    }

@GetMapping("/all-including-hidden")
public ResponseEntity<List<ContactForm>> getAllEnquiriesIncludingHidden() {
    return ResponseEntity.ok(contactService.getAllIncludingHidden());
}

@PostMapping("/fix-status")
public ResponseEntity<Map<String, String>> fixEmptyStatus() {
    Map<String, String> response = new HashMap<>();
    try {
        List<ContactForm> allForms = contactRepository.findAll();
        int fixed = 0;
        for (ContactForm form : allForms) {
            if (form.getStatus() == null || form.getStatus().isEmpty()) {
                form.setStatus("New");
                contactRepository.save(form);
                fixed++;
            }
        }
        response.put("status", "success");
        response.put("message", "Fixed " + fixed + " records with empty status");
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        response.put("status", "error");
        response.put("message", "Error: " + e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }
}

}