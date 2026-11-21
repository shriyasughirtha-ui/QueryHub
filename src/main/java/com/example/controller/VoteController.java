
package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Vote;
import com.example.service.VoteService;

@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public Vote castVote(
            @RequestParam Long userId,
            @RequestParam Long questionId,
            @RequestParam String voteType) {

        return voteService.castVote(userId, questionId, voteType);
    }
    
    @GetMapping("/count/{questionId}")
    public long getVoteCount(
            @PathVariable Long questionId,
            @RequestParam String voteType) {

        return voteService.getVoteCount(questionId, voteType);
    }
    
    @GetMapping("/history/{userId}")
    public java.util.List<Vote> getVotingHistory(
            @PathVariable Long userId) {

        return voteService.getVotingHistory(userId);
    }
    
    @PutMapping
    public Vote changeVote(
            @RequestParam Long userId,
            @RequestParam Long questionId,
            @RequestParam String voteType) {

        return voteService.changeVote(userId, questionId, voteType);
    }

   
}