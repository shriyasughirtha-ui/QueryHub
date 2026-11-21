package com.example.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.entity.AdminActivityLog;
import com.example.repository.AdminActivityLogRepository;

@Service
public class AdminActivityLogService {


private final AdminActivityLogRepository repository;

public AdminActivityLogService(AdminActivityLogRepository repository) {
    this.repository = repository;
}

public AdminActivityLog logActivity(String adminEmail, String action,
        String targetType, Long targetId, String details) {
    AdminActivityLog log = new AdminActivityLog();
    log.setAdminEmail(adminEmail);
    log.setAction(action);
    log.setTargetType(targetType);
    log.setTargetId(targetId);
    log.setDetails(details);
    log.setCreatedAt(LocalDateTime.now());
    return repository.save(log);
}

public List<AdminActivityLog> getAllLogs() {
    return repository.findAllByOrderByCreatedAtDesc();
}

public List<AdminActivityLog> getLogsByAdmin(String adminEmail) {
    return repository.findByAdminEmailIgnoreCaseOrderByCreatedAtDesc(adminEmail);
}

public List<AdminActivityLog> getLogsByAction(String action) {
    return repository.findByActionIgnoreCaseOrderByCreatedAtDesc(action);
}

public List<AdminActivityLog> getLogsByDate(
        LocalDateTime start, LocalDateTime end) {
    return repository.findByCreatedAtBetweenOrderByCreatedAtDesc(start, end);
}

public Page<AdminActivityLog> getLogsPaged(int page, int size) {
    Pageable pageable = PageRequest.of(
            page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    return repository.findAll(pageable);
}

}


