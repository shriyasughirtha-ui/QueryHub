package com.example.dto;

public class UserProfileDTO {

    private Long id;
    private String name;
    private String email;
    private int reputation;
    private String role;
    private int questionCount;
    private int answerCount;
    private int acceptedAnswerCount;
    private long upvotesReceived;
    private long downvotesReceived;

    public UserProfileDTO(Long id, String name, String email,
                           int reputation, String role,
                           int questionCount, int answerCount,
                           int acceptedAnswerCount,
                           long upvotesReceived, long downvotesReceived) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.reputation = reputation;
        this.role = role;
        this.questionCount = questionCount;
        this.answerCount = answerCount;
        this.acceptedAnswerCount = acceptedAnswerCount;
        this.upvotesReceived = upvotesReceived;
        this.downvotesReceived = downvotesReceived;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getReputation() {
        return reputation;
    }

    public String getRole() {
        return role;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public int getAcceptedAnswerCount() {
        return acceptedAnswerCount;
    }

    public long getUpvotesReceived() {
        return upvotesReceived;
    }

    public long getDownvotesReceived() {
        return downvotesReceived;
    }
}
