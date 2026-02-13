package org.backend.controller;

import org.backend.entity.ContactForm;
import org.backend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("sargweb/api/contact")
@CrossOrigin(origins = "*") 
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitForm(@RequestBody ContactForm contactForm) {
        Map<String, String> response = new HashMap<>();
        try {
            contactService.submitContact(contactForm);
            
            // Returning a Map ensures Spring Boot converts it to JSON: {"message": "..."}
            response.put("status", "success");
            response.put("message", "Contact request submitted successfully!");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to send request: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}