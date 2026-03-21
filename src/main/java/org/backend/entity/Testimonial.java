package org.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "testimonials")
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String role;

    @Column(columnDefinition = "TEXT")
    private String text;

    private String logo;

    private String staticColor = "#f1f5f9";

    private String customWidth = "100%";

    private String customHeight = "100%";

    private String opacity = "1";
}
