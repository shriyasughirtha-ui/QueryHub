package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByQuestionId(Long questionId);

    List<Comment> findByAnswerId(Long answerId);

    List<Comment> findByUserId(Long userId);
    long countByQuestionId(Long questionId);
}