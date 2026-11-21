package com.example.dto;

public class UserBadgeDTO {

    private Long userId;
    private String badge;

    public UserBadgeDTO(Long userId, String badge) {
        this.userId = userId;
        this.badge = badge;
    }

    public Long getUserId() {
        return userId;
    }

    public String getBadge() {
        return badge;
    }
}
