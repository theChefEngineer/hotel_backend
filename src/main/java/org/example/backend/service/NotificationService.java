package org.example.backend.service;

import org.example.backend.exception.NotificationNotFoundException;
import org.example.backend.model.Notification;
import org.example.backend.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing notifications.
 */
@Service
@Transactional
public class NotificationService {
    private final NotificationRepository notificationRepository;

    /**
     * Constructs a new NotificationService with the specified NotificationRepository.
     *
     * @param notificationRepository the repository to handle notification data operations
     */
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Retrieves all notifications.
     *
     * @return a list of all Notification objects
     */
    @Transactional(readOnly = true)
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    /**
     * Creates a new notification.
     *
     * @param notification the Notification object to be created
     * @return the created Notification object
     */
    public Notification createNotification(Notification notification) {
        LocalDateTime now = LocalDateTime.now();
        notification.setCreationDate(now);
        notification.setLastModificationDate(now);
        return notificationRepository.save(notification);
    }

    /**
     * Updates an existing notification.
     *
     * @param id                  the ID of the notification to be updated
     * @param updatedNotification the updated Notification object
     * @return the updated Notification object
     * @throws NotificationNotFoundException if the notification with the given ID is not found
     */
    public Notification updateNotification(Long id, Notification updatedNotification) {
        return notificationRepository.findById(id)
                .map(notification -> {
                    notification.setName(updatedNotification.getName());
                    notification.setMessage(updatedNotification.getMessage());
                    notification.setLastModificationDate(LocalDateTime.now());
                    return notificationRepository.save(notification);
                })
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found with id: " + id));
    }

    /**
     * Deletes a notification.
     *
     * @param id the ID of the notification to be deleted
     * @throws NotificationNotFoundException if the notification with the given ID is not found
     */
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new NotificationNotFoundException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }
}
