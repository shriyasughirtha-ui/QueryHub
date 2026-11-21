package com.example.dto;

public class QuestionFollowNotificationCountDTO {

    private Long userId;
    private long count;

    public QuestionFollowNotificationCountDTO(Long userId, long count) {
        this.userId = userId;
        this.count = count;
    }

    public Long getUserId() {
        return userId;
    }

    public long getCount() {
        return count;
    }
}
