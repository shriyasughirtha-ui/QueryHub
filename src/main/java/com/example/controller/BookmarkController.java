
package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Bookmark;
import com.example.repository.BookmarkRepository;
import com.example.service.BookmarkService;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkService bookmarkService;

    public BookmarkController(
            BookmarkRepository bookmarkRepository,
            BookmarkService bookmarkService) {
        this.bookmarkRepository = bookmarkRepository;
        this.bookmarkService = bookmarkService;
    }

    @PostMapping
    public Bookmark addBookmark(
            @RequestParam Long userId,
            @RequestParam Long questionId) {
        return bookmarkService.addBookmark(userId, questionId);
    }

    @GetMapping("/user/{userId}")
    public List<Bookmark> getUserBookmarks(@PathVariable Long userId) {
        return bookmarkService.getUserBookmarks(userId);
    }

    @DeleteMapping
    public String deleteBookmark(
            @RequestParam Long userId,
            @RequestParam Long questionId) {
        return bookmarkService.deleteBookmark(userId, questionId);
    }

    @GetMapping("/check")
    public boolean checkBookmark(
            @RequestParam Long userId,
            @RequestParam Long questionId) {
        return bookmarkService.checkBookmark(userId, questionId);
    }

    @GetMapping("/count/{userId}")
    public long countUserBookmarks(@PathVariable Long userId) {
        return bookmarkService.countUserBookmarks(userId);
    }

    @DeleteMapping("/user/{userId}")
    public String deleteAllUserBookmarks(@PathVariable Long userId) {
        return bookmarkService.deleteAllUserBookmarks(userId);
    }

    @GetMapping("/{id}")
    public Bookmark getBookmarkById(@PathVariable Long id) {
        return bookmarkService.getBookmarkById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteBookmarkById(
            @PathVariable Long id,
            @RequestParam Long userId) {
        return bookmarkService.deleteBookmarkById(id, userId);
    }

    @GetMapping("/count/question/{questionId}")
    public long countBookmarksForQuestion(
            @PathVariable Long questionId) {
        return bookmarkService.countBookmarksForQuestion(questionId);
    }
}
