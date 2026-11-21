package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByUserId(Long userId);

    List<Answer> findByQuestionId(Long questionId);

    List<Answer> findByUserIdAndAcceptedTrue(Long userId);
    List<Answer> findAllByOrderByUpvotesDesc();
    List<Answer> findAllByOrderByAcceptedDesc();
    List<Answer> findAllByOrderByIdDesc();
    List<Answer> findAllByOrderByDownvotesDesc();
    List<Answer> findByContentContainingIgnoreCase(String keyword);
    List<Answer> findByQuestionIdAndAcceptedTrue(Long questionId);
}