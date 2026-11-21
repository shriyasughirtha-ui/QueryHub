package com.example.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.dto.ActivityTimelineDTO;
import com.example.dto.BadgeProgressDTO;
import com.example.dto.EmailDomainCountDTO;
import com.example.dto.NotificationPreferenceDTO;
import com.example.dto.NotificationSummaryDTO;
import com.example.dto.NotificationTypeCountDTO;
import com.example.dto.QuestionFollowNotificationCountDTO;
import com.example.dto.ReputationAnalyticsDTO;
import com.example.dto.ReputationBreakdownDTO;
import com.example.dto.ReputationHistoryDTO;
import com.example.dto.ReputationLevelCountDTO;
import com.example.dto.ReputationLevelDTO;
import com.example.dto.ReputationPointsDTO;
import com.example.dto.ReputationPointsSummaryDTO;
import com.example.dto.ReputationReasonCountDTO;
import com.example.dto.ReputationReportDTO;
import com.example.dto.ReputationSummaryDTO;
import com.example.dto.RoleAverageReputationDTO;
import com.example.dto.RoleCountDTO;
import com.example.dto.RoleReputationStatisticsDTO;
import com.example.dto.UserAchievementDTO;
import com.example.dto.UserActivityDTO;
import com.example.dto.UserBadgeDTO;
import com.example.dto.UserDashboardDTO;
import com.example.dto.UserEngagementDTO;
import com.example.dto.UserFollowStatsDTO;
import com.example.dto.UserProfileDTO;
import com.example.dto.UserRecommendationDTO;
import com.example.dto.UserReputationStatisticsDTO;
import com.example.dto.UserResponseDTO;
import com.example.dto.UserStatisticsDTO;
import com.example.entity.Answer;
import com.example.entity.Notification;
import com.example.entity.NotificationPreference;
import com.example.entity.Question;
import com.example.entity.QuestionFollow;
import com.example.entity.QuestionView;
import com.example.entity.User;
import com.example.entity.UserFollow;
import com.example.repository.AnswerRepository;
import com.example.repository.AnswerVoteRepository;
import com.example.repository.BookmarkRepository;
import com.example.repository.CommentRepository;
import com.example.repository.NotificationPreferenceRepository;
import com.example.repository.NotificationRepository;
import com.example.repository.QuestionFollowRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.QuestionViewRepository;
import com.example.repository.QuestionVoteRepository;
import com.example.repository.ReputationHistoryRepository;
import com.example.repository.UserFollowRepository;
import com.example.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final NotificationRepository notificationRepository;
    private final UserFollowRepository userFollowRepository;
    private final QuestionFollowRepository questionFollowRepository;
    private final QuestionViewRepository questionViewRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReputationHistoryRepository reputationHistoryRepository;
    private final QuestionVoteRepository questionVoteRepository;
    private final AnswerVoteRepository answerVoteRepository;
    private final AuthenticationManager authenticationManager;
    private final CommentRepository commentRepository;
    private final BookmarkRepository bookmarkRepository;
    private final NotificationPreferenceRepository notificationPreferenceRepository;
    public UserController(
            UserRepository userRepository,
            QuestionRepository questionRepository,
            QuestionViewRepository questionViewRepository,
            AnswerRepository answerRepository,
           
AuthenticationManager authenticationManager,
            NotificationRepository notificationRepository,
            PasswordEncoder passwordEncoder,
            BookmarkRepository bookmarkRepository,
            UserFollowRepository userFollowRepository,
            QuestionFollowRepository questionFollowRepository,
            NotificationPreferenceRepository notificationPreferenceRepository,
            ReputationHistoryRepository reputationHistoryRepository,
            QuestionVoteRepository questionVoteRepository,
            AnswerVoteRepository answerVoteRepository,
            CommentRepository commentRepository) {

        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.passwordEncoder = passwordEncoder;
        this.notificationRepository = notificationRepository;
        this.reputationHistoryRepository = reputationHistoryRepository;
        this.questionVoteRepository = questionVoteRepository;
        this.answerVoteRepository = answerVoteRepository;
        this.questionFollowRepository = questionFollowRepository;
        this.commentRepository = commentRepository;
        this.authenticationManager = authenticationManager;
        this.userFollowRepository = userFollowRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.notificationPreferenceRepository =
        notificationPreferenceRepository;
        this.questionViewRepository = questionViewRepository;
    }

    @PostMapping("/register")
    public UserResponseDTO registerUser(@Valid @RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
    throw new ResponseStatusException(
            HttpStatus.CONFLICT,
            "Email is already registered"
    );
}

        user.setReputation(0);
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getReputation(),
                savedUser.getRole()
        );
    }
