
package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.QuestionStatusHistory;

public interface QuestionStatusHistoryRepository
        extends JpaRepository<QuestionStatusHistory, Long> {

    List<QuestionStatusHistory> findByQuestionIdOrderByChangedAtDesc(Long questionId);
}
