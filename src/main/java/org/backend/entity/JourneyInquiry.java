package org.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "journey_inquiries")
public class JourneyInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String emailAddress;

    private String mobileNumber;

    @Column(nullable = false)
    private String expertiseNeeded;

    @Column(columnDefinition = "TEXT")
    private String projectGoals;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}