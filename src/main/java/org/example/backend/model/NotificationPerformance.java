package org.example.backend.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification_performances")
public class NotificationPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int impressions;

    @Column(nullable = false)
    private int clicks;

    @Column(nullable = false)
    private int conversions;

    @Column(precision = 6, scale = 4, nullable = false)
    private BigDecimal ctr;

    @Column(precision = 6, scale = 4, nullable = false)
    private BigDecimal cvr;


    // Getters and setters
}
