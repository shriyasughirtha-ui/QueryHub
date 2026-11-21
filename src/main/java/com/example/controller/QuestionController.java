package com.example.controller;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import com.example.dto.QuestionAnalyticsDTO;
import com.example.dto.QuestionEngagementDTO;
import com.example.dto.QuestionFollowersCountDTO;
import com.example.dto.QuestionRecommendationDTO;
import com.example.dto.TrendingQuestionDTO;
import com.example.entity.Notification;
import com.example.entity.Question;
import com.example.entity.QuestionEditHistory;
import com.example.entity.QuestionFollow;
import com.example.entity.QuestionView;
import com.example.entity.ReputationHistory;
import com.example.entity.User;
import com.example.repository.AnswerRepository;
import com.example.repository.CommentRepository;
import com.example.repository.NotificationRepository;
import com.example.repository.QuestionEditHistoryRepository;
import com.example.repository.QuestionFollowRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.QuestionViewRepository;
import com.example.repository.QuestionVoteRepository;
import com.example.repository.ReputationHistoryRepository;
import com.example.repository.UserRepository;
import com.example.service.BadgeService;
import com.example.service.QuestionStatusHistoryService;
import com.example.service.VoteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionFollowRepository questionFollowRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final BadgeService badgeService;
    private final QuestionStatusHistoryService questionStatusHistoryService;
    private final CommentRepository commentRepository;
    private final QuestionVoteRepository questionVoteRepository;
    private final QuestionEditHistoryRepository questionEditHistoryRepository;
    private final NotificationRepository notificationRepository;
    private final ReputationHistoryRepository reputationHistoryRepository;
    private final QuestionViewRepository questionViewRepository;
    private final VoteService voteService;

   
public QuestionController(QuestionRepository questionRepository,
                          AnswerRepository answerRepository,
                          UserRepository userRepository,
                          QuestionStatusHistoryService questionStatusHistoryService,
                          QuestionFollowRepository questionFollowRepository,
                          QuestionVoteRepository questionVoteRepository,
                          CommentRepository commentRepository,
                           QuestionEditHistoryRepository questionEditHistoryRepository,
                          NotificationRepository notificationRepository,
                          ReputationHistoryRepository reputationHistoryRepository,
                          QuestionViewRepository questionViewRepository,
                          VoteService voteService,
                          BadgeService badgeService) {

    this.questionRepository = questionRepository;
    this.answerRepository = answerRepository;
    this.userRepository = userRepository;
    this.questionStatusHistoryService = questionStatusHistoryService;
    this.questionEditHistoryRepository = questionEditHistoryRepository;
    this.questionVoteRepository = questionVoteRepository;
    this.commentRepository = commentRepository;
    this.notificationRepository = notificationRepository;
    this.reputationHistoryRepository = reputationHistoryRepository;
    this.questionViewRepository = questionViewRepository;
    this.questionFollowRepository = questionFollowRepository;
    this.voteService = voteService;
    this.badgeService = badgeService;
}



@PostMapping
public Question createQuestion(
        @Valid @RequestBody Question question,
        @RequestParam Long userId) {

    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                    new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND,
                            "User not found"));

    question.setUser(user);
    question.setStatus("OPEN");
    question.setViews(0);
    question.setUpvotes(0);
    question.setDownvotes(0);
    question.setShares(0);

    Question savedQuestion = questionRepository.save(question);
    questionStatusHistoryService.recordStatusChange(
        savedQuestion,
        "CREATED",
        "OPEN"
);

badgeService.checkBadges(user);

