package com.example.dto;

public class ReputationBreakdownDTO {

    private Long userId;
    private String reason;
    private long count;
    private long points;

    public ReputationBreakdownDTO(
            Long userId,
            String reason,
            long count,
            long points) {

        this.userId = userId;
        this.reason = reason;
        this.count = count;
        this.points = points;
    }

    public Long getUserId() {
        return userId;
    }

    public String getReason() {
        return reason;
    }

    public long getCount() {
        return count;
    }

    public long getPoints() {
        return points;
    }
}
