package com.example.dto;

public class UserEngagementDTO {

    private Long userId;
    private long questions;
    private long answers;
    private long comments;
    private long followers;
    private int reputation;
    private long engagementScore;

    public UserEngagementDTO(
            Long userId,
            long questions,
            long answers,
            long comments,
            long followers,
            int reputation,
            long engagementScore) {

        this.userId = userId;
        this.questions = questions;
        this.answers = answers;
        this.comments = comments;
        this.followers = followers;
        this.reputation = reputation;
        this.engagementScore = engagementScore;
    }

    public Long getUserId() {
        return userId;
    }

    public long getQuestions() {
        return questions;
    }

    public long getAnswers() {
        return answers;
    }

    public long getComments() {
        return comments;
    }

    public long getFollowers() {
        return followers;
    }

    public int getReputation() {
        return reputation;
    }

    public long getEngagementScore() {
        return engagementScore;
    }
}