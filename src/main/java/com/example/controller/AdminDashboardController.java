package com.example.controller;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AdminDashboardDTO;
import com.example.dto.AdminReportDashboardDTO;
import com.example.dto.AdminReputationStatisticsDTO;
import com.example.dto.AdminUserActivityDashboardDTO;
import com.example.dto.AdminUserActivityStatisticsDTO;
import com.example.entity.Report;
import com.example.entity.User;
import com.example.repository.AnswerRepository;
import com.example.repository.NotificationRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.ReportRepository;
import com.example.repository.UserRepository;
import com.example.service.AdminActivityLogService;

@RestController
public class AdminDashboardController {
    private final AdminActivityLogService adminActivityLogService;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final NotificationRepository notificationRepository;
    private final ReportRepository reportRepository;

    public AdminDashboardController(
            UserRepository userRepository,
            QuestionRepository questionRepository,
            AnswerRepository answerRepository,
            AdminActivityLogService adminActivityLogService,
            NotificationRepository notificationRepository,
            ReportRepository reportRepository) {
        this.adminActivityLogService = adminActivityLogService;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.notificationRepository = notificationRepository;
        this.reportRepository = reportRepository;
    }

    @GetMapping("/admin/dashboard")
    public AdminDashboardDTO getAdminDashboard() {

        long totalUsers = userRepository.count();
        long totalQuestions = questionRepository.count();
        long totalAnswers = answerRepository.count();
        long totalNotifications = notificationRepository.count();
        long totalReports = reportRepository.count();

        long pendingReports =
                reportRepository.countByStatusIgnoreCase("PENDING");

        long resolvedReports =
                reportRepository.countByStatusIgnoreCase("RESOLVED");

        long rejectedReports =
                reportRepository.countByStatusIgnoreCase("REJECTED");

        return new AdminDashboardDTO(
                totalUsers,
                totalQuestions,
                totalAnswers,
                totalNotifications,
                totalReports,
                pendingReports,
                resolvedReports,
                rejectedReports
        );
    }

    @PutMapping("/admin/users/{id}/active")
    public User updateUserActiveStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {
User user = userRepository.findById(id)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND,
                "User not found"));

        user.setActive(active);

        return userRepository.save(user);
    }
    @PutMapping("/admin/users/{id}/role")
public User updateUserRole(
        @PathVariable Long id,
        @RequestParam String role) {

    User user = userRepository.findById(id)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND,
                "User not found"));

    String newRole = role.toUpperCase();

if (!newRole.equals("ADMIN") && !newRole.equals("USER")) {
    throw new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.BAD_REQUEST,
            "Invalid role. Use ADMIN or USER");
}