@PostMapping("/login")
public ResponseEntity<UserResponseDTO> loginUser(
        @RequestBody User user,
        HttpServletRequest request,
        HttpServletResponse response) {

    try {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(),
                                user.getPassword()
                        )
                );

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        HttpSessionSecurityContextRepository securityContextRepository =
                new HttpSessionSecurityContextRepository();

        securityContextRepository.saveContext(
                context,
                request,
                response
        );

        User existingUser = userRepository
                .findByEmail(user.getEmail())
                .orElseThrow();

        return ResponseEntity.ok(
                new UserResponseDTO(
                        existingUser.getId(),
                        existingUser.getName(),
                        existingUser.getEmail(),
                        existingUser.getReputation(),
                        existingUser.getRole()
                )
        );

    } catch (AuthenticationException exception) {
        SecurityContextHolder.clearContext();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(null);
    }
}

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getReputation(),
                        user.getRole()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getReputation(),
                user.getRole()
        );
    }

    @GetMapping("/{id}/questions")
    public List<Question> getUserQuestions(@PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return List.of();
        }

        return questionRepository.findByUserId(id);
    }

    @GetMapping("/{id}/answers")
    public List<Answer> getUserAnswers(@PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return List.of();
        }

        return answerRepository.findByUserId(id);
    }

    @GetMapping("/{id}/profile")
    public UserProfileDTO getUserProfile(@PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        int questionCount = questionRepository.findByUserId(id).size();
        int answerCount = answerRepository.findByUserId(id).size();

        int acceptedAnswerCount =
                answerRepository.findByUserIdAndAcceptedTrue(id).size();

        long questionUpvotes =
                questionVoteRepository.countByQuestionUserIdAndVoteType(
                        id, "UP");

        long questionDownvotes =
                questionVoteRepository.countByQuestionUserIdAndVoteType(
                        id, "DOWN");

        long answerUpvotes =
                answerVoteRepository.countByAnswerUserIdAndVoteType(
                        id, "UP");

        long answerDownvotes =
                answerVoteRepository.countByAnswerUserIdAndVoteType(
                        id, "DOWN");

        long upvotesReceived = questionUpvotes + answerUpvotes;
        long downvotesReceived = questionDownvotes + answerDownvotes;

        return new UserProfileDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getReputation(),
                user.getRole(),
                questionCount,
                answerCount,
                acceptedAnswerCount,
                upvotesReceived,
                downvotesReceived
        );
    }

    @GetMapping("/leaderboard")
    public List<UserResponseDTO> getLeaderboard() {

        return userRepository.findAllByOrderByReputationDesc()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getReputation(),
                        user.getRole()
                ))
                .toList();
    }

    @GetMapping("/{id}/reputation-history")
    public List<ReputationHistoryDTO> getReputationHistory(
            @PathVariable Long id) {

        return reputationHistoryRepository.findByUserId(id)
                .stream()
                .map(history -> new ReputationHistoryDTO(
                        history.getId(),
                        history.getChangeAmount(),
                        history.getReason()
                ))
                .toList();
    }

    @GetMapping("/{id}/activity")
    public UserActivityDTO getUserActivity(@PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        UserActivityDTO activity = new UserActivityDTO();

        activity.setUserId(user.getId());
        activity.setQuestionsAsked(
                questionRepository.findByUserId(id).size());
        activity.setAnswersGiven(
                answerRepository.findByUserId(id).size());
        activity.setAcceptedAnswers(
                answerRepository.findByUserIdAndAcceptedTrue(id).size());
        activity.setCommentsMade(
                commentRepository.findByUserId(id).size());
        activity.setReputation(user.getReputation());

        return activity;
    }

    @GetMapping("/{id}/view-history")
    public List<QuestionView> getUserViewHistory(
            @PathVariable Long id) {

        return questionViewRepository.findByUserId(id);
    }

    @GetMapping("/{id}/recently-viewed")
    public List<QuestionView> getRecentlyViewedQuestions(
            @PathVariable Long id) {

        return questionViewRepository.findByUserIdOrderByViewedAtDesc(id);
    }

    @GetMapping("/{id}/followed-questions")
    public List<QuestionFollow> getFollowedQuestions(
            @PathVariable Long id) {

        return questionFollowRepository.findByUserId(id);
    }

    @GetMapping("/{id}/reputation-summary")
    public ReputationSummaryDTO getReputationSummary(
            @PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        long historyCount =
                reputationHistoryRepository.countByUserId(id);

        return new ReputationSummaryDTO(
                user.getId(),
                user.getReputation(),
                historyCount
        );
    }

    @GetMapping("/{id}/reputation-history-count")
    public long getReputationHistoryCountByReason(
            @PathVariable Long id,
            @RequestParam String reason) {

        return reputationHistoryRepository
                .countByUserIdAndReasonContainingIgnoreCase(id, reason);
    }

    @GetMapping("/{id}/reputation-points")
