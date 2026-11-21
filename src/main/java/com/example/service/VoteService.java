
package com.example.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Question;
import com.example.entity.User;
import com.example.entity.Vote;
import com.example.repository.QuestionRepository;
import com.example.repository.UserRepository;
import com.example.repository.VoteRepository;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public VoteService(
            VoteRepository voteRepository,
            UserRepository userRepository,
            QuestionRepository questionRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Vote castVote(Long userId, Long questionId, String voteType) {
        validateIds(userId, questionId);
        validateVoteType(voteType);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Question not found"));

        if (question.getUser() != null
                && question.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You cannot vote on your own question");
        }

        if (voteRepository.findByUserIdAndQuestionId(userId, questionId)
                .isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "You have already voted on this question");
        }

        Vote vote = new Vote();
        vote.setUser(user);
        vote.setQuestion(question);
        vote.setVoteType(voteType.toUpperCase());

        Vote savedVote = voteRepository.save(vote);
        syncQuestionVoteCounts(question);

        return savedVote;
    }

    public long getVoteCount(Long questionId, String voteType) {
        if (questionId == null || questionId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Question ID must be positive");
        }

        validateVoteType(voteType);

        if (!questionRepository.existsById(questionId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Question not found");
        }

        return voteRepository.countByQuestionIdAndVoteType(
                questionId, voteType.toUpperCase());
    }

    public List<Vote> getVotingHistory(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User ID must be positive");
        }

        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found");
        }

        return voteRepository.findByUserId(userId);
    }

@Transactional
public Vote changeVote(Long userId, Long questionId, String voteType) {
    validateIds(userId, questionId);
    validateVoteType(voteType);

    Vote vote = voteRepository
            .findByUserIdAndQuestionId(userId, questionId)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Vote not found"));

    String newVoteType = voteType.toUpperCase();

    if (vote.getVoteType().equalsIgnoreCase(newVoteType)) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "You have already selected this vote type");
    }

    vote.setVoteType(newVoteType);

    Vote updatedVote = voteRepository.save(vote);
    syncQuestionVoteCounts(vote.getQuestion());

    return updatedVote;
}

    private void validateIds(Long userId, Long questionId) {
        if (userId == null || userId <= 0
                || questionId == null || questionId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User ID and question ID must be positive");
        }
    }

    private void validateVoteType(String voteType) {
        if (!"UPVOTE".equalsIgnoreCase(voteType)
                && !"DOWNVOTE".equalsIgnoreCase(voteType)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Vote type must be UPVOTE or DOWNVOTE");
        }
    }

    private void syncQuestionVoteCounts(Question question) {
        question.setUpvotes((int) voteRepository
                .countByQuestionIdAndVoteType(
                        question.getId(), "UPVOTE"));

        question.setDownvotes((int) voteRepository
                .countByQuestionIdAndVoteType(
                        question.getId(), "DOWNVOTE"));

        questionRepository.save(question);
    }
}
