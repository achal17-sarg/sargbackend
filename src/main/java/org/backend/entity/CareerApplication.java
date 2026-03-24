package org.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    
    @Column(name = "full_name", nullable = false)
    @JsonProperty("fullName")
    private String candidateName;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String position;
    
    @Column(columnDefinition = "TEXT")
    private String coverLetter;
    
    private String resume;
    
    @Column(name = "job_id")
    private Long jobId;
    
    @Column(nullable = false)
    private String status = "Pending";
    
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleted = false;
    
    @Column(nullable = false)
    private LocalDateTime appliedAt;
    
    @PrePersist
    protected void onCreate() {
        this.appliedAt = LocalDateTime.now();
        if (this.status == null || this.status.isEmpty()) {
            this.status = "Pending";
        }
        if (this.deleted == null) {
            this.deleted = false;
        }
    }
}