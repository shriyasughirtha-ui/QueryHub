package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long userId);
    List<Notification> findByUserIdAndReadStatusFalse(Long userId);
    long countByUserIdAndReadStatusFalse(Long userId);
    List<Notification> findByUserIdAndMessageContainingIgnoreCase(Long userId, String message);
    Page<Notification> findByUserId(Long userId, Pageable pageable);
    List<Notification> findByUserIdOrderByIdDesc(Long userId);
    List<Notification> findByUserIdAndReadStatus(Long userId, boolean readStatus);

List<Notification> findByUserIdAndReadStatusOrderByIdDesc(Long userId, boolean readStatus);
@Transactional
void deleteByUserId(Long userId);
long countByUserIdAndMessageContainingIgnoreCase(Long userId, String message);
long countByUserId(Long userId);
long countByUserIdAndMessageContainingIgnoreCaseAndReadStatus(
        Long userId,
        String message,
        boolean readStatus
);
long countByUserIdAndReadStatus(Long userId, boolean readStatus);


}