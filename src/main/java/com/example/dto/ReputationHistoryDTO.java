package com.example.dto;

public class ReputationHistoryDTO {

    private Long id;
    private int changeAmount;
    private String reason;

    public ReputationHistoryDTO(Long id, int changeAmount, String reason) {
        this.id = id;
        this.changeAmount = changeAmount;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public int getChangeAmount() {
        return changeAmount;
    }

    public String getReason() {
        return reason;
    }
}