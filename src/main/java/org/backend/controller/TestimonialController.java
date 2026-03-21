package org.backend.controller;

import org.backend.entity.Testimonial;
import org.backend.repository.TestimonialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/testimonials")
public class TestimonialController {

    @Autowired
    private TestimonialRepository testimonialRepository;

    private final String uploadDir = "uploads/logos/";

    @GetMapping
    public ResponseEntity<List<Testimonial>> getAllTestimonials() {
        return ResponseEntity.ok(testimonialRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createTestimonial(
            @RequestParam("name") String name,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "staticColor", defaultValue = "#f1f5f9") String staticColor,
            @RequestParam(value = "customWidth", defaultValue = "100%") String customWidth,
            @RequestParam(value = "customHeight", defaultValue = "100%") String customHeight,
            @RequestParam(value = "logo", required = false) MultipartFile logoFile) {

        try {
            Testimonial testimonial = new Testimonial();
            testimonial.setName(name);
            testimonial.setRole(role);
            testimonial.setText(text);
            testimonial.setStaticColor(staticColor);
            testimonial.setCustomWidth(customWidth);
            testimonial.setCustomHeight(customHeight);

            if (logoFile != null && !logoFile.isEmpty()) {
                String logoUrl = saveFile(logoFile);
                testimonial.setLogo(logoUrl);
            }

            return ResponseEntity.ok(testimonialRepository.save(testimonial));
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Failed to create testimonial: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTestimonial(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "staticColor", defaultValue = "#f1f5f9") String staticColor,
            @RequestParam(value = "customWidth", defaultValue = "100%") String customWidth,
            @RequestParam(value = "customHeight", defaultValue = "100%") String customHeight,
            @RequestParam(value = "logo", required = false) MultipartFile logoFile) {

        try {
            Testimonial testimonial = testimonialRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Testimonial not found"));

            testimonial.setName(name);
            testimonial.setRole(role);
            testimonial.setText(text);
            testimonial.setStaticColor(staticColor);
            testimonial.setCustomWidth(customWidth);
            testimonial.setCustomHeight(customHeight);

            if (logoFile != null && !logoFile.isEmpty()) {
                String logoUrl = saveFile(logoFile);
                testimonial.setLogo(logoUrl);
            }

            return ResponseEntity.ok(testimonialRepository.save(testimonial));
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Failed to update: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTestimonial(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        testimonialRepository.deleteById(id);
        response.put("message", "Deleted successfully");
        return ResponseEntity.ok(response);
    }

    private String saveFile(MultipartFile file) throws IOException {
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.write(filePath, file.getBytes());

        return "http://localhost:8090/uploads/logos/" + fileName;
    }
}
