package org.example.backend.repository;



import org.example.backend.model.NotificationPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NotificationPerformanceRepository extends JpaRepository<NotificationPerformance, Long> {
    List<NotificationPerformance> findByNotificationIdAndDateBetween(Long notificationId, LocalDate startDate, LocalDate endDate);
}