return savedQuestion;
}
@PutMapping("/{id}/expiry")
public ResponseEntity<?> setQuestionExpiry(
        @PathVariable Long id,
        @RequestParam Long userId,
        @RequestParam java.time.LocalDateTime expiresAt) {

    Question question = questionRepository.findById(id)
            .orElseThrow(() ->
                    new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND,
                            "Question not found"));

    if (question.getUser() == null
            || !question.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("You can only set expiry for your own question");
    }

    if (!"OPEN".equalsIgnoreCase(question.getStatus())) {
        return ResponseEntity.badRequest()
                .body("Only open questions can have an expiry date");
    }

    if (!expiresAt.isAfter(java.time.LocalDateTime.now())) {
        return ResponseEntity.badRequest()
                .body("Expiry date must be in the future");
    }

    question.setExpiresAt(expiresAt);

    return ResponseEntity.ok(questionRepository.save(question));
}
@PostMapping("/drafts")
public Question saveDraft(
        @RequestBody Question question,
        @RequestParam Long userId) {

    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                    new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND,
                            "User not found"));

    question.setUser(user);
    question.setStatus("DRAFT");
    question.setViews(0);
    question.setUpvotes(0);
    question.setDownvotes(0);
    question.setShares(0);

    return questionRepository.save(question);
}

    @GetMapping
public List<Question> getAllQuestions() {
    return questionRepository.findByStatusIgnoreCase("OPEN");
}

    @GetMapping("/search")
    public List<Question> searchQuestions(@RequestParam String keyword) {
        return questionRepository.findByTitleContainingIgnoreCaseAndStatusIgnoreCase(keyword, "OPEN");
    }

    @GetMapping("/category/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable String category) {
        return questionRepository.findByCategoryIgnoreCase(category);
    }

 @GetMapping("/{id}")
public Question getQuestion(@PathVariable Long id,
                            @RequestParam Long userId) {

    Question question = questionRepository.findById(id).orElse(null);
    User user = userRepository.findById(userId).orElse(null);

    if (question == null) {
        return null;
    }

    if (user != null) {

        QuestionView questionView = new QuestionView();
        questionView.setQuestion(question);
        questionView.setUser(user);
        questionView.setViewedAt(java.time.LocalDateTime.now());

        questionViewRepository.save(questionView);
    }

    question.setViews(question.getViews() + 1);
    questionRepository.save(question);

    return question;
}


@PutMapping("/{id}/upvote")
public String upvoteQuestion(@PathVariable Long id,
                             @RequestParam Long userId) {

    Question question = questionRepository.findById(id).orElse(null);
    User user = userRepository.findById(userId).orElse(null);

    if (question == null) {
        return "Question not found";
    }

    if (user == null) {
        return "User not found";
    }

    if (question.getUser() != null &&
            question.getUser().getId().equals(userId)) {
        return "You cannot upvote your own question";
    }

    try {
        voteService.castVote(userId, id, "UPVOTE");
    } catch (org.springframework.web.server.ResponseStatusException e) {
        if (e.getStatusCode().value() == 409) {
            return "You have already voted on this question";
        }
        return e.getReason() != null ? e.getReason() : "Unable to upvote question";
    }

    User questionOwner = question.getUser();

    if (questionOwner != null && !questionOwner.getId().equals(userId)) {
        questionOwner.setReputation(questionOwner.getReputation() + 2);
        userRepository.save(questionOwner);

        ReputationHistory history = new ReputationHistory();
        history.setUser(questionOwner);
        history.setChangeAmount(2);
        history.setReason("Someone upvoted your question");
        reputationHistoryRepository.save(history);

        Notification notification = new Notification();
        notification.setUser(questionOwner);
        notification.setMessage("Someone upvoted your question");
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    return "Question upvoted successfully";
}

@PutMapping("/{id}/downvote")
public String downvoteQuestion(
        @PathVariable Long id,
        @RequestParam Long userId) {

    Question question = questionRepository.findById(id).orElse(null);
    User user = userRepository.findById(userId).orElse(null);

    if (question == null) {
        return "Question not found";
    }

    if (user == null) {
        return "User not found";
    }

    voteService.castVote(userId, id, "DOWNVOTE");

    question.setDownvotes(question.getDownvotes() + 1);
    questionRepository.save(question);

    User questionOwner = question.getUser();

    if (questionOwner != null && !questionOwner.getId().equals(userId)) {

        if (questionOwner.getReputation() > 0) {
            questionOwner.setReputation(questionOwner.getReputation() - 1);
            userRepository.save(questionOwner);

            ReputationHistory history = new ReputationHistory();
            history.setUser(questionOwner);
            history.setChangeAmount(-1);
            history.setReason("Someone downvoted your question");

            reputationHistoryRepository.save(history);
        }

        Notification notification = new Notification();
        notification.setUser(questionOwner);
        notification.setMessage("Someone downvoted your question");
        notification.setRead(false);

        notificationRepository.save(notification);
    }

    return "Question downvoted successfully";
}

    
@PutMapping("/{id}")
public Question updateQuestion(
        @PathVariable Long id,
        @RequestParam Long userId,
        @RequestBody Question updatedQuestion) {

    if (id <= 0 || userId <= 0) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Question ID and user ID must be positive");
    }

    Question question = questionRepository.findById(id)
            .orElseThrow(() ->
                    new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND,
                            "Question not found"));

    if (question.getUser() == null
            || !question.getUser().getId().equals(userId)) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.FORBIDDEN,
                "You can only edit your own questions");
    }

   QuestionEditHistory history = new QuestionEditHistory();

