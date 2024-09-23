package org.example.backend.controller;

import org.example.backend.model.NotificationPerformance;
import org.example.backend.service.PerformanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing performance metrics.
 */
@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    /**
     * Constructs a new PerformanceController with the specified PerformanceService.
     *
     * @param performanceService the service to handle performance-related operations
     */
    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    /**
     * Generates performance metrics.
     *
     * @return ResponseEntity with no content if successful
     */
    @PostMapping("/generate")
    public ResponseEntity<Void> generatePerformanceMetrics() {
        performanceService.generatePerformanceMetrics();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Retrieves performance metrics for a specified date range.
     *
     * @param startDate the start date of the range (inclusive)
     * @param endDate the end date of the range (inclusive)
     * @return a list of NotificationPerformance objects within the specified date range
     */
    @GetMapping
    public ResponseEntity<List<NotificationPerformance>> getPerformanceMetrics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<NotificationPerformance> metrics = performanceService.getPerformanceMetrics(startDate, endDate);
        return ResponseEntity.ok(metrics);
    }
}
