package org.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "job_posts")
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String jobTitle;
    
    @Column(columnDefinition = "TEXT")
    private String jobDescription;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private String experience;
    
    @Column(nullable = false)
    private String salary;
    
    @Column(nullable = false)
    private String applyButton;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
