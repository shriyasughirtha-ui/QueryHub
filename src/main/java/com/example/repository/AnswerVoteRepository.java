package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.AnswerVote;

public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {

    Optional<AnswerVote> findByUserIdAndAnswerId(Long userId, Long answerId);

    long countByAnswerUserIdAndVoteType(Long userId, String voteType);
}