history.setPreviousTitle(question.getTitle());
history.setPreviousDescription(question.getDescription());
history.setPreviousCategory(question.getCategory());
history.setPreviousTags(question.getTags());
history.setEditedAt(java.time.LocalDateTime.now());
history.setQuestion(question);

questionEditHistoryRepository.save(history);

question.setTitle(updatedQuestion.getTitle());
question.setDescription(updatedQuestion.getDescription());
question.setCategory(updatedQuestion.getCategory());
question.setTags(updatedQuestion.getTags());

    return questionRepository.save(question);
}
@GetMapping("/user/{userId}")
public List<Question> getQuestionsByUser(@PathVariable Long userId) {
    return questionRepository.findByUserId(userId);
}
@GetMapping("/closed/{userId}")
public List<Question> getClosedQuestions(@PathVariable Long userId) {
    return questionRepository.findByUserIdAndStatusIgnoreCase(
            userId, "CLOSED");
}
@GetMapping("/drafts/{userId}")
public List<Question> getUserDrafts(@PathVariable Long userId) {

    return questionRepository.findByUserIdAndStatusIgnoreCase(
            userId,
            "DRAFT"
    );
}
@GetMapping("/scheduled/{userId}")
public List<Question> getScheduledQuestions(@PathVariable Long userId) {

    return questionRepository.findByUserIdAndStatusIgnoreCase(
            userId,
            "SCHEDULED"
    );
}
@PutMapping("/scheduled/{id}/cancel")
public ResponseEntity<?> cancelScheduledQuestion(
        @PathVariable Long id,
        @RequestParam Long userId) {
            System.out.println("CANCEL SCHEDULED ENDPOINT CALLED: " + id);

    Question question = questionRepository.findById(id)
            .orElseThrow(() ->
                    new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND,
                            "Scheduled question not found"));

    if (question.getUser() == null
            || !question.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("You can only cancel your own scheduled questions");
    }

    if (!"SCHEDULED".equalsIgnoreCase(question.getStatus())) {
        return ResponseEntity.badRequest()
                .body("This question is not scheduled");
    }

   String previousStatus = question.getStatus();

question.setStatus("DRAFT");
question.setScheduledAt(null);

Question cancelledQuestion = questionRepository.save(question);

questionStatusHistoryService.recordStatusChange(
        cancelledQuestion,
        previousStatus,
        "DRAFT"
);

