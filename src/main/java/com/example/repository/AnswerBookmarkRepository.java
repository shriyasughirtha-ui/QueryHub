
package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.AnswerBookmark;

public interface AnswerBookmarkRepository extends JpaRepository<AnswerBookmark, Long> {

    boolean existsByUserIdAndAnswerId(Long userId, Long answerId);

    List<AnswerBookmark> findByUserId(Long userId);

    void deleteByUserIdAndAnswerId(Long userId, Long answerId);
}

