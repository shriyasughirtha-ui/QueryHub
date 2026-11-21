package com.example.dto;

public class UserDashboardDTO {

    private Long userId;
    private String name;
    private int reputation;
    private String reputationLevel;
    private long questionCount;
    private long answerCount;
    private long acceptedAnswerCount;
    private long followersCount;
    private long followingCount;
    private long bookmarkCount;
    private long notificationCount;
    private long unreadNotificationCount;
    private long badgeCount;

    public UserDashboardDTO(
            Long userId,
            String name,
            int reputation,
            String reputationLevel,
            long questionCount,
            long answerCount,
            long acceptedAnswerCount,
            long followersCount,
            long followingCount,
            long bookmarkCount,
            long notificationCount,
            long unreadNotificationCount,
            long badgeCount) {

        this.userId = userId;
        this.name = name;
        this.reputation = reputation;
        this.reputationLevel = reputationLevel;
        this.questionCount = questionCount;
        this.answerCount = answerCount;
        this.acceptedAnswerCount = acceptedAnswerCount;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.bookmarkCount = bookmarkCount;
        this.notificationCount = notificationCount;
        this.unreadNotificationCount = unreadNotificationCount;
        this.badgeCount = badgeCount;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getReputation() {
        return reputation;
    }

    public String getReputationLevel() {
        return reputationLevel;
    }

    public long getQuestionCount() {
        return questionCount;
    }

    public long getAnswerCount() {
        return answerCount;
    }

    public long getAcceptedAnswerCount() {
        return acceptedAnswerCount;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public long getFollowingCount() {
        return followingCount;
    }

    public long getBookmarkCount() {
        return bookmarkCount;
    }

    public long getNotificationCount() {
        return notificationCount;
    }

    public long getUnreadNotificationCount() {
        return unreadNotificationCount;
    }

    public long getBadgeCount() {
        return badgeCount;
    }
}