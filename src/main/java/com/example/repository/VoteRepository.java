
package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUserIdAndQuestionId(Long userId, Long questionId);

    long countByQuestionIdAndVoteType(Long questionId, String voteType);

    long countByUserId(Long userId);
    
    List<Vote> findByUserId(Long userId);
}
