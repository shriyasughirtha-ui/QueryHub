package com.example.dto;

public class RoleReputationStatisticsDTO {

    private String role;
    private long userCount;
    private int highestReputation;
    private int lowestReputation;
    private double averageReputation;

    public RoleReputationStatisticsDTO(
            String role,
            long userCount,
            int highestReputation,
            int lowestReputation,
            double averageReputation) {

        this.role = role;
        this.userCount = userCount;
        this.highestReputation = highestReputation;
        this.lowestReputation = lowestReputation;
        this.averageReputation = averageReputation;
    }

    public String getRole() {
        return role;
    }

    public long getUserCount() {
        return userCount;
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
