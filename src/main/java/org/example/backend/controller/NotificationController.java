package org.example.backend.controller;

import org.example.backend.model.Notification;
import org.example.backend.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing notifications.
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * Constructs a new NotificationController with the specified NotificationService.
     *
     * @param notificationService the service to handle notification-related operations
     */
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Retrieves all notifications.
     *
     * @return a list of all Notification objects
     */
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    /**
     * Creates a new notification.
     *
     * @param notification the Notification object to be created
     * @return the created Notification object
     */
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification createdNotification = notificationService.createNotification(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
    }

    /**
     * Updates an existing notification.
     *
     * @param id           the ID of the notification to be updated
     * @param notification the updated Notification object
     * @return the updated Notification object
     */
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification notification) {
        Notification updatedNotification = notificationService.updateNotification(id, notification);
        return ResponseEntity.ok(updatedNotification);
    }

    /**
     * Deletes a notification.
     *
     * @param id the ID of the notification to be deleted
     * @return ResponseEntity with no content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
