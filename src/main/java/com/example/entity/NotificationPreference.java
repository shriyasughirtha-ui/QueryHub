package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification_preferences")
public class NotificationPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    private boolean answerNotifications = true;
    private boolean commentNotifications = true;
    private boolean upvoteNotifications = true;
    private boolean downvoteNotifications = true;
    private boolean followNotifications = true;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAnswerNotifications() {
        return answerNotifications;
    }

    public void setAnswerNotifications(boolean answerNotifications) {
        this.answerNotifications = answerNotifications;
    }

    public boolean isCommentNotifications() {
        return commentNotifications;
    }

    public void setCommentNotifications(boolean commentNotifications) {
        this.commentNotifications = commentNotifications;
    }

    public boolean isUpvoteNotifications() {
        return upvoteNotifications;
    }

    public void setUpvoteNotifications(boolean upvoteNotifications) {
        this.upvoteNotifications = upvoteNotifications;
    }

    public boolean isDownvoteNotifications() {
        return downvoteNotifications;
    }

    public void setDownvoteNotifications(boolean downvoteNotifications) {
        this.downvoteNotifications = downvoteNotifications;
    }

    public boolean isFollowNotifications() {
        return followNotifications;
    }

    public void setFollowNotifications(boolean followNotifications) {
        this.followNotifications = followNotifications;
    }
}
