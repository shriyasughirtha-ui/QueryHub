package com.example.dto;

public class UserActivityTimelineDTO {

    private Long userId;
    private String activity;
    private String description;

    public UserActivityTimelineDTO(
            Long userId,
            String activity,
            String description) {

        this.userId = userId;
        this.activity = activity;
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public String getActivity() {
        return activity;
    }

    public String getDescription() {
        return description;
    }
}
