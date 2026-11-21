package com.example.dto;

public class AdminDashboardDTO {

    private long totalUsers;
    private long totalQuestions;
    private long totalAnswers;
    private long totalNotifications;
    private long totalReports;
    private long pendingReports;
    private long resolvedReports;
    private long rejectedReports;

    public AdminDashboardDTO(
            long totalUsers,
            long totalQuestions,
            long totalAnswers,
            long totalNotifications,
            long totalReports,
            long pendingReports,
            long resolvedReports,
            long rejectedReports) {

        this.totalUsers = totalUsers;
        this.totalQuestions = totalQuestions;
        this.totalAnswers = totalAnswers;
        this.totalNotifications = totalNotifications;
        this.totalReports = totalReports;
        this.pendingReports = pendingReports;
        this.resolvedReports = resolvedReports;
        this.rejectedReports = rejectedReports;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getTotalQuestions() {
        return totalQuestions;
    }

    public long getTotalAnswers() {
        return totalAnswers;
    }

    public long getTotalNotifications() {
        return totalNotifications;
    }

    public long getTotalReports() {
        return totalReports;
    }

    public long getPendingReports() {
        return pendingReports;
    }

    public long getResolvedReports() {
        return resolvedReports;
    }

    public long getRejectedReports() {
        return rejectedReports;
    }
}
