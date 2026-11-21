package com.example.dto;

public class NotificationMessageCountDTO {

    private String keyword;
    private long count;

    public NotificationMessageCountDTO(
            String keyword,
            long count) {

        this.keyword = keyword;
        this.count = count;
    }

    public String getKeyword() {
        return keyword;
    }

    public long getCount() {
        return count;
    }
}