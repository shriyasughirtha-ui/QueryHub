package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.QuestionVote;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {

    Optional<QuestionVote> findByUserIdAndQuestionId(Long userId, Long questionId);

    long countByQuestionUserIdAndVoteType(Long userId, String voteType);
}
