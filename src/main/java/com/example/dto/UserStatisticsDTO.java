package com.example.dto;

public class UserStatisticsDTO {

    private Long userId;
    private long questionsAsked;
    private long answersGiven;
    private long acceptedAnswers;
    private long commentsMade;
    private long followers;
    private long following;
    private int reputation;
    private long badges;

    public UserStatisticsDTO(
            Long userId,
            long questionsAsked,
            long answersGiven,
            long acceptedAnswers,
            long commentsMade,
            long followers,
            long following,
            int reputation,
            long badges) {

        this.userId = userId;
        this.questionsAsked = questionsAsked;
        this.answersGiven = answersGiven;
        this.acceptedAnswers = acceptedAnswers;
        this.commentsMade = commentsMade;
        this.followers = followers;
        this.following = following;
        this.reputation = reputation;
        this.badges = badges;
    }

    public Long getUserId() {
        return userId;
    }

    public long getQuestionsAsked() {
        return questionsAsked;
    }

    public long getAnswersGiven() {
        return answersGiven;
    }

    public long getAcceptedAnswers() {
        return acceptedAnswers;
    }

    public long getCommentsMade() {
        return commentsMade;
    }

    public long getFollowers() {
        return followers;
    }

    public long getFollowing() {
        return following;
    }

    public int getReputation() {
        return reputation;
    }

    public long getBadges() {
        return badges;
    }
}