user.setRole(newRole);

    return userRepository.save(user);
}
@GetMapping("/admin/users/active")
public List<User> getActiveUsers() {
    return userRepository.findByActiveTrue();
}
@GetMapping("/admin/users/inactive")
public List<User> getInactiveUsers() {
    return userRepository.findByActiveFalse();
}
@GetMapping("/admin/users")
public List<User> getAllUsers() {
    return userRepository.findAll();
}
@GetMapping("/admin/users/search")
public List<User> searchUsers(@RequestParam String keyword) {
    return userRepository
            .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
}
@GetMapping("/admin/users/role")
public List<User> getUsersByRole(@RequestParam String role) {
    return userRepository.findByRoleIgnoreCase(role);
}
@GetMapping("/admin/users/reputation")
public List<User> getUsersByReputation(
        @RequestParam int min) {

    return userRepository.findByReputationGreaterThanEqual(min);
}
@GetMapping("/admin/users/sort/reputation")
public List<User> sortUsersByReputation() {
    return userRepository.findAllByOrderByReputationDesc();
}
@GetMapping("/admin/users/sort/name")
public List<User> sortUsersByName() {
    return userRepository.findAllByOrderByNameAsc();
}
@GetMapping("/admin/users/sort/name-desc")
public List<User> sortUsersByNameDesc() {
    return userRepository.findAllByOrderByNameDesc();
}
@GetMapping("/admin/users/activity-statistics")
public AdminUserActivityStatisticsDTO getUserActivityStatistics() {

    long totalUsers = userRepository.count();

    long activeUsers = userRepository.countByActiveTrue();

    long inactiveUsers = userRepository.countByActiveFalse();

    return new AdminUserActivityStatisticsDTO(
            totalUsers,
            activeUsers,
            inactiveUsers
    );
}
@GetMapping("/admin/users/reputation-statistics")
public AdminReputationStatisticsDTO getReputationStatistics() {

    Integer highest = userRepository.findHighestReputation();
    Integer lowest = userRepository.findLowestReputation();
    Double average = userRepository.findAverageReputation();

    return new AdminReputationStatisticsDTO(
            highest != null ? highest : 0,
            lowest != null ? lowest : 0,
            average != null ? average : 0.0
    );
}
@GetMapping("/admin/users/reputation-by-role")
public Double getAverageReputationByRole(
        @RequestParam String role) {

    Double average =
            userRepository.findAverageReputationByRole(role);

    return average != null ? average : 0.0;
}
@GetMapping("/admin/users/highest-reputation-by-role")
public Integer getHighestReputationByRole(
        @RequestParam String role) {

    Integer highest =
            userRepository.findHighestReputationByRole(role);

    return highest != null ? highest : 0;
}
@GetMapping("/admin/users/lowest-reputation-by-role")
public Integer getLowestReputationByRole(
        @RequestParam String role) {

    Integer lowest =
            userRepository.findLowestReputationByRole(role);

    return lowest != null ? lowest : 0;
}
@GetMapping("/admin/users/email-domain-count")
public long getEmailDomainCount(
        @RequestParam String domain) {

    return userRepository.countByEmailEndingWithIgnoreCase(domain);
}
@GetMapping("/admin/users/reputation-range-count")
public long getReputationRangeCount(
        @RequestParam int min,
        @RequestParam int max) {

    return userRepository.countByReputationRange(min, max);
}
@GetMapping("/admin/users/role-reputation-statistics")
public Double getRoleReputationStatistics(
        @RequestParam String role) {

    Double average =
            userRepository.findAverageReputationByRole(role);

    return average != null ? average : 0.0;
}
@GetMapping("/admin/users/reputation-level-statistics")
public List<Object[]> getReputationLevelStatistics() {

    return userRepository.countUsersByReputationLevel();
}
@GetMapping("/admin/users/reputation-range")
public List<User> getUsersByReputationRange(
        @RequestParam int min,
        @RequestParam int max) {

    return userRepository.findByReputationBetween(min, max);
}
@GetMapping("/admin/users/activity-dashboard")
public AdminUserActivityDashboardDTO getUserActivityDashboard() {

    long totalUsers = userRepository.count();
    long activeUsers = userRepository.countByActiveTrue();
    long inactiveUsers = userRepository.countByActiveFalse();

    long adminUsers = userRepository.countByRoleIgnoreCase("ADMIN");
    long normalUsers = userRepository.countByRoleIgnoreCase("USER");

    Integer highest = userRepository.findHighestReputation();
    Integer lowest = userRepository.findLowestReputation();
    Double average = userRepository.findAverageReputation();

    return new AdminUserActivityDashboardDTO(
            totalUsers,
            activeUsers,
            inactiveUsers,
            adminUsers,
            normalUsers,
            highest != null ? highest : 0,
            lowest != null ? lowest : 0,
            average != null ? average : 0.0
    );
}
@GetMapping("/admin/reports/target-type")
public List<Report> getReportsByTargetType(
        @RequestParam String targetType) {

    return reportRepository.findByTargetTypeIgnoreCase(targetType);
}
@GetMapping("/admin/reports/status")
public List<Report> getReportsByStatus(
        @RequestParam String status) {

    return reportRepository.findByStatusIgnoreCase(status);
}
@GetMapping("/admin/reports/target-type-count")
public long getReportCountByTargetType(
        @RequestParam String targetType) {

    return reportRepository.countByTargetTypeIgnoreCase(targetType);
}
@GetMapping("/admin/reports/pending-count-by-target")
public long getPendingReportCountByTarget(
        @RequestParam String targetType) {

    return reportRepository
            .countByTargetTypeIgnoreCaseAndStatusIgnoreCase(
                    targetType, "PENDING");
}
@GetMapping("/admin/reports/dashboard")
public AdminReportDashboardDTO getReportDashboard() {

    long totalReports = reportRepository.count();
    long pendingReports =
            reportRepository.countByStatusIgnoreCase("PENDING");
    long resolvedReports =
            reportRepository.countByStatusIgnoreCase("RESOLVED");
    long rejectedReports =
            reportRepository.countByStatusIgnoreCase("REJECTED");

    long questionReports =
            reportRepository.countByTargetTypeIgnoreCase("QUESTION");
    long answerReports =
            reportRepository.countByTargetTypeIgnoreCase("ANSWER");

    return new AdminReportDashboardDTO(
            totalReports,
            pendingReports,
            resolvedReports,
            rejectedReports,
            questionReports,
            answerReports
    );
}
@GetMapping("/admin/reports/search")
public List<Report> searchReports(
        @RequestParam String keyword) {

    return reportRepository
            .findByReasonContainingIgnoreCase(keyword);
}
@GetMapping("/admin/reports/{id}")
public Report getReportById(@PathVariable Long id) {
    return reportRepository.findById(id).orElse(null);
}


