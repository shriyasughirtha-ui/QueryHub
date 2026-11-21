package com.example.dto;

public class UserReputationStatisticsDTO {

    private int highestReputation;
    private int lowestReputation;
    private double averageReputation;

    public UserReputationStatisticsDTO(
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
