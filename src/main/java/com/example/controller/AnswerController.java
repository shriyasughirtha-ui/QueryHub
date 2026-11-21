package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Answer;
import com.example.entity.AnswerVote;
import com.example.entity.Notification;
import com.example.entity.Question;
import com.example.entity.QuestionFollow;
import com.example.entity.ReputationHistory;
import com.example.entity.User;
import com.example.repository.AnswerRepository;
import com.example.repository.AnswerVoteRepository;
import com.example.repository.NotificationRepository;
import com.example.repository.QuestionFollowRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.ReputationHistoryRepository;
import com.example.repository.UserRepository;
import com.example.service.AnswerEditHistoryService;
import com.example.service.BadgeService;
@RestController
@RequestMapping("/answers")
public class AnswerController {

private final AnswerRepository answerRepository;
private final BadgeService badgeService;
private final UserRepository userRepository;
private final QuestionRepository questionRepository;
private final QuestionFollowRepository questionFollowRepository;
private final NotificationRepository notificationRepository;
private final AnswerVoteRepository answerVoteRepository;
private final ReputationHistoryRepository reputationHistoryRepository;
private final AnswerEditHistoryService answerEditHistoryService;

public AnswerController(AnswerRepository answerRepository,
                        QuestionRepository questionRepository,
                        NotificationRepository notificationRepository,
                        UserRepository userRepository,
                        QuestionFollowRepository questionFollowRepository,
                        BadgeService badgeService,
                        AnswerVoteRepository answerVoteRepository,
                        AnswerEditHistoryService answerEditHistoryService,
                        ReputationHistoryRepository reputationHistoryRepository) {

    this.answerRepository = answerRepository;
    this.questionRepository = questionRepository;
    this.userRepository = userRepository;
    this.notificationRepository = notificationRepository;
    this.questionFollowRepository = questionFollowRepository;
    this.answerVoteRepository = answerVoteRepository;
    this.badgeService = badgeService;
    this.answerEditHistoryService = answerEditHistoryService;
    this.reputationHistoryRepository = reputationHistoryRepository;
}

    @PostMapping
public Answer createAnswer(@RequestParam Long userId,
                           @RequestParam Long questionId,
                           @RequestParam String content) {

    User user = userRepository.findById(userId).orElse(null);
    Question question = questionRepository.findById(questionId).orElse(null);

    if (user == null || question == null) {
        return null;
    }

    Answer answer = new Answer();
    answer.setContent(content);
    answer.setAccepted(false);
    answer.setUpvotes(0);
    answer.setDownvotes(0);
    answer.setUser(user);
    answer.setQuestion(question);

    Answer savedAnswer = answerRepository.save(answer);
    badgeService.checkBadges(user);

    User questionOwner = question.getUser();

    if (questionOwner != null && !questionOwner.getId().equals(userId)) {
        Notification notification = new Notification();
        notification.setUser(questionOwner);
        notification.setMessage(
    "Someone answered your question: " + question.getTitle()
);
        notification.setRead(false);

        notificationRepository.save(notification);
    }
    List<QuestionFollow> followers =
        questionFollowRepository.findByQuestionId(questionId);

for (QuestionFollow follow : followers) {
    User follower = follow.getUser();

    if (follower != null
            && !follower.getId().equals(userId)
            && (questionOwner == null
                || !follower.getId().equals(questionOwner.getId()))) {

        Notification followerNotification = new Notification();
        followerNotification.setUser(follower);
        followerNotification.setMessage(
                "New answer posted on a question you follow: "
                        + question.getTitle()
        );
        followerNotification.setRead(false);

        notificationRepository.save(followerNotification);
    }
}

    return savedAnswer;
}
    @GetMapping
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Answer getAnswer(@PathVariable Long id) {
        return answerRepository.findById(id).orElse(null);
    }

@GetMapping("/edit-history/{id}")
public List<com.example.entity.AnswerEditHistory> getAnswerEditHistory(
        @PathVariable Long id) {

    return answerEditHistoryService.getAnswerEditHistory(id);
}
@PutMapping("/{id}/restore/{historyId}")
public ResponseEntity<?> restoreAnswerVersion(
        @PathVariable Long id,
        @PathVariable Long historyId,
        @RequestParam Long userId) {

    Answer answer = answerRepository.findById(id).orElse(null);

    if (answer == null) {
        return ResponseEntity.notFound().build();
    }

    if (answer.getUser() == null ||
            !answer.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("You can only restore your own answer");
    }

    com.example.entity.AnswerEditHistory history =
            answerEditHistoryService.getAnswerEditHistory(id)
                    .stream()
                    .filter(item -> item.getId().equals(historyId))
                    .findFirst()
                    .orElse(null);

    if (history == null) {
        return ResponseEntity.notFound().build();
    }

    answerEditHistoryService.recordEdit(answer, answer.getContent());

    answer.setContent(history.getPreviousContent());

    return ResponseEntity.ok(answerRepository.save(answer));
}

