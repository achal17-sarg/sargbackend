package org.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
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
    
   // Change this line in ContactForm.java
   @JsonIgnore
@Column(name = "submitted_at", nullable = false, updatable = false)
private LocalDateTime submittedAt = LocalDateTime.now();
@PrePersist
    protected void onCreate() {
        this.submittedAt = LocalDateTime.now();
    }
}