return ResponseEntity.ok(cancelledQuestion);
}
@PutMapping("/drafts/{id}")
public ResponseEntity<?> updateDraft(
        @PathVariable Long id,
        @RequestParam Long userId,
        @RequestBody Question updatedQuestion) {

    Question question = questionRepository.findById(id)
            .orElseThrow(() ->
                    new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND,
                            "Draft not found"));

    if (question.getUser() == null
            || !question.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("You can only edit your own draft");
    }

    if (!"DRAFT".equalsIgnoreCase(question.getStatus())) {
        return ResponseEntity.badRequest()
                .body("This question is not a draft");
    }

    question.setTitle(updatedQuestion.getTitle());
    question.setDescription(updatedQuestion.getDescription());
    question.setCategory(updatedQuestion.getCategory());
    question.setTags(updatedQuestion.getTags());

    return ResponseEntity.ok(questionRepository.save(question));
}
@PutMapping("/drafts/{id}/publish")
public ResponseEntity<?> publishDraft(
        @PathVariable Long id,
        @RequestParam Long userId) {

    Question question = questionRepository.findById(id)
            .orElseThrow(() ->
                    new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND,
                            "Draft not found"));

    if (question.getUser() == null
            || !question.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("You can only publish your own draft");
    }

    if (!"DRAFT".equalsIgnoreCase(question.getStatus())) {
        return ResponseEntity.badRequest()
                .body("This question is not a draft");
    }

  String previousStatus = question.getStatus();

question.setStatus("OPEN");

Question publishedQuestion = questionRepository.save(question);

questionStatusHistoryService.recordStatusChange(
        publishedQuestion,
        previousStatus,
        "OPEN"
);

return ResponseEntity.ok(publishedQuestion);
}
@PutMapping("/drafts/{id}/schedule")
public ResponseEntity<?> scheduleDraft(
        @PathVariable Long id,
        @RequestParam Long userId,
        @RequestParam java.time.LocalDateTime scheduledAt) {

    Question question = questionRepository.findById(id)
            .orElseThrow(() ->
                    new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND,
                            "Draft not found"));

    if (question.getUser() == null
            || !question.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("You can only schedule your own draft");
    }

    if (!"DRAFT".equalsIgnoreCase(question.getStatus())) {
        return ResponseEntity.badRequest()
                .body("Only drafts can be scheduled");
    }

    if (!scheduledAt.isAfter(java.time.LocalDateTime.now())) {
        return ResponseEntity.badRequest()
                .body("Scheduled time must be in the future");
    }

  String previousStatus = question.getStatus();

question.setStatus("SCHEDULED");
question.setScheduledAt(scheduledAt);

Question scheduledQuestion = questionRepository.save(question);

questionStatusHistoryService.recordStatusChange(
        scheduledQuestion,
        previousStatus,
        "SCHEDULED"
);

return ResponseEntity.ok(scheduledQuestion);
}
@DeleteMapping("/drafts/{id}")
public ResponseEntity<?> deleteDraft(
        @PathVariable Long id,
        @RequestParam Long userId) {

    Question question = questionRepository.findById(id)
            .orElseThrow(() ->
                    new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND,
                            "Draft not found"));

    if (question.getUser() == null
            || !question.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("You can only delete your own draft");
    }

    if (!"DRAFT".equalsIgnoreCase(question.getStatus())) {
        return ResponseEntity.badRequest()
                .body("This question is not a draft");
    }

    questionRepository.delete(question);

    return ResponseEntity.ok("Draft deleted successfully");
}
@DeleteMapping("/{id}")
public String deleteQuestion(
        @PathVariable Long id,
        @RequestParam Long userId) {

    if (id <= 0 || userId <= 0) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Question ID and user ID must be positive");
    }

    Question question = questionRepository.findById(id)
            .orElseThrow(() ->
                    new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND,
                            "Question not found"));

    if (question.getUser() == null
            || !question.getUser().getId().equals(userId)) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.FORBIDDEN,
                "You can only delete your own questions");
    }

    questionRepository.delete(question);

    return "Question deleted successfully";
}



    @GetMapping("/tag/{tag}")
    public List<Question> getQuestionsByTag(@PathVariable String tag) {
        return questionRepository.findByTagsContainingIgnoreCase(tag);
    }