public ReputationPointsDTO getReputationPoints(
        @PathVariable Long id) {

    long positivePoints =
            reputationHistoryRepository
                    .sumPositivePoints(id, 0);

    long negativePoints =
            reputationHistoryRepository
                    .sumNegativePoints(id, 0);

    return new ReputationPointsDTO(
            id,
            positivePoints,
            Math.abs(negativePoints)
    );
}
@GetMapping("/{id}/reputation-level")
public ReputationLevelDTO getReputationLevel(@PathVariable Long id) {

    User user = userRepository.findById(id).orElse(null);

    if (user == null) {
        return null;
    }

    int reputation = user.getReputation();
    String level;

    if (reputation >= 100) {
        level = "Master";
    } else if (reputation >= 50) {
        level = "Expert";
    } else if (reputation >= 10) {
        level = "Contributor";
    } else {
        level = "Beginner";
    }

    return new ReputationLevelDTO(
            user.getId(),
            reputation,
            level
    );
}
@GetMapping("/{id}/badges")
public List<UserBadgeDTO> getUserBadges(@PathVariable Long id) {

    User user = userRepository.findById(id).orElse(null);

    if (user == null) {
        return List.of();
    }

    List<UserBadgeDTO> badges = new java.util.ArrayList<>();

    int reputation = user.getReputation();
    int questionCount = questionRepository.findByUserId(id).size();
    int answerCount = answerRepository.findByUserId(id).size();
    int acceptedAnswerCount =
            answerRepository.findByUserIdAndAcceptedTrue(id).size();

    if (questionCount >= 1) {
        badges.add(new UserBadgeDTO(id, "First Question"));
    }

    if (answerCount >= 1) {
        badges.add(new UserBadgeDTO(id, "Active Answerer"));
    }

    if (acceptedAnswerCount >= 1) {
        badges.add(new UserBadgeDTO(id, "Accepted Answer"));
    }

    if (reputation >= 10) {
        badges.add(new UserBadgeDTO(id, "Popular Contributor"));
    }

    if (reputation >= 50) {
        badges.add(new UserBadgeDTO(id, "Expert Contributor"));
    }

    return badges;
}
@PostMapping("/{followingId}/follow")
public String followUser(
        @PathVariable Long followingId,
        @RequestParam Long followerId) {

    User follower = userRepository.findById(followerId).orElse(null);
    User following = userRepository.findById(followingId).orElse(null);

    if (follower == null || following == null) {
        return "User not found";
    }

    if (followerId.equals(followingId)) {
        return "You cannot follow yourself";
    }

    if (userFollowRepository
            .existsByFollowerIdAndFollowingId(followerId, followingId)) {
        return "Already following this user";
    }

    UserFollow userFollow = new UserFollow();
    userFollow.setFollower(follower);
    userFollow.setFollowing(following);
userFollowRepository.save(userFollow);

Notification notification = new Notification();
notification.setMessage("Someone followed you");
notification.setRead(false);
notification.setUser(following);

notificationRepository.save(notification);

return "User followed successfully";
}
@GetMapping("/{id}/following")
public List<UserFollow> getFollowing(@PathVariable Long id) {
    return userFollowRepository.findByFollowerId(id);
}
@GetMapping("/{id}/followers")
public List<UserFollow> getFollowers(@PathVariable Long id) {
    return userFollowRepository.findByFollowingId(id);
}
@DeleteMapping("/{followingId}/unfollow")
public String unfollowUser(
        @PathVariable Long followingId,
        @RequestParam Long followerId) {

    if (!userFollowRepository
            .existsByFollowerIdAndFollowingId(followerId, followingId)) {
        return "Not following this user";
    }

    userFollowRepository
            .deleteByFollowerIdAndFollowingId(followerId, followingId);

    return "User unfollowed successfully";
}
@GetMapping("/{id}/follow-stats")
public UserFollowStatsDTO getFollowStats(@PathVariable Long id) {

    User user = userRepository.findById(id).orElse(null);

    if (user == null) {
        return null;
    }

    long followersCount =
            userFollowRepository.countByFollowingId(id);

    long followingCount =
            userFollowRepository.countByFollowerId(id);

    return new UserFollowStatsDTO(
            id,
            followersCount,
            followingCount
    );
}
@GetMapping("/{id}/notifications/question-follow-count")
public QuestionFollowNotificationCountDTO getQuestionFollowNotificationCount(
        @PathVariable Long id) {

    long count =
            notificationRepository
                    .countByUserIdAndMessageContainingIgnoreCase(
                            id,
                            "followed your question"
                    );

    return new QuestionFollowNotificationCountDTO(
            id,
            count
    );
}
@GetMapping("/{id}/notifications/question-follow")
public List<Notification> getQuestionFollowNotifications(
        @PathVariable Long id) {

    return notificationRepository
            .findByUserIdAndMessageContainingIgnoreCase(
                    id,
                    "followed your question"
            );
}
@PutMapping("/{id}/notifications/{notificationId}/read")
public String markNotificationAsRead(
        @PathVariable Long id,
        @PathVariable Long notificationId) {

    Notification notification =
            notificationRepository.findById(notificationId).orElse(null);

    if (notification == null) {
        return "Notification not found";
    }

    if (notification.getUser() == null ||
            !notification.getUser().getId().equals(id)) {
        return "Notification does not belong to this user";
    }

    notification.setRead(true);
    notificationRepository.save(notification);

    return "Notification marked as read";
}
@GetMapping("/{id}/notifications/question-follow-unread-count")
public long getQuestionFollowUnreadCount(@PathVariable Long id) {

    return notificationRepository
            .countByUserIdAndMessageContainingIgnoreCaseAndReadStatus(
                    id,
                    "followed your question",
                    false
            );
}
@GetMapping("/{id}/notifications/type-count")
public List<NotificationTypeCountDTO> getNotificationTypeCount(
        @PathVariable Long id) {

    List<NotificationTypeCountDTO> result =
            new java.util.ArrayList<>();

    long answerCount =
            notificationRepository
                    .countByUserIdAndMessageContainingIgnoreCase(
                            id,
                            "answered your question"
                    );

    long commentCount =
            notificationRepository
                    .countByUserIdAndMessageContainingIgnoreCase(
                            id,
                            "commented"
                    );

    long upvoteCount =
            notificationRepository
                    .countByUserIdAndMessageContainingIgnoreCase(
                            id,
                            "upvoted"
                    );

    long downvoteCount =
            notificationRepository
                    .countByUserIdAndMessageContainingIgnoreCase(
                            id,
                            "downvoted"
                    );

    long followCount =
            notificationRepository
                    .countByUserIdAndMessageContainingIgnoreCase(
                            id,
                            "followed"
                    );

    result.add(new NotificationTypeCountDTO(
            id, "Answer", answerCount));

    result.add(new NotificationTypeCountDTO(
            id, "Comment", commentCount));

    result.add(new NotificationTypeCountDTO(
            id, "Upvote", upvoteCount));

    result.add(new NotificationTypeCountDTO(
            id, "Downvote", downvoteCount));

    result.add(new NotificationTypeCountDTO(
            id, "Follow", followCount));

    return result;
}
@GetMapping("/{id}/notifications/summary")
public NotificationSummaryDTO getNotificationSummary(
        @PathVariable Long id) {

    long total =
            notificationRepository.countByUserId(id);

    long unread =
            notificationRepository
                    .countByUserIdAndReadStatusFalse(id);

    long read = total - unread;

    long answer =
            notificationRepository
                    .countByUserIdAndMessageContainingIgnoreCase(
                            id,
                            "answered your question"
                    );

    long comment =
            notificationRepository
                    .countByUserIdAndMessageContainingIgnoreCase(
                            id,
                            "commented"
                    );

    long upvote =
            notificationRepository
                    .countByUserIdAndMessageContainingIgnoreCase(
                            id,
                            "upvoted"
                    );

    long downvote =
            notificationRepository
                    .countByUserIdAndMessageContainingIgnoreCase(
                            id,
                            "downvoted"
                    );

    long follow =
            notificationRepository
                    .countByUserIdAndMessageContainingIgnoreCase(
                            id,
                            "followed"
                    );

    return new NotificationSummaryDTO(
            id,
            total,
            read,
            unread,
            answer,
            comment,
            upvote,
            downvote,
            follow
    );
}
@GetMapping("/{id}/dashboard")
public UserDashboardDTO getUserDashboard(@PathVariable Long id) {

    User user = userRepository.findById(id).orElse(null);

    if (user == null) {
        return null;
    }

    long questionCount =
            questionRepository.findByUserId(id).size();

    long answerCount =
            answerRepository.findByUserId(id).size();

    long acceptedAnswerCount =
            answerRepository.findByUserIdAndAcceptedTrue(id).size();

    long followersCount =
            userFollowRepository.countByFollowingId(id);

    long followingCount =
            userFollowRepository.countByFollowerId(id);

    long bookmarkCount =
            bookmarkRepository.findByUserId(id).size();

    long notificationCount =
            notificationRepository.countByUserId(id);

    long unreadNotificationCount =
            notificationRepository
                    .countByUserIdAndReadStatusFalse(id);

    long badgeCount =
            getUserBadges(id).size();

    int reputation = user.getReputation();

    String reputationLevel;

    if (reputation >= 100) {
        reputationLevel = "Master";
    } else if (reputation >= 50) {
        reputationLevel = "Expert";
    } else if (reputation >= 10) {
        reputationLevel = "Contributor";
    } else {
        reputationLevel = "Beginner";
    }

    return new UserDashboardDTO(
            id,
            user.getName(),
            reputation,
            reputationLevel,
            questionCount,
            answerCount,
            acceptedAnswerCount,
            followersCount,
            followingCount,
            bookmarkCount,
            notificationCount,
            unreadNotificationCount,
            badgeCount
    );
}
@GetMapping("/{id}/recommendations")
public List<UserRecommendationDTO> getUserRecommendations(
        @PathVariable Long id) {

    User user =
            userRepository.findById(id).orElse(null);

    if (user == null) {
        return List.of();
    }

    List<Question> userQuestions =
            questionRepository.findByUserId(id);

    List<Question> allQuestions =
            questionRepository.findAll();

    List<UserRecommendationDTO> result =
            new java.util.ArrayList<>();

    for (Question question : allQuestions) {

        boolean alreadyAsked = false;

        for (Question userQuestion : userQuestions) {

            if (userQuestion.getId().equals(question.getId())) {
                alreadyAsked = true;
                break;
            }
        }

        if (alreadyAsked) {
            continue;
        }

        boolean matchingCategory = false;

        for (Question userQuestion : userQuestions) {

            if (userQuestion.getCategory() != null
                    && question.getCategory() != null
                    && userQuestion.getCategory()
                            .equalsIgnoreCase(question.getCategory())) {

                matchingCategory = true;
                break;
            }
        }

        if (matchingCategory) {

            result.add(
                    new UserRecommendationDTO(
                            question.getId(),
                            question.getTitle(),
                            question.getCategory(),
                            question.getTags()
                    )
            );
        }
    }

    return result;
}
@GetMapping("/{id}/activity-timeline")
public List<ActivityTimelineDTO> getActivityTimeline(
        @PathVariable Long id) {

    User user =
            userRepository.findById(id).orElse(null);

    if (user == null) {
        return List.of();
    }

    List<ActivityTimelineDTO> result =
            new java.util.ArrayList<>();

    List<Question> questions =
            questionRepository.findByUserId(id);

    for (Question question : questions) {

        result.add(
                new ActivityTimelineDTO(
                        "Question",
                        question.getId(),
                        question.getTitle()
                )
        );
    }

    List<Answer> answers =
            answerRepository.findByUserId(id);

    for (Answer answer : answers) {

        result.add(
                new ActivityTimelineDTO(
                        "Answer",
                        answer.getId(),
                        answer.getContent()
                )
        );
    }

    return result;
}
@GetMapping("/{id}/reputation-analytics")
public ReputationAnalyticsDTO getReputationAnalytics(
        @PathVariable Long id) {

    User user =
            userRepository.findById(id).orElse(null);

    if (user == null) {
        return null;
    }

    long positivePoints =
            reputationHistoryRepository
                    .sumPositivePoints(id, 0);

    long negativePoints =
            reputationHistoryRepository
                    .sumNegativePoints(id, 0);

    long reputationEvents =
            reputationHistoryRepository
                    .countByUserId(id);

    int reputation =
            user.getReputation();

    String reputationLevel;

    if (reputation >= 100) {
        reputationLevel = "Master";
    } else if (reputation >= 50) {
        reputationLevel = "Expert";
    } else if (reputation >= 10) {
        reputationLevel = "Contributor";
    } else {
        reputationLevel = "Beginner";
    }

    return new ReputationAnalyticsDTO(
            id,
            reputation,
            positivePoints,
            negativePoints,
            reputationEvents,
            reputationLevel
    );
}
@GetMapping("/{id}/reputation-breakdown")
public List<ReputationBreakdownDTO> getReputationBreakdown(
        @PathVariable Long id) {

    User user =
            userRepository.findById(id).orElse(null);

    if (user == null) {
        return List.of();
    }

    List<ReputationBreakdownDTO> result =
            new java.util.ArrayList<>();

   String[] reasons = {
        "Someone upvoted your question",
        "Someone downvoted your question",
        "Someone upvoted your answer",
        "Someone downvoted your answer",
        "Your answer was accepted"
};

    int[] points = {
            2,
            -1,
            5,
            -1,
            10
    };

    for (int i = 0; i < reasons.length; i++) {

        long count =
                reputationHistoryRepository
                        .countByUserIdAndReasonContainingIgnoreCase(
                                id,
                                reasons[i]);

        long totalPoints =
                count * points[i];

        result.add(
                new ReputationBreakdownDTO(
                        id,
                        reasons[i],
                        count,
                        totalPoints
                )
        );
    }

    return result;
}
@GetMapping("/{id}/reputation-points-summary")
public ReputationPointsSummaryDTO getReputationPointsSummary(
        @PathVariable Long id) {

    User user =
            userRepository.findById(id).orElse(null);

    if (user == null) {
        return null;
    }

    long positivePoints =
            reputationHistoryRepository.sumPositivePoints(id, 0);

    long negativePoints =
            reputationHistoryRepository.sumNegativePoints(id, 0);

    long netPoints =
            positivePoints + negativePoints;

    return new ReputationPointsSummaryDTO(
            id,
            positivePoints,
            negativePoints,
            netPoints
    );
}
@GetMapping("/{id}/reputation-reason-count")
public List<ReputationReasonCountDTO> getReputationReasonCount(
        @PathVariable Long id) {

    User user =
            userRepository.findById(id).orElse(null);

    if (user == null) {
        return List.of();
    }

    List<ReputationReasonCountDTO> result =
            new java.util.ArrayList<>();

    String[] reasons = {
            "Someone upvoted your question",
            "Someone downvoted your question",
            "Someone upvoted your answer",
            "Someone downvoted your answer",
            "Your answer was accepted"
    };

    for (String reason : reasons) {

        long count =
                reputationHistoryRepository
                        .countByUserIdAndReasonContainingIgnoreCase(
                                id,
                                reason);

        result.add(
                new ReputationReasonCountDTO(
                        id,
                        reason,
                        count
                )
        );
    }

    return result;
}
@GetMapping("/{id}/reputation-report")
public ReputationReportDTO getReputationReport(
        @PathVariable Long id) {

    User user =
            userRepository.findById(id).orElse(null);

    if (user == null) {
        return null;
    }

    int reputation = user.getReputation();

    String reputationLevel;

    if (reputation >= 100) {
        reputationLevel = "Master";
    } else if (reputation >= 50) {
        reputationLevel = "Expert";
    } else if (reputation >= 10) {
        reputationLevel = "Contributor";
    } else {
        reputationLevel = "Beginner";
    }

    long positivePoints =
            reputationHistoryRepository.sumPositivePoints(id, 0);

    long negativePoints =
            reputationHistoryRepository.sumNegativePoints(id, 0);

    long netPoints =
            positivePoints + negativePoints;

    long reputationEvents =
            reputationHistoryRepository.countByUserId(id);

    List<ReputationBreakdownDTO> breakdown =
            new java.util.ArrayList<>();

    String[] reasons = {
            "Someone upvoted your question",
            "Someone downvoted your question",
            "Someone upvoted your answer",
            "Someone downvoted your answer",
            "Your answer was accepted"
    };

    int[] points = {
            2,
            -1,
            5,
            -1,
            10
    };

    for (int i = 0; i < reasons.length; i++) {

        long count =
                reputationHistoryRepository
                        .countByUserIdAndReasonContainingIgnoreCase(
                                id,
                                reasons[i]);

        long totalPoints =
                count * points[i];

        breakdown.add(
                new ReputationBreakdownDTO(
                        id,
                        reasons[i],
                        count,
                        totalPoints
                )
        );
    }

    return new ReputationReportDTO(
            id,
            reputation,
            reputationLevel,
            positivePoints,
            negativePoints,
            netPoints,
            reputationEvents,
            breakdown
    );
}
@GetMapping("/{id}/badge-progress")
public List<BadgeProgressDTO> getBadgeProgress(
        @PathVariable Long id) {

    User user =
            userRepository.findById(id).orElse(null);

    if (user == null) {
        return List.of();
    }

    List<BadgeProgressDTO> result =
            new java.util.ArrayList<>();

    long questionCount =
            questionRepository.findByUserId(id).size();

    long answerCount =
            answerRepository.findByUserId(id).size();

    long acceptedAnswerCount =
            answerRepository.findByUserIdAndAcceptedTrue(id).size();

    long reputation =
            user.getReputation();

    addBadgeProgress(
            result,
            id,
            "First Question",
            questionCount,
            1
    );

    addBadgeProgress(
            result,
            id,
            "Active Answerer",
            answerCount,
            5
    );

    addBadgeProgress(
            result,
            id,
            "Accepted Answer",
            acceptedAnswerCount,
            1
    );

    addBadgeProgress(
            result,
            id,
            "Popular Contributor",
            reputation,
            10
    );

    addBadgeProgress(
            result,
            id,
            "Expert Contributor",
            reputation,
            50
    );

    return result;
}

