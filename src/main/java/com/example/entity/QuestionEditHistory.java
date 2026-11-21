
package com.example.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "question_edit_history")
public class QuestionEditHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String previousTitle;

    private String previousDescription;

    private String previousCategory;

    private String previousTags;

    private LocalDateTime editedAt;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreviousTitle() {
        return previousTitle;
    }

    public void setPreviousTitle(String previousTitle) {
        this.previousTitle = previousTitle;
    }

    public String getPreviousDescription() {
        return previousDescription;
    }

    public void setPreviousDescription(String previousDescription) {
        this.previousDescription = previousDescription;
    }

    public String getPreviousCategory() {
        return previousCategory;
    }

    public void setPreviousCategory(String previousCategory) {
        this.previousCategory = previousCategory;
    }

    public String getPreviousTags() {
        return previousTags;
    }

    public void setPreviousTags(String previousTags) {
        this.previousTags = previousTags;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