@GetMapping("/page")
public Page<Question> getQuestionsByPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

    if (page < 0) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Page number cannot be negative");
    }

    if (size < 1 || size > 100) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Page size must be between 1 and 100");
    }

    return questionRepository.findAll(PageRequest.of(page, size));
}



    @GetMapping("/sort/newest")
    public List<Question> getNewestQuestions() {
        return questionRepository.findAllByOrderByIdDesc();
    }

    @GetMapping("/sort/views")
    public List<Question> getMostViewedQuestions() {
        return questionRepository.findAllByOrderByViewsDesc();
    }

    @GetMapping("/sort/upvotes")
    public List<Question> getMostUpvotedQuestions() {
        return questionRepository.findAllByOrderByUpvotesDesc();
    }

    @GetMapping("/sort/answers")
    public List<Question> getMostAnsweredQuestions() {

        List<Question> questions = questionRepository.findAll();

        questions.sort((q1, q2) -> {

            int count1 = answerRepository.findByQuestionId(q1.getId()).size();
            int count2 = answerRepository.findByQuestionId(q2.getId()).size();

            return Integer.compare(count2, count1);
        });

        return questions;
    }

    @GetMapping("/{id}/statistics")
    public com.example.dto.QuestionStatisticsDTO getQuestionStatistics(
            @PathVariable Long id) {

        Question question = questionRepository.findById(id).orElse(null);

        if (question == null) {
            return null;
        }

        com.example.dto.QuestionStatisticsDTO statistics =
                new com.example.dto.QuestionStatisticsDTO();

        statistics.setQuestionId(question.getId());
        statistics.setViews(question.getViews());
        statistics.setUpvotes(question.getUpvotes());
       statistics.setDownvotes(question.getDownvotes());
statistics.setShares(question.getShares());
statistics.setAnswerCount(
        answerRepository.findByQuestionId(id).size()
);
        statistics.setStatus(question.getStatus());

        return statistics;
    }
    @GetMapping("/{id}/view-history")
