package com.example.dto;

public class NotificationPreferenceDTO {

    private Long userId;
    private boolean answerNotifications;
    private boolean commentNotifications;
    private boolean upvoteNotifications;
    private boolean downvoteNotifications;
    private boolean followNotifications;

    public NotificationPreferenceDTO(
            Long userId,
            boolean answerNotifications,
            boolean commentNotifications,
            boolean upvoteNotifications,
            boolean downvoteNotifications,
            boolean followNotifications) {

        this.userId = userId;
        this.answerNotifications = answerNotifications;
        this.commentNotifications = commentNotifications;
        this.upvoteNotifications = upvoteNotifications;
        this.downvoteNotifications = downvoteNotifications;
        this.followNotifications = followNotifications;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isAnswerNotifications() {
        return answerNotifications;
    }

    public boolean isCommentNotifications() {
        return commentNotifications;
    }

    public boolean isUpvoteNotifications() {
        return upvoteNotifications;
    }

    public boolean isDownvoteNotifications() {
        return downvoteNotifications;
    }

    public boolean isFollowNotifications() {
        return followNotifications;
    }
}
