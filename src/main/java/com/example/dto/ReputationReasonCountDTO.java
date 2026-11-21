package com.example.dto;

public class ReputationReasonCountDTO {

    private Long userId;
    private String reason;
    private long count;

    public ReputationReasonCountDTO(
            Long userId,
            String reason,
            long count) {

        this.userId = userId;
        this.reason = reason;
        this.count = count;
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
}
