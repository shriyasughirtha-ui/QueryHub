
package com.example.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.AdminActivityLog;

public interface AdminActivityLogRepository
        extends JpaRepository<AdminActivityLog, Long> {

    List<AdminActivityLog> findAllByOrderByCreatedAtDesc();

    List<AdminActivityLog> findByAdminEmailIgnoreCaseOrderByCreatedAtDesc(
            String adminEmail);

List<AdminActivityLog> findByActionIgnoreCaseOrderByCreatedAtDesc(String action);

List<AdminActivityLog> findByCreatedAtBetweenOrderByCreatedAtDesc(
        LocalDateTime start, LocalDateTime end);
}