
package com.example.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Answer;
import com.example.entity.AnswerEditHistory;
import com.example.repository.AnswerEditHistoryRepository;

@Service
public class AnswerEditHistoryService {

    private final AnswerEditHistoryRepository historyRepository;

    public AnswerEditHistoryService(AnswerEditHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void recordEdit(Answer answer, String previousContent) {
        AnswerEditHistory history = new AnswerEditHistory();

        history.setAnswer(answer);
        history.setPreviousContent(previousContent);
        history.setEditedAt(LocalDateTime.now());

        historyRepository.save(history);
    }

    public List<AnswerEditHistory> getAnswerEditHistory(Long answerId) {
        return historyRepository.findByAnswerIdOrderByEditedAtDesc(answerId);
    }
}