private void addBadgeProgress(
        List<BadgeProgressDTO> result,
        Long userId,
        String badge,
        long current,
        long required) {

    int progress =
            (int) Math.min(
                    100,
                    (current * 100) / required
            );

    boolean achieved =
            current >= required;

    result.add(
            new BadgeProgressDTO(
                    userId,
                    badge,
                    current,
                    required,
                    progress,
                    achieved
            )
    );
}
@GetMapping("/{id}/statistics")
public UserStatisticsDTO getUserStatistics(
        @PathVariable Long id) {

    User user =
            userRepository.findById(id).orElse(null);

    if (user == null) {
        return null;
    }

    long questionsAsked =
            questionRepository.findByUserId(id).size();

    long answersGiven =
            answerRepository.findByUserId(id).size();

    long acceptedAnswers =
            answerRepository.findByUserIdAndAcceptedTrue(id).size();

    long commentsMade =
            commentRepository.findByUserId(id).size();

    long followers =
            userFollowRepository.countByFollowingId(id);

    long following =
            userFollowRepository.countByFollowerId(id);

    int reputation =
            user.getReputation();

    long badges =
            getUserBadges(id).size();

    return new UserStatisticsDTO(
            id,
            questionsAsked,
            answersGiven,
            acceptedAnswers,
            commentsMade,
            followers,
            following,
            reputation,
            badges
    );
}
@GetMapping("/{id}/notification-preferences")
public NotificationPreferenceDTO getNotificationPreferences(
        @PathVariable Long id) {

    User user =
            userRepository.findById(id).orElse(null);

    if (user == null) {
        return null;
    }

    NotificationPreference preference =
            notificationPreferenceRepository
                    .findByUserId(id)
                    .orElse(null);

    if (preference == null) {

        preference = new NotificationPreference();
        preference.setUser(user);

        preference =
                notificationPreferenceRepository.save(preference);
    }

    return new NotificationPreferenceDTO(
            id,
            preference.isAnswerNotifications(),
            preference.isCommentNotifications(),
            preference.isUpvoteNotifications(),
            preference.isDownvoteNotifications(),
            preference.isFollowNotifications()
    );
}
@PutMapping("/{id}/notification-preferences")
public NotificationPreferenceDTO updateNotificationPreferences(
        @PathVariable Long id,
        @RequestBody NotificationPreferenceDTO request) {

    User user =
            userRepository.findById(id).orElse(null);

    if (user == null) {
        return null;
    }

    NotificationPreference preference =
            notificationPreferenceRepository
                    .findByUserId(id)
                    .orElse(new NotificationPreference());

    preference.setUser(user);

    preference.setAnswerNotifications(
            request.isAnswerNotifications()
    );

    preference.setCommentNotifications(
            request.isCommentNotifications()
    );

    preference.setUpvoteNotifications(
            request.isUpvoteNotifications()
    );

    preference.setDownvoteNotifications(
            request.isDownvoteNotifications()
    );

    preference.setFollowNotifications(
            request.isFollowNotifications()
    );

    preference =
            notificationPreferenceRepository.save(preference);

    return new NotificationPreferenceDTO(
            id,
            preference.isAnswerNotifications(),
            preference.isCommentNotifications(),
            preference.isUpvoteNotifications(),
            preference.isDownvoteNotifications(),
            preference.isFollowNotifications()
    );
}
@GetMapping("/{id}/engagement-score")
public UserEngagementDTO getEngagementScore(
        @PathVariable Long id) {

    User user =
            userRepository.findById(id).orElse(null);

    if (user == null) {
        return null;
    }

    long questions =
            questionRepository.findByUserId(id).size();

    long answers =
            answerRepository.findByUserId(id).size();

    long comments =
            commentRepository.findByUserId(id).size();

    long followers =
            userFollowRepository.countByFollowingId(id);

    int reputation =
            user.getReputation();

    long engagementScore =
            (questions * 5)
            + (answers * 5)
            + (comments * 2)
            + (followers * 3)
            + reputation;

    return new UserEngagementDTO(
            id,
            questions,
            answers,
            comments,
            followers,
            reputation,
            engagementScore
    );
}
@GetMapping("/{id}/achievement-summary")
public UserAchievementDTO getAchievementSummary(
        @PathVariable Long id) {

    User user =
            userRepository.findById(id).orElse(null);

    if (user == null) {
        return null;
    }

    int reputation =
            user.getReputation();

    String reputationLevel;

    if (reputation >= 100) {
        reputationLevel = "Master";
    } else if (reputation >= 50) {
        reputationLevel = "Expert";
    } else if (reputation >= 10) {
        reputationLevel = "Contributor";
    } else {
        reputationLevel = "Beginner";
    }

    long badges =
            getUserBadges(id).size();

    long questions =
            questionRepository.findByUserId(id).size();

    long answers =
            answerRepository.findByUserId(id).size();

    long acceptedAnswers =
            answerRepository
                    .findByUserIdAndAcceptedTrue(id)
                    .size();

    long followers =
            userFollowRepository
                    .countByFollowingId(id);

    long comments =
            commentRepository
                    .findByUserId(id)
                    .size();

    long engagementScore =
            (questions * 5)
            + (answers * 5)
            + (comments * 2)
            + (followers * 3)
            + reputation;

    return new UserAchievementDTO(
            id,
            reputationLevel,
            badges,
            questions,
            answers,
            acceptedAnswers,
            followers,
            engagementScore
    );
}
@GetMapping("/search")
public List<User> searchUsers(
        @RequestParam String keyword) {

    return userRepository
            .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    keyword,
                    keyword
            );
}
@GetMapping("/by-reputation")
public List<User> getUsersByReputation(
        @RequestParam int min) {

    return userRepository
            .findByReputationGreaterThanEqual(min);
}
@GetMapping("/sort")
public List<User> sortUsers(
        @RequestParam String by) {

    if (by.equalsIgnoreCase("reputation")) {
        return userRepository
                .findAllByOrderByReputationDesc();
    }

    if (by.equalsIgnoreCase("reputation-asc")) {
        return userRepository
                .findAllByOrderByReputationAsc();
    }

    if (by.equalsIgnoreCase("name")) {
        return userRepository
                .findAllByOrderByNameAsc();
    }

    if (by.equalsIgnoreCase("name-desc")) {
        return userRepository
                .findAllByOrderByNameDesc();
    }

    return List.of();
}
@GetMapping("/by-role")
public List<User> getUsersByRole(
        @RequestParam String role) {

    return userRepository
            .findByRoleIgnoreCase(role);
}
@GetMapping("/role-count")
public RoleCountDTO getRoleCount(
        @RequestParam String role) {

    long count =
            userRepository.countByRoleIgnoreCase(role);

    return new RoleCountDTO(
            role,
            count
    );
}
@GetMapping("/email-domain-count")
public EmailDomainCountDTO getEmailDomainCount(
        @RequestParam String domain) {

    long count =
            userRepository.countByEmailEndingWithIgnoreCase(domain);

    return new EmailDomainCountDTO(
            domain,
            count
    );
}
@GetMapping("/reputation-range")
public List<User> getUsersByReputationRange(
        @RequestParam int min,
        @RequestParam int max) {

    return userRepository.findByReputationBetween(min, max);
}
@GetMapping("/role-average-reputation")
public RoleAverageReputationDTO getRoleAverageReputation(
        @RequestParam String role) {

    Double average =
            userRepository.findAverageReputationByRole(role);

    if (average == null) {
        average = 0.0;
    }

    return new RoleAverageReputationDTO(
            role,
            average
    );
}
@GetMapping("/reputation-statistics")
public UserReputationStatisticsDTO getReputationStatistics() {

    Integer highest =
            userRepository.findHighestReputation();

    Integer lowest =
            userRepository.findLowestReputation();

    Double average =
            userRepository.findAverageReputation();

    if (highest == null) {
        highest = 0;
    }

    if (lowest == null) {
        lowest = 0;
    }

    if (average == null) {
        average = 0.0;
    }

    return new UserReputationStatisticsDTO(
            highest,
            lowest,
            average
    );
}
@GetMapping("/reputation-level-count")
public ReputationLevelCountDTO getReputationLevelCount(
        @RequestParam String level) {

    int min;
    int max;

    if (level.equalsIgnoreCase("Beginner")) {
        min = 0;
        max = 9;
    } else if (level.equalsIgnoreCase("Contributor")) {
        min = 10;
        max = 49;
    } else if (level.equalsIgnoreCase("Expert")) {
        min = 50;
        max = 99;
    } else if (level.equalsIgnoreCase("Master")) {
        min = 100;
        max = Integer.MAX_VALUE;
    } else {
        return new ReputationLevelCountDTO(level, 0);
    }

    long count =
            userRepository.countByReputationRange(min, max);

    return new ReputationLevelCountDTO(
            level,
            count
    );
}
@GetMapping("/role-reputation-statistics")
public RoleReputationStatisticsDTO getRoleReputationStatistics(
        @RequestParam String role) {

    long userCount =
            userRepository.countByRoleIgnoreCase(role);

    Integer highest =
            userRepository.findHighestReputationByRole(role);

    Integer lowest =
            userRepository.findLowestReputationByRole(role);

    Double average =
            userRepository.findAverageReputationByRole(role);

    if (highest == null) {
        highest = 0;
    }

    if (lowest == null) {
        lowest = 0;
    }

    if (average == null) {
        average = 0.0;
    }

    return new RoleReputationStatisticsDTO(
            role,
            userCount,
            highest,
            lowest,
            average
    );
}
@PutMapping("/{id}/active")
public User updateUserActiveStatus(
        @PathVariable Long id,
        @RequestParam boolean active) {

    User user = userRepository.findById(id).orElse(null);

    if (user == null) {
        return null;
    }

    user.setActive(active);

    return userRepository.save(user);
}

   
}


