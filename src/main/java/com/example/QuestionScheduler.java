
package com.example;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.entity.Question;
import com.example.repository.QuestionRepository;
import com.example.service.QuestionStatusHistoryService;

@Component
public class QuestionScheduler {
    private final QuestionStatusHistoryService questionStatusHistoryService;

    private final QuestionRepository questionRepository;

    public QuestionScheduler(
            QuestionStatusHistoryService questionStatusHistoryService,
            QuestionRepository questionRepository) {
        this.questionStatusHistoryService = questionStatusHistoryService;
        this.questionRepository = questionRepository;
    }
@Scheduled(fixedRate = 60000)
public void expireQuestions() {
    List<Question> expiredQuestions =
            questionRepository.findByStatusAndExpiresAtLessThanEqual(
                    "OPEN", LocalDateTime.now());

    for (Question question : expiredQuestions) {
        String previousStatus = question.getStatus();

        question.setStatus("CLOSED");

        Question closedQuestion = questionRepository.save(question);

        questionStatusHistoryService.recordStatusChange(
                closedQuestion,
                previousStatus,
                "CLOSED"
        );
    }
}

   @Scheduled(fixedRate = 60000)
public void publishScheduledQuestions() {

    List<Question> scheduledQuestions =
            questionRepository.findByStatusAndScheduledAtLessThanEqual(
                    "SCHEDULED",
                    LocalDateTime.now()
            );

    for (Question question : scheduledQuestions) {
        String previousStatus = question.getStatus();

        question.setStatus("OPEN");
        question.setScheduledAt(null);

        Question publishedQuestion = questionRepository.save(question);

        questionStatusHistoryService.recordStatusChange(
                publishedQuestion,
                previousStatus,
                "OPEN"
        );
    }
}
}
