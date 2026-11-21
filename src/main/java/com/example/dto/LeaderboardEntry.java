package com.example.dto;

public class LeaderboardEntry {

    private Long id;
    private String name;
    private int reputation;

    public LeaderboardEntry(Long id, String name, int reputation) {
        this.id = id;
        this.name = name;
        this.reputation = reputation;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getReputation() {
        return reputation;
    }
}