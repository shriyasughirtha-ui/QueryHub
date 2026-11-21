package com.example.dto;

public class NotificationStatisticsDTO {

    private long totalNotifications;
    private long readNotifications;
    private long unreadNotifications;

    public NotificationStatisticsDTO(
            long totalNotifications,
            long readNotifications,
            long unreadNotifications) {

        this.totalNotifications = totalNotifications;
        this.readNotifications = readNotifications;
        this.unreadNotifications = unreadNotifications;
    }

    public long getTotalNotifications() {
        return totalNotifications;
    }

    public long getReadNotifications() {
        return readNotifications;
    }

    public long getUnreadNotifications() {
        return unreadNotifications;
    }
}
