package com.example.dto;

public class NotificationSummaryDTO {

    private Long userId;
    private long total;
    private long read;
    private long unread;
    private long answer;
    private long comment;
    private long upvote;
    private long downvote;
    private long follow;

    public NotificationSummaryDTO(
            Long userId,
            long total,
            long read,
            long unread,
            long answer,
            long comment,
            long upvote,
            long downvote,
            long follow) {

        this.userId = userId;
        this.total = total;
        this.read = read;
        this.unread = unread;
        this.answer = answer;
        this.comment = comment;
        this.upvote = upvote;
        this.downvote = downvote;
        this.follow = follow;
    }

    public Long getUserId() {
        return userId;
    }

    public long getTotal() {
        return total;
    }

    public long getRead() {
        return read;
    }

    public long getUnread() {
        return unread;
    }

    public long getAnswer() {
        return answer;
    }

    public long getComment() {
        return comment;
    }

    public long getUpvote() {
        return upvote;
    }

    public long getDownvote() {
        return downvote;
    }

    public long getFollow() {
        return follow;
    }
}