public List<QuestionView> getQuestionViewHistory(@PathVariable Long id) {

    return questionViewRepository.findByQuestionId(id);
}
@GetMapping("/{id}/edit-history")
public List<QuestionEditHistory> getQuestionEditHistory(
        @PathVariable Long id) {

    return questionEditHistoryRepository
            .findByQuestionIdOrderByEditedAtDesc(id);
}
@GetMapping("/status-history/{id}")
public List<com.example.entity.QuestionStatusHistory> getQuestionStatusHistory(
        @PathVariable Long id) {

    return questionStatusHistoryService.getQuestionStatusHistory(id);
}
@PutMapping("/{id}/restore/{historyId}")
public ResponseEntity<?> restoreQuestionVersion(
        @PathVariable Long id,
        @PathVariable Long historyId) {

    Question question = questionRepository.findById(id).orElse(null);

    if (question == null) {
        return ResponseEntity.notFound().build();
    }

    QuestionEditHistory history = questionEditHistoryRepository
            .findById(historyId).orElse(null);

    if (history == null ||
            history.getQuestion() == null ||
            !history.getQuestion().getId().equals(id)) {
        return ResponseEntity.badRequest()
                .body("Edit history not found for this question");
    }

    QuestionEditHistory currentHistory = new QuestionEditHistory();
    currentHistory.setPreviousTitle(question.getTitle());
    currentHistory.setPreviousDescription(question.getDescription());
    currentHistory.setPreviousCategory(question.getCategory());
    currentHistory.setPreviousTags(question.getTags());
    currentHistory.setEditedAt(java.time.LocalDateTime.now());
    currentHistory.setQuestion(question);

    questionEditHistoryRepository.save(currentHistory);

    question.setTitle(history.getPreviousTitle());
    question.setDescription(history.getPreviousDescription());
    question.setCategory(history.getPreviousCategory());
    question.setTags(history.getPreviousTags());

    questionRepository.save(question);

    return ResponseEntity.ok(question);
}
@PostMapping("/{id}/follow")
public String followQuestion(@PathVariable Long id,
                             @RequestParam Long userId) {

    Question question = questionRepository.findById(id).orElse(null);
    User user = userRepository.findById(userId).orElse(null);

    if (question == null) {
        return "Question not found";
    }

    if (user == null) {
        return "User not found";
    }
    if (question.getUser() != null &&
        question.getUser().getId().equals(userId)) {
    return "You cannot follow your own question";
}

    if (questionFollowRepository
            .findByUserIdAndQuestionId(userId, id)
            .isPresent()) {

        return "You are already following this question";
    }

    QuestionFollow follow = new QuestionFollow();
    follow.setUser(user);
    follow.setQuestion(question);

    questionFollowRepository.save(follow);

User questionOwner = question.getUser();

if (questionOwner != null && !questionOwner.getId().equals(userId)) {

    Notification notification = new Notification();
    notification.setUser(questionOwner);
    notification.setMessage("Someone followed your question");
    notification.setRead(false);

    notificationRepository.save(notification);
}

return "Question followed successfully";
}
@DeleteMapping("/{id}/follow")
public String unfollowQuestion(@PathVariable Long id,
                               @RequestParam Long userId) {

    QuestionFollow follow = questionFollowRepository
            .findByUserIdAndQuestionId(userId, id)
            .orElse(null);

    if (follow == null) {
        return "You are not following this question";
    }

    questionFollowRepository.delete(follow);

    return "Question unfollowed successfully";
}
@PutMapping("/{id}/share")
public String shareQuestion(@PathVariable Long id) {

    Question question = questionRepository.findById(id).orElse(null);

    if (question == null) {
        return "Question not found";
    }

    question.setShares(question.getShares() + 1);
    questionRepository.save(question);

    return "Question shared successfully";
}
@GetMapping("/{id}/views-by-user")
public long getQuestionViewsByUser(@PathVariable Long id,
                                   @RequestParam Long userId) {

    return questionViewRepository.countByQuestionIdAndUserId(id, userId);
}
@GetMapping("/{id}/followers-count")
public QuestionFollowersCountDTO getFollowersCount(@PathVariable Long id) {

    long followersCount =
            questionFollowRepository.countByQuestionId(id);

    return new QuestionFollowersCountDTO(
            id,
            followersCount
    );
}

