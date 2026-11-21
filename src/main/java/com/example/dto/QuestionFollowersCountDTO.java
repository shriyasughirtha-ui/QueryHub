package com.example.dto;

public class QuestionFollowersCountDTO {

    private Long questionId;
    private long followersCount;

    public QuestionFollowersCountDTO(Long questionId, long followersCount) {
        this.questionId = questionId;
        this.followersCount = followersCount;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public long getFollowersCount() {
        return followersCount;
    }
}
