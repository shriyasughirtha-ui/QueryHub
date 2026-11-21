package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Report;
import com.example.repository.ReportRepository;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportRepository reportRepository;

    public ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
@PostMapping
public Report createReport(@RequestBody Report report) {

    boolean exists =
            reportRepository.existsByUserIdAndTargetTypeAndTargetIdAndReasonIgnoreCase(
                    report.getUserId(),
                    report.getTargetType(),
                    report.getTargetId(),
                    report.getReason()
            );

    if (exists) {
        return null;
    }

    if (report.getStatus() == null ||
            report.getStatus().isBlank()) {

        report.setStatus("PENDING");
    }

    return reportRepository.save(report);
}

    @GetMapping
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Report> getReportsByUser(
            @PathVariable Long userId) {

        return reportRepository.findByUserId(userId);
    }

    @GetMapping("/status/{status}")
    public List<Report> getReportsByStatus(
            @PathVariable String status) {

        return reportRepository.findByStatusIgnoreCase(status);
    }
    @PutMapping("/{id}/status")
public Report updateReportStatus(
        @PathVariable Long id,
        @RequestParam String status) {

    Report report = reportRepository.findById(id).orElse(null);

    if (report == null) {
        return null;
    }

    report.setStatus(status.toUpperCase());

    return reportRepository.save(report);
}
@GetMapping("/count")
public long getReportCount(@RequestParam String status) {
    return reportRepository.countByStatusIgnoreCase(status);
}
}