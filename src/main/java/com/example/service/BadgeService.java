
package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Badge;
import com.example.entity.User;
import com.example.repository.AnswerRepository;
import com.example.repository.BadgeRepository;
import com.example.repository.QuestionRepository;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public BadgeService(
            BadgeRepository badgeRepository,
            AnswerRepository answerRepository,
            QuestionRepository questionRepository) {

        this.badgeRepository = badgeRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public void checkBadges(User user) {

        Long userId = user.getId();

        long questionCount =
                questionRepository.findByUserId(userId).size();

        long answerCount =
                answerRepository.findByUserId(userId).size();

        long acceptedAnswerCount =
                answerRepository.findByUserIdAndAcceptedTrue(userId).size();

        if (questionCount >= 1) {
            awardBadge(user, "Beginner",
                    "Asked your first question", "🌱");
        }

        if (answerCount >= 5) {
            awardBadge(user, "Helpful",
                    "Posted 5 answers", "💬");
        }

        if (user.getReputation() >= 50) {
            awardBadge(user, "Rising Star",
                    "Reached 50 reputation points", "⭐");
        }

        if (user.getReputation() >= 100) {
            awardBadge(user, "Expert",
                    "Reached 100 reputation points", "🏆");
        }

        if (acceptedAnswerCount >= 5) {
            awardBadge(user, "Problem Solver",
                    "Received 5 accepted answers", "🎯");
        }
    }

    private void awardBadge(
            User user, String name, String description, String icon) {

        if (!badgeRepository.existsByUserIdAndName(user.getId(), name)) {
            badgeRepository.save(
                    new Badge(name, description, icon, user));
        }
    }
}
