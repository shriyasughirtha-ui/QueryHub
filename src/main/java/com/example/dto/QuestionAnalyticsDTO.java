package com.example.dto;

public class QuestionAnalyticsDTO {

    private Long questionId;
    private long views;
    private long upvotes;
    private long downvotes;
    private long answerCount;
    private long followersCount;
    private long shares;
    private long commentCount;
    private boolean acceptedAnswer;
    private String status;

    public QuestionAnalyticsDTO(
            Long questionId,
            long views,
            long upvotes,
            long downvotes,
            long answerCount,
            long followersCount,
            long shares,
            long commentCount,
            boolean acceptedAnswer,
            String status) {

        this.questionId = questionId;
        this.views = views;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.answerCount = answerCount;
        this.followersCount = followersCount;
        this.shares = shares;
        this.commentCount = commentCount;
        this.acceptedAnswer = acceptedAnswer;
        this.status = status;
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

    public long getDownvotes() {
        return downvotes;
    }

    public long getAnswerCount() {
        return answerCount;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public long getShares() {
        return shares;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public boolean isAcceptedAnswer() {
        return acceptedAnswer;
    }

    public String getStatus() {
        return status;
    }
}