    @GetMapping("/question/{questionId}")
    public List<Answer> getAnswersByQuestion(@PathVariable Long questionId) {

        Question question = questionRepository.findById(questionId).orElse(null);

        if (question == null) {
            return List.of();
        }

        return answerRepository.findByQuestionId(questionId);
    }

@PutMapping("/{id}/accept")
public ResponseEntity<?> acceptAnswer(
        @PathVariable Long id,
        @RequestParam Long userId) {

    Answer answer = answerRepository.findById(id).orElse(null);

    if (answer == null) {
        return ResponseEntity.notFound().build();
    }

    Question question = answer.getQuestion();

    if (question == null || question.getUser() == null) {
        return ResponseEntity.badRequest()
                .body("Question or question owner not found");
    }

    // Only the question owner can accept an answer
    if (!question.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("Only the question owner can accept an answer");
    }

    // The question owner cannot accept their own answer
    if (answer.getUser() == null) {
        return ResponseEntity.badRequest()
                .body("Answer owner not found");
    }

    if (answer.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("You cannot accept your own answer");
    }

    if ("CLOSED".equalsIgnoreCase(question.getStatus())) {
        return ResponseEntity.badRequest()
                .body("This question is already closed");
    }

    answer.setAccepted(true);
    question.setStatus("CLOSED");

    User answerUser = answer.getUser();

    User existingUser = userRepository.findById(answerUser.getId())
            .orElse(null);

    if (existingUser != null) {
        existingUser.setReputation(existingUser.getReputation() + 10);

        userRepository.save(existingUser);
        badgeService.checkBadges(existingUser);

        Notification notification = new Notification();
        notification.setUser(existingUser);
        notification.setMessage("Your answer was accepted");
        notification.setRead(false);

        notificationRepository.save(notification);

        ReputationHistory history = new ReputationHistory();
        history.setUser(existingUser);
        history.setChangeAmount(10);
        history.setReason("Answer accepted");

        reputationHistoryRepository.save(history);
    }

    questionRepository.save(question);
    Answer savedAnswer = answerRepository.save(answer);
    badgeService.checkBadges(answer.getUser());

    return ResponseEntity.ok(savedAnswer);
}
    @PutMapping("/{id}/upvote")
public String upvoteAnswer(@PathVariable Long id,
                           @RequestParam Long userId) {

    Answer answer = answerRepository.findById(id).orElse(null);
    User user = userRepository.findById(userId).orElse(null);

    if (answer == null) {
        return "Answer not found";
    }

    if (user == null) {
        return "User not found";
    }

    if (answerVoteRepository.findByUserIdAndAnswerId(userId, id).isPresent()) {
        return "You have already voted on this answer";
    }

    AnswerVote vote = new AnswerVote();
    vote.setUser(user);
    vote.setAnswer(answer);
    vote.setVoteType("UP");

    answerVoteRepository.save(vote);

    answer.setUpvotes(answer.getUpvotes() + 1);
answerRepository.save(answer);

User answerOwner = answer.getUser();

if (answerOwner != null && !answerOwner.getId().equals(userId)) {

answerOwner.setReputation(answerOwner.getReputation() + 5);
userRepository.save(answerOwner);

ReputationHistory history = new ReputationHistory();
history.setUser(answerOwner);
history.setChangeAmount(5);
history.setReason("Someone upvoted your answer");

reputationHistoryRepository.save(history);

Notification notification = new Notification();
    notification.setUser(answerOwner);
    notification.setMessage(
    "Someone upvoted your answer on: "
        + answer.getQuestion().getTitle()
);
    notification.setRead(false);

    notificationRepository.save(notification);
}
return "Answer upvoted successfully";
}

@PutMapping("/{id}/downvote")
public String downvoteAnswer(@PathVariable Long id,
                             @RequestParam Long userId) {

    Answer answer = answerRepository.findById(id).orElse(null);
    User user = userRepository.findById(userId).orElse(null);

    if (answer == null) {
        return "Answer not found";
    }

    if (user == null) {
        return "User not found";
    }

    if (answerVoteRepository.findByUserIdAndAnswerId(userId, id).isPresent()) {
        return "You have already voted on this answer";
    }

    AnswerVote vote = new AnswerVote();
    vote.setUser(user);
    vote.setAnswer(answer);
    vote.setVoteType("DOWN");

    answerVoteRepository.save(vote);

    answer.setDownvotes(answer.getDownvotes() + 1);
    answerRepository.save(answer);

    User answerOwner = answer.getUser();

    if (answerOwner != null && !answerOwner.getId().equals(userId)) {

        if (answerOwner.getReputation() > 0) {
    answerOwner.setReputation(answerOwner.getReputation() - 1);
    userRepository.save(answerOwner);

    ReputationHistory history = new ReputationHistory();
    history.setUser(answerOwner);
    history.setChangeAmount(-1);
    history.setReason("Someone downvoted your answer");

    reputationHistoryRepository.save(history);
}

        Notification notification = new Notification();
        notification.setUser(answerOwner);
        notification.setMessage(
    "Someone downvoted your answer on: "
        + answer.getQuestion().getTitle()
);
        notification.setRead(false);

        notificationRepository.save(notification);
    }

    return "Answer downvoted successfully";
}
@PutMapping("/{id}")
public ResponseEntity<?> updateAnswer(
        @PathVariable Long id,
        @RequestParam Long userId,
        @RequestBody Answer updatedAnswer) {

    Answer answer = answerRepository.findById(id).orElse(null);

    if (answer == null) {
        return ResponseEntity.notFound().build();
    }

    if (answer.getUser() == null) {
        return ResponseEntity.badRequest()
                .body("Answer owner not found");
    }

    if (!answer.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("You can only edit your own answer");
    }

    if (updatedAnswer.getContent() == null ||
            updatedAnswer.getContent().trim().isEmpty()) {
        return ResponseEntity.badRequest()
                .body("Answer content cannot be empty");
    }

   answerEditHistoryService.recordEdit(
        answer, answer.getContent());

answer.setContent(updatedAnswer.getContent().trim());

    return ResponseEntity.ok(answerRepository.save(answer));
}
   @DeleteMapping("/{id}")
public ResponseEntity<?> deleteAnswer(
        @PathVariable Long id,
        @RequestParam Long userId) {

    Answer answer = answerRepository.findById(id).orElse(null);

    if (answer == null) {
        return ResponseEntity.notFound().build();
    }

    if (answer.getUser() == null) {
        return ResponseEntity.badRequest()
                .body("Answer owner not found");
    }

    if (!answer.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("You can only delete your own answer");
    }

    answerRepository.delete(answer);

    return ResponseEntity.ok("Answer deleted successfully");
}
    @GetMapping("/sort/upvotes")
public List<Answer> getMostUpvotedAnswers() {
    return answerRepository.findAllByOrderByUpvotesDesc();
}
@GetMapping("/sort/accepted")
public List<Answer> getAcceptedAnswersFirst() {
    return answerRepository.findAllByOrderByAcceptedDesc();
}
@GetMapping("/sort/newest")
public List<Answer> getNewestAnswers() {
    return answerRepository.findAllByOrderByIdDesc();
}
@GetMapping("/sort/downvotes")
public List<Answer> getMostDownvotedAnswers() {
    return answerRepository.findAllByOrderByDownvotesDesc();
}
@GetMapping("/search")
public List<Answer> searchAnswers(@RequestParam String keyword) {
    return answerRepository.findByContentContainingIgnoreCase(keyword);
}
@GetMapping("/user/{userId}")
public List<Answer> getAnswersByUser(@PathVariable Long userId) {
    return answerRepository.findByUserId(userId);
}
@GetMapping("/user/{userId}/accepted")
public List<Answer> getAcceptedAnswersByUser(@PathVariable Long userId) {
    return answerRepository.findByUserIdAndAcceptedTrue(userId);
}
@GetMapping("/user/{userId}/count")
public long getAnswerCountByUser(@PathVariable Long userId) {
    return answerRepository.findByUserId(userId).size();
}
@GetMapping("/question/{questionId}/count")
public long getAnswerCountByQuestion(@PathVariable Long questionId) {
    return answerRepository.findByQuestionId(questionId).size();
}
@PutMapping("/{id}/pin")
public ResponseEntity<?> togglePinAnswer(
        @PathVariable Long id,
        @RequestParam Long userId) {

    Answer answer = answerRepository.findById(id).orElse(null);

    if (answer == null) {
        return ResponseEntity.notFound().build();
    }

    if (answer.getQuestion() == null ||
            answer.getQuestion().getUser() == null ||
            !answer.getQuestion().getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("Only the question owner can pin answers");
    }

    boolean pin = !answer.isPinned();

    if (pin) {
        List<Answer> answers =
                answerRepository.findByQuestionId(
                        answer.getQuestion().getId());

        for (Answer item : answers) {
            item.setPinned(false);
        }

        answerRepository.saveAll(answers);
    }

    answer.setPinned(pin);

    return ResponseEntity.ok(answerRepository.save(answer));
}
}