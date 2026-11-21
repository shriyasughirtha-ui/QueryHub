package com.example.dto;

public class ReputationLevelCountDTO {

    private String level;
    private long count;

    public ReputationLevelCountDTO(
            String level,
            long count) {

        this.level = level;
        this.count = count;
    }

    public String getLevel() {
        return level;
    }

    public long getCount() {
        return count;
    }
}
