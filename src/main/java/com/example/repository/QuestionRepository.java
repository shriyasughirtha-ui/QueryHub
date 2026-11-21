package com.example.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByTitleContainingIgnoreCase(String keyword);

    List<Question> findByCategoryIgnoreCase(String category);

    List<Question> findByUserId(Long userId);

    List<Question> findByTagsContainingIgnoreCase(String tag);

    List<Question> findAllByOrderByIdDesc();

    List<Question> findAllByOrderByViewsDesc();

    List<Question> findAllByOrderByUpvotesDesc();
    List<Question> findByStatusIgnoreCase(String status);
    List<Question> findByStatusAndExpiresAtLessThanEqual(
        String status, java.time.LocalDateTime expiresAt);
    List<Question> findByUserIdAndStatusIgnoreCase(Long userId, String status);
    List<Question> findByTitleContainingIgnoreCaseAndStatusIgnoreCase(
        String keyword, String status);
        List<Question> findByCategoryIgnoreCaseAndStatusIgnoreCase(
        String category, String status);
        List<Question> findByStatusAndScheduledAtLessThanEqual(
        String status,
        LocalDateTime scheduledAt
);
    
}