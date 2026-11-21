package com.example.dto;

public class AdminReputationStatisticsDTO {

    private int highestReputation;
    private int lowestReputation;
    private double averageReputation;

    public AdminReputationStatisticsDTO(
            int highestReputation,
            int lowestReputation,
            double averageReputation) {

        this.highestReputation = highestReputation;
        this.lowestReputation = lowestReputation;
        this.averageReputation = averageReputation;
    }

    public int getHighestReputation() {
        return highestReputation;
    }

    public int getLowestReputation() {
        return lowestReputation;
    }

    public double getAverageReputation() {
        return averageReputation;
    }
}
