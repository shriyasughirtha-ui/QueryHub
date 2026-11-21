package com.example.dto;

public class AdminUserStatisticsDTO {

    private long totalUsers;
    private long adminUsers;
    private long normalUsers;

    public AdminUserStatisticsDTO(
            long totalUsers,
            long adminUsers,
            long normalUsers) {

        this.totalUsers = totalUsers;
        this.adminUsers = adminUsers;
        this.normalUsers = normalUsers;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getAdminUsers() {
        return adminUsers;
    }

    public long getNormalUsers() {
        return normalUsers;
    }
}