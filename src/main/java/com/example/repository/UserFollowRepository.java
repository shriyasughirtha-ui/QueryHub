package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.UserFollow;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {

    List<UserFollow> findByFollowerId(Long followerId);

    List<UserFollow> findByFollowingId(Long followingId);

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    @Transactional
    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);
    long countByFollowingId(Long followingId);

long countByFollowerId(Long followerId);
}