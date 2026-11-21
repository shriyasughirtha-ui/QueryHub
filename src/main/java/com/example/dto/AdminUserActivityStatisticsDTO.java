package com.example.dto;

public class AdminUserActivityStatisticsDTO {

    private long totalUsers;
    private long activeUsers;
    private long inactiveUsers;

    public AdminUserActivityStatisticsDTO(
            long totalUsers,
            long activeUsers,
            long inactiveUsers) {

        this.totalUsers = totalUsers;
        this.activeUsers = activeUsers;
        this.inactiveUsers = inactiveUsers;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getActiveUsers() {
        return activeUsers;
    }

    public long getInactiveUsers() {
        return inactiveUsers;
    }
}