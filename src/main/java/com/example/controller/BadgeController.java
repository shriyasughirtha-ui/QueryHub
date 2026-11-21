package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Badge;
import com.example.repository.BadgeRepository;

@RestController
@RequestMapping("/badges")
public class BadgeController {

    private final BadgeRepository badgeRepository;

    public BadgeController(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    @GetMapping("/user/{userId}")
    public List<Badge> getUserBadges(@PathVariable Long userId) {
        return badgeRepository.findByUserId(userId);
    }
}
