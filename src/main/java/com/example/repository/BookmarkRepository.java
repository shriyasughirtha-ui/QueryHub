package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUserId(Long userId);

    Optional<Bookmark> findByUserIdAndQuestionId(Long userId, Long questionId);
    
long countByUserId(Long userId);

void deleteByUserId(Long userId);
long countByQuestionId(Long questionId);
}
