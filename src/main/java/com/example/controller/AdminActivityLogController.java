package com.example.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.AdminActivityLog;
import com.example.service.AdminActivityLogService;

@RestController
public class AdminActivityLogController {


private final AdminActivityLogService service;

public AdminActivityLogController(AdminActivityLogService service) {
    this.service = service;
}

@GetMapping("/admin/activity-logs")
public List<AdminActivityLog> getAllLogs() {
    return service.getAllLogs();
}

@GetMapping("/admin/activity-logs/by-admin")
public List<AdminActivityLog> getLogsByAdmin(@RequestParam String email) {
    return service.getLogsByAdmin(email);
}


@GetMapping("/admin/activity-logs/by-action")
public List<AdminActivityLog> getLogsByAction(@RequestParam String action) {
    return service.getLogsByAction(action);
}

@GetMapping("/admin/activity-logs/by-date")
public List<AdminActivityLog> getLogsByDate(
        @RequestParam LocalDateTime start,
        @RequestParam LocalDateTime end) {
    if (start.isAfter(end)) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Start date must be before or equal to end date");
    }
    return service.getLogsByDate(start, end);
}

@GetMapping("/admin/activity-logs/paged")
public Page<AdminActivityLog> getLogsPaged(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

    if (page < 0 || size < 1 || size > 100) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Page must be non-negative and size must be between 1 and 100");
    }

    return service.getLogsPaged(page, size);
}

}
