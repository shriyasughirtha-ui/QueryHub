package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Answer;
import com.example.entity.Comment;
import com.example.entity.Notification;
import com.example.entity.Question;
import com.example.entity.User;
import com.example.repository.AnswerRepository;
import com.example.repository.CommentRepository;
import com.example.repository.NotificationRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.UserRepository;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final NotificationRepository notificationRepository;

    public CommentController(CommentRepository commentRepository,
                             UserRepository userRepository,
                             QuestionRepository questionRepository,
                             AnswerRepository answerRepository,
                             NotificationRepository notificationRepository) {

        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.notificationRepository = notificationRepository;
    }

   
@PostMapping
public Comment createComment(@RequestParam Long userId,
                             @RequestParam String content,
                             @RequestParam(required = false) Long questionId,
                             @RequestParam(required = false) Long answerId) {

    User user = userRepository.findById(userId).orElse(null);

    if (user == null) {
        return null;
    }

    if (content == null || content.trim().isEmpty()) {
        return null;
    }

    if ((questionId == null && answerId == null) ||
            (questionId != null && answerId != null)) {
        return null;
    }

    Comment comment = new Comment();
    comment.setContent(content.trim());
    comment.setUser(user);

    if (questionId != null) {
        Question question =
                questionRepository.findById(questionId).orElse(null);

        if (question == null) {
            return null;
        }

        comment.setQuestion(question);

        User questionOwner = question.getUser();

        if (questionOwner != null &&
                !questionOwner.getId().equals(userId)) {

            Notification notification = new Notification();
            notification.setUser(questionOwner);
            notification.setMessage("Someone commented on your question");

            notificationRepository.save(notification);
        }
    }

    if (answerId != null) {
        Answer answer =
                answerRepository.findById(answerId).orElse(null);

        if (answer == null) {
            return null;
        }

        comment.setAnswer(answer);

        User answerOwner = answer.getUser();

        if (answerOwner != null &&
                !answerOwner.getId().equals(userId)) {

            Notification notification = new Notification();
            notification.setUser(answerOwner);
            notification.setMessage("Someone commented on your answer");

            notificationRepository.save(notification);
        }
    }

    return commentRepository.save(comment);
}


   @PutMapping("/{id}")
public ResponseEntity<?> updateComment(
        @PathVariable Long id,
        @RequestParam Long userId,
        @RequestParam String content) {

    Comment comment = commentRepository.findById(id).orElse(null);

    if (comment == null) {
        return ResponseEntity.notFound().build();
    }

    if (comment.getUser() == null ||
            !comment.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("You can only edit your own comments");
    }

    if (content == null || content.trim().isEmpty()) {
        return ResponseEntity.badRequest()
                .body("Comment cannot be empty");
    }

    comment.setContent(content.trim());

    return ResponseEntity.ok(commentRepository.save(comment));
}

    @GetMapping("/question/{questionId}")
    public List<Comment> getQuestionComments(@PathVariable Long questionId) {
        return commentRepository.findByQuestionId(questionId);
    }

    @GetMapping("/answer/{answerId}")
    public List<Comment> getAnswerComments(@PathVariable Long answerId) {
        return commentRepository.findByAnswerId(answerId);
    }

    @GetMapping("/user/{userId}")
    public List<Comment> getUserComments(@PathVariable Long userId) {
        return commentRepository.findByUserId(userId);
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

 @DeleteMapping("/{id}")
public ResponseEntity<?> deleteComment(
        @PathVariable Long id,
        @RequestParam Long userId) {

    Comment comment = commentRepository.findById(id).orElse(null);

    if (comment == null) {
        return ResponseEntity.notFound().build();
    }

    if (comment.getUser() == null ||
            !comment.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("You can only delete your own comments");
    }

    commentRepository.delete(comment);

    return ResponseEntity.ok("Comment deleted successfully");
}
}