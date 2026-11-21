
package com.example.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Bookmark;
import com.example.entity.Question;
import com.example.entity.User;
import com.example.repository.BookmarkRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.UserRepository;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public BookmarkService(
            BookmarkRepository bookmarkRepository,
            UserRepository userRepository,
            QuestionRepository questionRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Bookmark addBookmark(Long userId, Long questionId) {
        validateIds(userId, questionId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Question not found"));

        if (bookmarkRepository.findByUserIdAndQuestionId(
                userId, questionId).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Question is already bookmarked");
        }

        if (bookmarkRepository.countByUserId(userId) >= 50) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User has reached the maximum number of bookmarks");
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setQuestion(question);

        return bookmarkRepository.save(bookmark);
    }

    public List<Bookmark> getUserBookmarks(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User ID must be positive");
        }

        return bookmarkRepository.findByUserId(userId);
    }

    @Transactional
    public String deleteBookmark(Long userId, Long questionId) {
        validateIds(userId, questionId);

        Bookmark bookmark = bookmarkRepository
                .findByUserIdAndQuestionId(userId, questionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Bookmark not found"));

        bookmarkRepository.delete(bookmark);
        return "Bookmark removed successfully";
    }

    public boolean checkBookmark(Long userId, Long questionId) {
        if (userId == null || questionId == null
                || userId <= 0 || questionId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User ID and question ID must be positive");
        }

        return bookmarkRepository
                .findByUserIdAndQuestionId(userId, questionId)
                .isPresent();
    }

    public long countUserBookmarks(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User ID must be positive");
        }

        return bookmarkRepository.countByUserId(userId);
    }

    @Transactional
    public String deleteAllUserBookmarks(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User ID must be positive");
        }

        if (bookmarkRepository.countByUserId(userId) == 0) {
            return "No bookmarks found for this user";
        }

        bookmarkRepository.deleteByUserId(userId);
        return "All bookmarks removed successfully";
    }

    public Bookmark getBookmarkById(Long id) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Bookmark ID must be positive");
        }

        return bookmarkRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Bookmark not found"));
    }

    @Transactional
    public String deleteBookmarkById(Long id, Long userId) {
        if (id == null || id <= 0 || userId == null || userId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Bookmark ID and user ID must be positive");
        }

        Bookmark bookmark = bookmarkRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Bookmark not found"));

        if (bookmark.getUser() == null
                || !bookmark.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot delete another user's bookmark");
        }

        bookmarkRepository.delete(bookmark);
        return "Bookmark deleted successfully";
    }

    public long countBookmarksForQuestion(Long questionId) {
        if (questionId == null || questionId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Question ID must be positive");
        }

        return bookmarkRepository.countByQuestionId(questionId);
    }

    private void validateIds(Long userId, Long questionId) {
        if (userId == null || questionId == null
                || userId <= 0 || questionId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User ID and question ID must be positive");
        }
    }
}
