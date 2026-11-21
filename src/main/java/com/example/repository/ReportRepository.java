package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByUserId(Long userId);

    List<Report> findByStatusIgnoreCase(String status);

    long countByStatusIgnoreCase(String status);
    boolean existsByUserIdAndTargetTypeAndTargetIdAndReasonIgnoreCase(
        Long userId,
        String targetType,
        Long targetId,
        String reason
);
List<Report> findByTargetTypeIgnoreCase(String targetType);
long countByTargetTypeIgnoreCase(String targetType);
long countByTargetTypeIgnoreCaseAndStatusIgnoreCase(
        String targetType,
        String status);
List<Report> findByReasonContainingIgnoreCase(String keyword);


}
