
package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.QuestionEditHistory;

public interface QuestionEditHistoryRepository
        extends JpaRepository<QuestionEditHistory, Long> {

    List<QuestionEditHistory> findByQuestionIdOrderByEditedAtDesc(Long questionId);
}
