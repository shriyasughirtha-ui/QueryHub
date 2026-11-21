package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.QuestionFollow;

public interface QuestionFollowRepository extends JpaRepository<QuestionFollow, Long> {

    List<QuestionFollow> findByUserId(Long userId);

    Optional<QuestionFollow> findByUserIdAndQuestionId(Long userId, Long questionId);
    long countByQuestionId(Long questionId);
    List<QuestionFollow> findByQuestionId(Long questionId);
    boolean existsByUserIdAndQuestionId(Long userId, Long questionId);
}