@DeleteMapping("/admin/reports/{id}")
public String deleteReport(
        @PathVariable Long id,
        Authentication authentication) {

    if (!reportRepository.existsById(id)) {
        return "Report not found";
    }

    reportRepository.deleteById(id);

    String adminEmail = authentication.getName();

    adminActivityLogService.logActivity(
            adminEmail,
            "DELETE_REPORT",
            "REPORT",
            id,
            "Admin deleted report with ID: " + id
    );

    return "Report deleted successfully";
}




@PutMapping("/admin/reports/{id}/status")
public Report updateReportStatus(
        @PathVariable Long id,
        @RequestParam String status,
        Authentication authentication) {

    Report report = reportRepository.findById(id).orElse(null);

    if (report == null) {
        return null;
    }

    String newStatus = status.toUpperCase();

if (!newStatus.equals("PENDING")
        && !newStatus.equals("RESOLVED")
        && !newStatus.equals("REJECTED")) {
    throw new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.BAD_REQUEST,
            "Invalid status. Use PENDING, RESOLVED, or REJECTED");
}

report.setStatus(newStatus);

Report updatedReport = reportRepository.save(report);

String adminEmail = authentication.getName();

adminActivityLogService.logActivity(
        adminEmail,
        "UPDATE_REPORT_STATUS",
        "REPORT",
        id,
        "Admin changed report status to: " + newStatus
);

return updatedReport;
}
@GetMapping("/admin/reports")
public List<Report> getAllReports() {
    return reportRepository.findAll();
}

@GetMapping("/admin/users/paged")
public Page<User> getUsersPaged(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {

    if (page < 0 || size < 1 || size > 100) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Page must be 0 or greater, and size must be between 1 and 100");
    }

    if (!sortBy.equals("id")
            && !sortBy.equals("name")
            && !sortBy.equals("reputation")) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Sort by id, name, or reputation");
    }

    if (!direction.equalsIgnoreCase("asc")
            && !direction.equalsIgnoreCase("desc")) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Direction must be asc or desc");
    }

    org.springframework.data.domain.Sort sort =
            direction.equalsIgnoreCase("desc")
                    ? org.springframework.data.domain.Sort.by(sortBy).descending()
                    : org.springframework.data.domain.Sort.by(sortBy).ascending();

    Pageable pageable = PageRequest.of(page, size, sort);

    return userRepository.findAll(pageable);
}

@GetMapping("/admin/users/filter")
public List<User> filterUsers(
        @RequestParam boolean active,
        @RequestParam String role,
        @RequestParam(defaultValue = "0") int minReputation) {

    if (minReputation < 0) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Minimum reputation cannot be negative");
    }

    if (!role.equalsIgnoreCase("USER")
            && !role.equalsIgnoreCase("ADMIN")) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Role must be USER or ADMIN");
    }

    return userRepository.findByActiveAndRoleAndReputationGreaterThanEqual(
            active, role, minReputation);
}

}
