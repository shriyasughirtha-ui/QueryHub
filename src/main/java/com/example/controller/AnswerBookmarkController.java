
package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.AnswerBookmark;
import com.example.service.AnswerBookmarkService;

@RestController
@RequestMapping("/answer-bookmarks")
public class AnswerBookmarkController {

    private final AnswerBookmarkService bookmarkService;

    public AnswerBookmarkController(
            AnswerBookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping
    public AnswerBookmark addBookmark(
            @RequestParam Long userId,
            @RequestParam Long answerId) {
        return bookmarkService.addBookmark(userId, answerId);
    }

    @GetMapping("/user/{userId}")
    public List<AnswerBookmark> getUserBookmarks(
            @PathVariable Long userId) {
        return bookmarkService.getUserBookmarks(userId);
    }

    @DeleteMapping
    public String deleteBookmark(
            @RequestParam Long userId,
            @RequestParam Long answerId) {
        return bookmarkService.deleteBookmark(userId, answerId);
    }

    @GetMapping("/check")
    public boolean checkBookmark(
            @RequestParam Long userId,
            @RequestParam Long answerId) {
        return bookmarkService.checkBookmark(userId, answerId);
    }
}
