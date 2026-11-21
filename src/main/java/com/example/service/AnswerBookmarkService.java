
package com.example.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Answer;
import com.example.entity.AnswerBookmark;
import com.example.entity.User;
import com.example.repository.AnswerBookmarkRepository;
import com.example.repository.AnswerRepository;
import com.example.repository.UserRepository;

@Service
public class AnswerBookmarkService {

    private final AnswerBookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    public AnswerBookmarkService(
            AnswerBookmarkRepository bookmarkRepository,
            UserRepository userRepository,
            AnswerRepository answerRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
    }

    @Transactional
    public AnswerBookmark addBookmark(Long userId, Long answerId) {

        if (userId == null || answerId == null
                || userId <= 0 || answerId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User ID and answer ID must be positive");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Answer not found"));

        if (bookmarkRepository.existsByUserIdAndAnswerId(userId, answerId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Answer is already bookmarked");
        }

        if (bookmarkRepository.findByUserId(userId).size() >= 50) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Maximum number of bookmarks reached");
        }

        AnswerBookmark bookmark = new AnswerBookmark();
        bookmark.setUser(user);
        bookmark.setAnswer(answer);

        return bookmarkRepository.save(bookmark);
    }

    public List<AnswerBookmark> getUserBookmarks(Long userId) {

        if (userId == null || userId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User ID must be positive");
        }

        return bookmarkRepository.findByUserId(userId);
    }

    @Transactional
    public String deleteBookmark(Long userId, Long answerId) {

        if (userId == null || answerId == null
                || userId <= 0 || answerId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User ID and answer ID must be positive");
        }

        if (!bookmarkRepository.existsByUserIdAndAnswerId(userId, answerId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Bookmark not found");
        }

        bookmarkRepository.deleteByUserIdAndAnswerId(userId, answerId);

        return "Answer bookmark removed successfully";
    }

    public boolean checkBookmark(Long userId, Long answerId) {

        if (userId == null || answerId == null
                || userId <= 0 || answerId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User ID and answer ID must be positive");
        }

        return bookmarkRepository.existsByUserIdAndAnswerId(
                userId, answerId);
    }
}
