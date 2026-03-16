package org.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "client_designation")
    private String clientDesignation;

    @Column(name = "client_company")
    private String clientCompany;

    @Column(name = "client_website")
    private String clientWebsite;

    @Column(name = "client_logo")
    private String clientLogo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String feedback;

    @Column(nullable = false)
    private int rating = 5;

    @Column(name = "is_visible", nullable = false)
    private boolean isVisible = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
