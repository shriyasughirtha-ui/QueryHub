package com.example.dto;

public class RoleCountDTO {

    private String role;
    private long count;

    public RoleCountDTO(
            String role,
            long count) {

        this.role = role;
        this.count = count;
    }

    public String getRole() {
        return role;
    }

    public long getCount() {
        return count;
    }
}