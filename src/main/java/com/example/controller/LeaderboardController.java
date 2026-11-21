package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.LeaderboardEntry;
import com.example.entity.User;
import com.example.repository.UserRepository;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final UserRepository userRepository;

    public LeaderboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<LeaderboardEntry> getLeaderboard() {

        List<User> users =
                userRepository.findAllByOrderByReputationDesc();

        return users.stream()
                .limit(10)
                .map(user -> new LeaderboardEntry(
                        user.getId(),
                        user.getName(),
                        user.getReputation()
                ))
                .toList();
    }
}