
package com.example.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.dto.NotificationMessageCountDTO;
import com.example.dto.NotificationMessageStatusCountDTO;
import com.example.dto.NotificationStatisticsDTO;
import com.example.entity.Notification;
import com.example.entity.User;
import com.example.repository.NotificationRepository;
import com.example.repository.UserRepository;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationController(
            NotificationRepository notificationRepository,
            UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));
    }

    private Notification getOwnedNotification(Long id, Long userId) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Notification not found"));

        if (notification.getUser() == null
                || !notification.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access this notification");
        }

        return notification;
    }

    @PostMapping
    public Notification createNotification(
            @RequestParam Long userId,
            @RequestParam String message) {
        User user = getUser(userId);

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setRead(false);

        return notificationRepository.save(notification);
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(
            @PathVariable Long userId) {
        getUser(userId);
        return notificationRepository.findByUserIdOrderByIdDesc(userId);
    }

    @GetMapping("/user/{userId}/unread")
    public List<Notification> getUnreadNotifications(
            @PathVariable Long userId) {
        getUser(userId);
        return notificationRepository.findByUserIdAndReadStatusFalse(userId);
    }

    @PutMapping("/user/{userId}/read-all")
    public String markAllAsRead(@PathVariable Long userId) {
        List<Notification> notifications =
                notificationRepository.findByUserId(userId);

        for (Notification notification : notifications) {
            notification.setRead(true);
        }

        notificationRepository.saveAll(notifications);
        return "All notifications marked as read";
    }

    @PutMapping("/{id}/read")
    public Notification markAsRead(
            @PathVariable Long id,
            @RequestParam Long userId) {
        Notification notification = getOwnedNotification(id, userId);
        notification.setRead(true);
        return notificationRepository.save(notification);
    }

    @DeleteMapping("/{id}")
    public String deleteNotification(
            @PathVariable Long id,
            @RequestParam Long userId) {
        Notification notification = getOwnedNotification(id, userId);
        notificationRepository.delete(notification);
        return "Notification deleted successfully";
    }

    @GetMapping("/user/{userId}/unread-count")
    public long getUnreadNotificationCount(@PathVariable Long userId) {
        getUser(userId);
        return notificationRepository.countByUserIdAndReadStatusFalse(userId);
    }

    @GetMapping("/user/{userId}/search")
    public List<Notification> searchNotifications(
            @PathVariable Long userId,
            @RequestParam String message) {
        getUser(userId);
        return notificationRepository
                .findByUserIdAndMessageContainingIgnoreCase(userId, message);
    }

    @GetMapping("/user/{userId}/page")
    public Page<Notification> getNotificationPage(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        getUser(userId);

        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page must be >= 0 and size must be between 1 and 100");
        }

        return notificationRepository.findByUserId(
                userId, PageRequest.of(page, size));
    }

    @GetMapping("/user/{userId}/sorted")
    public List<Notification> getSortedNotifications(
            @PathVariable Long userId) {
        getUser(userId);
        return notificationRepository.findByUserIdOrderByIdDesc(userId);
    }

    @GetMapping("/user/{userId}/filter")
    public List<Notification> filterNotifications(
            @PathVariable Long userId,
            @RequestParam boolean readStatus) {
        getUser(userId);
        return notificationRepository
                .findByUserIdAndReadStatusOrderByIdDesc(userId, readStatus);
    }

    @DeleteMapping("/user/{userId}/all")
    public String deleteAllNotifications(@PathVariable Long userId) {
        getUser(userId);
        notificationRepository.deleteByUserId(userId);
        return "All notifications deleted successfully";
    }

    @GetMapping("/user/{userId}/count-by-type")
    public long countNotificationsByType(
            @PathVariable Long userId,
            @RequestParam String message) {
        getUser(userId);
        return notificationRepository
                .countByUserIdAndMessageContainingIgnoreCase(userId, message);
    }

    @GetMapping("/user/{userId}/count")
    public long getTotalNotificationCount(@PathVariable Long userId) {
        getUser(userId);
        return notificationRepository.countByUserId(userId);
    }

    @GetMapping("/statistics/{userId}")
    public NotificationStatisticsDTO getNotificationStatistics(
            @PathVariable Long userId) {
        getUser(userId);

        long total = notificationRepository.countByUserId(userId);
        long read = notificationRepository.countByUserIdAndReadStatus(userId, true);
        long unread = notificationRepository.countByUserIdAndReadStatusFalse(userId);

        return new NotificationStatisticsDTO(total, read, unread);
    }

    @GetMapping("/message-count/{userId}")
    public NotificationMessageCountDTO getNotificationMessageCount(
            @PathVariable Long userId,
            @RequestParam String keyword) {
        getUser(userId);

        long count = notificationRepository
                .countByUserIdAndMessageContainingIgnoreCase(userId, keyword);

        return new NotificationMessageCountDTO(keyword, count);
    }

    @GetMapping("/message-status-count/{userId}")
    public NotificationMessageStatusCountDTO getNotificationMessageStatusCount(
            @PathVariable Long userId,
            @RequestParam String keyword,
            @RequestParam boolean read) {
        getUser(userId);

        long count = notificationRepository
                .countByUserIdAndMessageContainingIgnoreCaseAndReadStatus(
                        userId, keyword, read);

        return new NotificationMessageStatusCountDTO(keyword, read, count);
    }
}
