package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.QuestionView;

public interface QuestionViewRepository extends JpaRepository<QuestionView, Long> {

    List<QuestionView> findByQuestionId(Long questionId);

    List<QuestionView> findByUserId(Long userId);

    List<QuestionView> findByQuestionIdAndUserId(Long questionId, Long userId);

    List<QuestionView> findByUserIdOrderByViewedAtDesc(Long userId);
    long countByQuestionIdAndUserId(Long questionId, Long userId);
}