@GetMapping("/{id}/followers")
public List<QuestionFollow> getQuestionFollowers(@PathVariable Long id) {

    return questionFollowRepository.findByQuestionId(id);
}
@GetMapping("/{id}/is-following")
public boolean isFollowingQuestion(
        @PathVariable Long id,
        @RequestParam Long userId) {

    return questionFollowRepository
            .existsByUserIdAndQuestionId(userId, id);
}
@GetMapping("/{id}/analytics")
public QuestionAnalyticsDTO getQuestionAnalytics(
        @PathVariable Long id) {

    Question question =
            questionRepository.findById(id).orElse(null);

    if (question == null) {
        return null;
    }

    long views = question.getViews();

    long upvotes = question.getUpvotes();

    long downvotes = question.getDownvotes();

    long answerCount =
            answerRepository.findByQuestionId(id).size();

    long followersCount =
            questionFollowRepository.countByQuestionId(id);

    long shares = question.getShares();

    long commentCount =
            commentRepository.countByQuestionId(id);

    boolean acceptedAnswer =
            answerRepository
                    .findByQuestionIdAndAcceptedTrue(id)
                    .size() > 0;

    String status =
            question.getStatus();

    return new QuestionAnalyticsDTO(
            id,
            views,
            upvotes,
            downvotes,
            answerCount,
            followersCount,
            shares,
            commentCount,
            acceptedAnswer,
            status
    );
}
@GetMapping("/{id}/engagement")
public QuestionEngagementDTO getQuestionEngagement(
        @PathVariable Long id) {

    Question question =
            questionRepository.findById(id).orElse(null);

    if (question == null) {
        return null;
    }

    long views = question.getViews();

    long upvotes = question.getUpvotes();

    long answers =
            answerRepository.findByQuestionId(id).size();

    long comments =
            commentRepository.countByQuestionId(id);

    long followers =
            questionFollowRepository.countByQuestionId(id);

    long shares = question.getShares();

    long engagementScore =
            views
            + (upvotes * 2)
            + (answers * 3)
            + (comments * 2)
            + (followers * 2)
            + (shares * 3);

    return new QuestionEngagementDTO(
            id,
            views,
            upvotes,
            answers,
            comments,
            followers,
            shares,
            engagementScore
    );
}
@GetMapping("/trending")
public List<TrendingQuestionDTO> getTrendingQuestions() {

    List<Question> questions =
            questionRepository.findAll();

    List<TrendingQuestionDTO> result =
            new java.util.ArrayList<>();

    for (Question question : questions) {

        long views = question.getViews();

        long upvotes = question.getUpvotes();

        long answers =
                answerRepository
                        .findByQuestionId(question.getId())
                        .size();

        long comments =
                commentRepository
                        .countByQuestionId(question.getId());

        long followers =
                questionFollowRepository
                        .countByQuestionId(question.getId());

        long shares = question.getShares();

        long engagementScore =
                views
                + (upvotes * 2)
                + (answers * 3)
                + (comments * 2)
                + (followers * 2)
                + (shares * 3);

        result.add(
                new TrendingQuestionDTO(
                        question.getId(),
                        question.getTitle(),
                        engagementScore
                )
        );
    }

    result.sort(
            (a, b) -> Long.compare(
                    b.getEngagementScore(),
                    a.getEngagementScore()
            )
    );

    return result;
}
@GetMapping("/{id}/recommendations")
public List<QuestionRecommendationDTO> getRecommendations(
        @PathVariable Long id) {

    Question question =
            questionRepository.findById(id).orElse(null);

    if (question == null) {
        return List.of();
    }

    List<Question> questions =
            questionRepository.findAll();

    List<QuestionRecommendationDTO> result =
            new java.util.ArrayList<>();

    for (Question q : questions) {

        if (q.getId().equals(id)) {
            continue;
        }

        boolean sameCategory =
                q.getCategory() != null
                && q.getCategory().equalsIgnoreCase(
                        question.getCategory());

        boolean sameTags =
                q.getTags() != null
                && question.getTags() != null
                && q.getTags().toLowerCase()
                        .contains(question.getTags().toLowerCase());

        if (sameCategory || sameTags) {

            result.add(
                    new QuestionRecommendationDTO(
                            q.getId(),
                            q.getTitle(),
                            q.getCategory(),
                            q.getTags()
                    )
            );
        }
    }

    return result;
}
@GetMapping("/status/{status}")
public List<Question> getQuestionsByStatus(
        @PathVariable String status) {

    return questionRepository.findByStatusIgnoreCase(status);
}
@PutMapping("/closed/{id}/reopen")
public ResponseEntity<?> reopenQuestion(
        @PathVariable Long id,
        @RequestParam Long userId) {

    Question question = questionRepository.findById(id)
            .orElseThrow(() ->
                    new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND,
                            "Question not found"));

    if (question.getUser() == null
            || !question.getUser().getId().equals(userId)) {
        return ResponseEntity.status(403)
                .body("You can only reopen your own questions");
    }

    if (!"CLOSED".equalsIgnoreCase(question.getStatus())) {
        return ResponseEntity.badRequest()
                .body("Only closed questions can be reopened");
    }

   String previousStatus = question.getStatus();

question.setStatus("OPEN");
question.setExpiresAt(null);

Question reopenedQuestion = questionRepository.save(question);

questionStatusHistoryService.recordStatusChange(
        reopenedQuestion,
        previousStatus,
        "OPEN"
);

return ResponseEntity.ok(reopenedQuestion);
}

}
