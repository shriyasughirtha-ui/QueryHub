package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Question;
import com.example.entity.QuestionFollow;
import com.example.entity.User;
import com.example.repository.QuestionFollowRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.UserRepository;

@RestController
@RequestMapping("/question-follows")
public class QuestionFollowController {

    private final QuestionFollowRepository questionFollowRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public QuestionFollowController(
            QuestionFollowRepository questionFollowRepository,
            QuestionRepository questionRepository,
            UserRepository userRepository) {
        this.questionFollowRepository = questionFollowRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{questionId}")
    public QuestionFollow followQuestion(
            @PathVariable Long questionId,
            @RequestParam Long userId) {

        if (questionFollowRepository.existsByUserIdAndQuestionId(userId, questionId)) {
            throw new RuntimeException("You are already following this question.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found."));

        QuestionFollow follow = new QuestionFollow();
        follow.setUser(user);
        follow.setQuestion(question);

        return questionFollowRepository.save(follow);
    }

    @DeleteMapping("/{questionId}")
    public String unfollowQuestion(
            @PathVariable Long questionId,
            @RequestParam Long userId) {

        QuestionFollow follow = questionFollowRepository
                .findByUserIdAndQuestionId(userId, questionId)
                .orElseThrow(() -> new RuntimeException("Follow record not found."));

        questionFollowRepository.delete(follow);

        return "Question unfollowed successfully.";
    }

    @GetMapping("/user/{userId}")
    public List<QuestionFollow> getFollowedQuestions(
            @PathVariable Long userId) {

        return questionFollowRepository.findByUserId(userId);
    }

    @GetMapping("/count/{questionId}")
    public long getFollowerCount(
            @PathVariable Long questionId) {

        return questionFollowRepository.countByQuestionId(questionId);
    }
}