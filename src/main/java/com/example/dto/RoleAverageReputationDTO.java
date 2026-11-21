package com.example.dto;

public class RoleAverageReputationDTO {

    private String role;
    private double averageReputation;

    public RoleAverageReputationDTO(
            String role,
            double averageReputation) {

        this.role = role;
        this.averageReputation = averageReputation;
    }

    public String getRole() {
        return role;
    }

    public double getAverageReputation() {
        return averageReputation;
    }
}
