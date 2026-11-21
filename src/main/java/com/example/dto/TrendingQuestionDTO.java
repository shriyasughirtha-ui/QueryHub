package com.example.dto;

public class TrendingQuestionDTO {

    private Long questionId;
    private String title;
    private long engagementScore;

    public TrendingQuestionDTO(
            Long questionId,
            String title,
            long engagementScore) {

        this.questionId = questionId;
        this.title = title;
        this.engagementScore = engagementScore;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getTitle() {
        return title;
    }

    public long getEngagementScore() {
        return engagementScore;
    }
}
