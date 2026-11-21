package com.example.dto;

public class UserAchievementDTO {

    private Long userId;
    private String reputationLevel;
    private long badges;
    private long questions;
    private long answers;
    private long acceptedAnswers;
    private long followers;
    private long engagementScore;

    public UserAchievementDTO(
            Long userId,
            String reputationLevel,
            long badges,
            long questions,
            long answers,
            long acceptedAnswers,
            long followers,
            long engagementScore) {

        this.userId = userId;
        this.reputationLevel = reputationLevel;
        this.badges = badges;
        this.questions = questions;
        this.answers = answers;
        this.acceptedAnswers = acceptedAnswers;
        this.followers = followers;
        this.engagementScore = engagementScore;
    }

    public Long getUserId() {
        return userId;
    }

    public String getReputationLevel() {
        return reputationLevel;
    }

    public long getBadges() {
        return badges;
    }

    public long getQuestions() {
        return questions;
    }

    public long getAnswers() {
        return answers;
    }

    public long getAcceptedAnswers() {
        return acceptedAnswers;
    }

    public long getFollowers() {
        return followers;
    }

    public long getEngagementScore() {
        return engagementScore;
    }
}
