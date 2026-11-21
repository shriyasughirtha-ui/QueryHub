package com.example.dto;

public class AdminUserActivityDashboardDTO {

    private long totalUsers;
    private long activeUsers;
    private long inactiveUsers;
    private long adminUsers;
    private long normalUsers;
    private int highestReputation;
    private int lowestReputation;
    private double averageReputation;

    public AdminUserActivityDashboardDTO(
            long totalUsers,
            long activeUsers,
            long inactiveUsers,
            long adminUsers,
            long normalUsers,
            int highestReputation,
            int lowestReputation,
            double averageReputation) {

        this.totalUsers = totalUsers;
        this.activeUsers = activeUsers;
        this.inactiveUsers = inactiveUsers;
        this.adminUsers = adminUsers;
        this.normalUsers = normalUsers;
        this.highestReputation = highestReputation;
        this.lowestReputation = lowestReputation;
        this.averageReputation = averageReputation;
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

    public long getAdminUsers() {
        return adminUsers;
    }

    public long getNormalUsers() {
        return normalUsers;
    }

    public int getHighestReputation() {
        return highestReputation;
    }

    public int getLowestReputation() {
        return lowestReputation;
    }

    public double getAverageReputation() {
        return averageReputation;
    }
}
