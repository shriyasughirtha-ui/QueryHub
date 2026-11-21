
package com.example.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.entity.Question;
import com.example.entity.QuestionStatusHistory;
import com.example.repository.QuestionStatusHistoryRepository;

@Service
public class QuestionStatusHistoryService {

    private final QuestionStatusHistoryRepository historyRepository;

    public QuestionStatusHistoryService(
            QuestionStatusHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void recordStatusChange(
            Question question,
            String previousStatus,
            String newStatus) {

        QuestionStatusHistory history = new QuestionStatusHistory();

        history.setQuestion(question);
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(newStatus);
        history.setChangedAt(LocalDateTime.now());

        historyRepository.save(history);
    }
    public java.util.List<QuestionStatusHistory> getQuestionStatusHistory(
        Long questionId) {

    return historyRepository.findByQuestionIdOrderByChangedAtDesc(
            questionId
    );
}
}

