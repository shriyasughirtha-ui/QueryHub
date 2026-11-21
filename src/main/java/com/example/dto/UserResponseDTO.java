package com.example.dto;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private int reputation;
    private String role;

    public UserResponseDTO(Long id, String name, String email, int reputation, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.reputation = reputation;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getReputation() {
        return reputation;
    }

    public String getRole() {
        return role;
    }
}