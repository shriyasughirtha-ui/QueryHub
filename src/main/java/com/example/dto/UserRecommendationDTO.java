package com.example.dto;

public class UserRecommendationDTO {

    private Long questionId;
    private String title;
    private String category;
    private String tags;

    public UserRecommendationDTO(
            Long questionId,
            String title,
            String category,
            String tags) {

        this.questionId = questionId;
        this.title = title;
        this.category = category;
        this.tags = tags;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getTags() {
        return tags;
    }
}
