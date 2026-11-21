package com.example.dto;

public class ActivityTimelineDTO {

    private String type;
    private Long id;
    private String content;

    public ActivityTimelineDTO(
            String type,
            Long id,
            String content) {

        this.type = type;
        this.id = id;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
