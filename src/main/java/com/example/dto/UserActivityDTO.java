package com.example.dto;

public class UserActivityDTO {

    private Long userId;
    private long questionsAsked;
    private long answersGiven;
    private long acceptedAnswers;
    private long commentsMade;
    private int reputation;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getQuestionsAsked() {
        return questionsAsked;
    }

    public void setQuestionsAsked(long questionsAsked) {
        this.questionsAsked = questionsAsked;
    }

    public long getAnswersGiven() {
        return answersGiven;
    }

    public void setAnswersGiven(long answersGiven) {
        this.answersGiven = answersGiven;
    }

    public long getAcceptedAnswers() {
        return acceptedAnswers;
    }

    public void setAcceptedAnswers(long acceptedAnswers) {
        this.acceptedAnswers = acceptedAnswers;
    }

    public long getCommentsMade() {
        return commentsMade;
    }

    public void setCommentsMade(long commentsMade) {
        this.commentsMade = commentsMade;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }
}