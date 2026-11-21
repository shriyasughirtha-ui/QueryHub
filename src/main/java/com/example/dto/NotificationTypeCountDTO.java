package com.example.dto;

public class NotificationTypeCountDTO {

    private Long userId;
    private String type;
    private long count;

    public NotificationTypeCountDTO(
            Long userId,
            String type,
            long count) {

        this.userId = userId;
        this.type = type;
        this.count = count;
    }

    public Long getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public long getCount() {
        return count;
    }
}
