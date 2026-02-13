package org.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // This generates getters/setters automatically
@Table(name = "demo_requests")
public class DemoRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;        // Business Owner, Accountant, etc.
    private String project;     // CRM, ERP, LMS
    private String plan;        // Basic Plan
    private String fullName;
    private String phoneNumber;
    private String email;
    private String pincode;
    
    @Column(columnDefinition = "TEXT")
    private String message;     // "How can we help?"
}