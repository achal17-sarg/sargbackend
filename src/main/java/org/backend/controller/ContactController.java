package org.backend.controller;

import org.backend.entity.ContactForm;
import org.backend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*") // Allows connection from your React/Angular frontend
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitForm(@RequestBody ContactForm contactForm) {
        try {
            contactService.submitContact(contactForm);
            return ResponseEntity.ok("Contact request submitted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send request: " + e.getMessage());
        }
    }
}