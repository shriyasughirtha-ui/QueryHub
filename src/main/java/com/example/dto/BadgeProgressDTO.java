package com.example.dto;

public class BadgeProgressDTO {

    private Long userId;
    private String badge;
    private long current;
    private long required;
    private int progress;
    private boolean achieved;

    public BadgeProgressDTO(
            Long userId,
            String badge,
            long current,
            long required,
            int progress,
            boolean achieved) {

        this.userId = userId;
        this.badge = badge;
        this.current = current;
        this.required = required;
        this.progress = progress;
        this.achieved = achieved;
    }

    public Long getUserId() {
        return userId;
    }

    public String getBadge() {
        return badge;
    }

    public long getCurrent() {
        return current;
    }

    public long getRequired() {
        return required;
    }

    public int getProgress() {
        return progress;
    }

    public boolean isAchieved() {
        return achieved;
    }
}
