package org.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact_forms")
public class ContactForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phoneno;
    
    @Column(nullable = false)
    private String business_nm;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "hidden", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean hidden = false; 
    
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'New'")
    private String status = "New";
    
    @JsonIgnore
    @Column(name = "submitted_at", nullable = false, updatable = false)
    private LocalDateTime submittedAt;
    
    @PrePersist
    protected void onCreate() {
        this.submittedAt = LocalDateTime.now();
        if (this.hidden == null) {
            this.hidden = false;
        }
        if (this.status == null || this.status.isEmpty()) {
            this.status = "New";
        }
        if (this.business_nm == null) {
            this.business_nm = "";
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        if (this.status == null || this.status.isEmpty()) {
            this.status = "New";
        }
    }
}
