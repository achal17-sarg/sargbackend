package org.backend.controller;
import org.backend.entity.DemoRequest;
import org.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("sargweb/api/demo")
public class DemoController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitDemo(@RequestBody DemoRequest demoRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            // Send the email notification
            emailService.sendDemoNotification(demoRequest); 
            
            // Return JSON so Angular's HttpClient.post().subscribe() succeeds
            response.put("status", "success");
            response.put("message", "Demo request processed successfully!");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to process demo request: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}