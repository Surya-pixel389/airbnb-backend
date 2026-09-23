package com.aribnb.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name  = "Property")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 10000)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private BigDecimal pricePerNight;

    private Integer bedroom;
    private Integer bathroom;
    private Integer maxGuest;

    @ElementCollection
    private List<String> amenities;

    @ElementCollection
    private List<String> imageUrl;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    private user host;

    @Column(columnDefinition = "Decimal(3,2) Default 0.0")
    private Double averageRating;

    private Integer totalReviews;

    @Column(columnDefinition = "boolean default true")
    private Boolean available = true;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", nullable = false, updatable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }


}
