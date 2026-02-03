package org.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "career_applications")
public class CareerApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String position;
    
    @Column(columnDefinition = "TEXT")
    private String coverLetter;
    
    private String resumeFileName;
    
    @Column(nullable = false)
    private LocalDateTime appliedAt = LocalDateTime.now();
}