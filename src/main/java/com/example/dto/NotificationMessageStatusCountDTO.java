package com.example.dto;

public class NotificationMessageStatusCountDTO {

    private String keyword;
    private boolean read;
    private long count;

    public NotificationMessageStatusCountDTO(
            String keyword,
            boolean read,
            long count) {

        this.keyword = keyword;
        this.read = read;
        this.count = count;
    }

    public String getKeyword() {
        return keyword;
    }

    public boolean isRead() {
        return read;
    }

    public long getCount() {
        return count;
    }
}