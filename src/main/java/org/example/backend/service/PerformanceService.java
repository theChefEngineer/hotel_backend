package org.example.backend.service;

import org.example.backend.model.Notification;
import org.example.backend.model.NotificationPerformance;
import org.example.backend.repository.NotificationPerformanceRepository;
import org.example.backend.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service class for managing performance metrics of notifications.
 */
@Service
@Transactional
public class PerformanceService {
    private static final int METRICS_GENERATION_PERIOD_MONTHS = 3;
    private static final int WEEKDAY_IMPRESSIONS_MAX = 100;
    private static final int WEEKEND_IMPRESSIONS_ADDITIONAL = 50;
    private static final int RATE_SCALE = 4;

    private final NotificationRepository notificationRepository;
    private final NotificationPerformanceRepository performanceRepository;
    private final Random random;

    public PerformanceService(NotificationRepository notificationRepository,
                              NotificationPerformanceRepository performanceRepository) {
        this.notificationRepository = notificationRepository;
        this.performanceRepository = performanceRepository;
        this.random = new Random();
    }

    /**
     * Generates performance metrics for all notifications over the last three months.
     */
    @Transactional
    public void generatePerformanceMetrics() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(METRICS_GENERATION_PERIOD_MONTHS);
        List<Notification> notifications = notificationRepository.findAll();
        List<NotificationPerformance> allPerformances = new ArrayList<>();

        for (Notification notification : notifications) {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                allPerformances.add(generateDailyMetrics(notification, date));
            }
        }

        performanceRepository.saveAll(allPerformances);
    }

    private NotificationPerformance generateDailyMetrics(Notification notification, LocalDate date) {
        int impressions = generateImpressions(date);
        int clicks = random.nextInt(impressions + 1);
        int conversions = random.nextInt(clicks + 1);

        BigDecimal ctr = calculateRate(clicks, impressions);
        BigDecimal cvr = calculateRate(conversions, impressions);

        return new NotificationPerformance(null, notification, date, impressions, clicks, conversions, ctr, cvr);
    }

    private int generateImpressions(LocalDate date) {
        return date.getDayOfWeek().getValue() <= 5 ?
                random.nextInt(WEEKDAY_IMPRESSIONS_MAX + 1) :
                random.nextInt(WEEKDAY_IMPRESSIONS_MAX + 1) + WEEKEND_IMPRESSIONS_ADDITIONAL;
    }

    private BigDecimal calculateRate(int numerator, int denominator) {
        if (denominator == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(numerator)
                .divide(BigDecimal.valueOf(denominator), RATE_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Retrieves performance metrics for all notifications within a date range.
     *
     * @param startDate the start date of the range (inclusive)
     * @param endDate   the end date of the range (inclusive)
     * @return a list of NotificationPerformance entities within the specified date range
     */
    @Transactional(readOnly = true)
    public List<NotificationPerformance> getPerformanceMetrics(LocalDate startDate, LocalDate endDate) {
        List<Notification> notifications = notificationRepository.findAll();
        List<NotificationPerformance> allPerformances = new ArrayList<>();

        for (Notification notification : notifications) {
            List<NotificationPerformance> performances = performanceRepository.findByNotificationIdAndDateBetween(
                    notification.getId(), startDate, endDate);
            allPerformances.addAll(performances);
        }

        return allPerformances;
    }
}
