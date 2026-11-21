package com.example.dto;

public class QuestionEngagementDTO {

    private Long questionId;
    private long views;
    private long upvotes;
    private long answers;
    private long comments;
    private long followers;
    private long shares;
    private long engagementScore;

    public QuestionEngagementDTO(
            Long questionId,
            long views,
            long upvotes,
            long answers,
            long comments,
            long followers,
            long shares,
            long engagementScore) {

        this.questionId = questionId;
        this.views = views;
        this.upvotes = upvotes;
        this.answers = answers;
        this.comments = comments;
        this.followers = followers;
        this.shares = shares;
        this.engagementScore = engagementScore;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public long getViews() {
        return views;
    }

    public long getUpvotes() {
        return upvotes;
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

    public long getShares() {
        return shares;
    }

    public long getEngagementScore() {
        return engagementScore;
    }
}
