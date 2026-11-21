
package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.AnswerEditHistory;

public interface AnswerEditHistoryRepository extends JpaRepository<AnswerEditHistory, Long> {

    List<AnswerEditHistory> findByAnswerIdOrderByEditedAtDesc(Long answerId);
}

