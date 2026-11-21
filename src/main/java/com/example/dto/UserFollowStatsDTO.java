package com.example.dto;

public class UserFollowStatsDTO {

    private Long userId;
    private long followersCount;
    private long followingCount;

    public UserFollowStatsDTO(
            Long userId,
            long followersCount,
            long followingCount) {

        this.userId = userId;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    public Long getUserId() {
        return userId;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public long getFollowingCount() {
        return followingCount;
    }
}
