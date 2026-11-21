
package com.example.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.entity.Question;
import com.example.entity.User;
import com.example.repository.AnswerRepository;
import com.example.repository.CommentRepository;
import com.example.repository.NotificationRepository;
import com.example.repository.QuestionFollowRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.QuestionViewRepository;
import com.example.repository.QuestionVoteRepository;
import com.example.repository.ReputationHistoryRepository;
import com.example.repository.UserRepository;
import com.example.service.VoteService;

@WebMvcTest(QuestionController.class)
@AutoConfigureMockMvc(addFilters = false)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuestionRepository questionRepository;

    @MockitoBean
    private AnswerRepository answerRepository;

    @MockitoBean
    private VoteService voteService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private QuestionFollowRepository questionFollowRepository;

    @MockitoBean
    private QuestionVoteRepository questionVoteRepository;

    @MockitoBean
    private CommentRepository commentRepository;

    @MockitoBean
    private NotificationRepository notificationRepository;

    @MockitoBean
    private ReputationHistoryRepository reputationHistoryRepository;

    @MockitoBean
    private QuestionViewRepository questionViewRepository;

    @Test
    void getAllQuestionsReturnsEmptyList() throws Exception {
        when(questionRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getAllQuestionsReturnsQuestionsWhenAvailable() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("How to learn Java?");
        question.setDescription("I want to learn Java programming.");
        question.setCategory("Programming");
        question.setTags("Java,Beginner");
        question.setStatus("OPEN");

        when(questionRepository.findAll()).thenReturn(List.of(question));

        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("How to learn Java?"))
                .andExpect(jsonPath("$[0].category").value("Programming"));
    }

    @Test
    void searchQuestionsReturnsMatchingQuestions() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("How to learn Java?");
        question.setDescription("I want to learn Java programming.");
        question.setCategory("Programming");

        when(questionRepository.findByTitleContainingIgnoreCase("Java"))
                .thenReturn(List.of(question));

        mockMvc.perform(get("/questions/search").param("keyword", "Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("How to learn Java?"));
    }

    @Test
    void getQuestionsByCategoryReturnsMatchingQuestions() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("How to learn Java?");
        question.setDescription("I want to learn Java programming.");
        question.setCategory("Programming");

        when(questionRepository.findByCategoryIgnoreCase("Programming"))
                .thenReturn(List.of(question));

        mockMvc.perform(get("/questions/category/Programming"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].category").value("Programming"));
    }

    @Test
    void createQuestionSuccessfully() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Shriya");
        user.setEmail("shriya@example.com");
        user.setPassword("password123");
        user.setRole("USER");

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(user));

        when(questionRepository.save(org.mockito.ArgumentMatchers.any(Question.class)))
                .thenAnswer(invocation -> {
                    Question savedQuestion = invocation.getArgument(0);
                    savedQuestion.setId(1L);
                    return savedQuestion;
                });

        String questionJson = """
                {
                    "title": "How to learn Java?",
                    "description": "I want to learn Java programming.",
                    "category": "Programming",
                    "tags": "Java,Beginner"
                }
                """;

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/questions")
                        .param("userId", "1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(questionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("How to learn Java?"))
                .andExpect(jsonPath("$.status").value("OPEN"))
                .andExpect(jsonPath("$.views").value(0));
    }

    @Test
    void createQuestionWithNonexistentUserDoesNotSaveQuestion() throws Exception {
        when(userRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        String questionJson = """
                {
                    "title": "How to learn Java?",
                    "description": "I want to learn Java programming.",
                    "category": "Programming",
                    "tags": "Java,Beginner"
                }
                """;

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/questions")
                        .param("userId", "999")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(questionJson))
                .andExpect(status().isNotFound())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string(""));

        org.mockito.Mockito.verify(questionRepository, org.mockito.Mockito.never())
                .save(org.mockito.ArgumentMatchers.any(Question.class));
    }

    @Test
    void updateQuestionSuccessfully() throws Exception {
        Question existingQuestion = new Question();
        existingQuestion.setId(1L);
        existingQuestion.setTitle("Old title");
        existingQuestion.setDescription("Old description");
        existingQuestion.setCategory("Programming");
        existingQuestion.setTags("Java");

        User owner = new User();
        owner.setId(1L);
        existingQuestion.setUser(owner);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(existingQuestion));

        when(questionRepository.save(org.mockito.ArgumentMatchers.any(Question.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        String updatedQuestionJson = """
                {
                    "title": "Updated title",
                    "description": "Updated description",
                    "category": "Programming",
                    "tags": "Java,Spring"
                }
                """;

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/1")
                        .param("userId", "1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(updatedQuestionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated title"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.tags").value("Java,Spring"));
    }

    @Test
    void deleteQuestionSuccessfully() throws Exception {
        Question question = new Question();
        User user = new User();
        user.setId(1L);
        question.setUser(user);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .delete("/questions/1")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("Question deleted successfully"));

        org.mockito.Mockito.verify(questionRepository).delete(question);
    }

    @Test
    void upvoteWithNonexistentUserReturnsNotFoundMessage() throws Exception {
        Question question = new Question();
        question.setId(1L);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(userRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/1/upvote")
                        .param("userId", "999"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("User not found"));

        org.mockito.Mockito.verify(voteService, org.mockito.Mockito.never())
                .castVote(999L, 1L, "UPVOTE");
    }

    @Test
    void upvoteQuestionSuccessfully() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("How to learn Java?");
        question.setDescription("I want to learn Java programming.");
        question.setCategory("Programming");
        question.setUpvotes(0);

        User user = new User();
        user.setId(2L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(userRepository.findById(2L))
                .thenReturn(java.util.Optional.of(user));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/1/upvote")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("Question upvoted successfully"));

        org.mockito.Mockito.verify(voteService).castVote(2L, 1L, "UPVOTE");
        org.mockito.Mockito.verify(questionVoteRepository, org.mockito.Mockito.never())
                .save(org.mockito.ArgumentMatchers.any(com.example.entity.QuestionVote.class));
    }

    @Test
    void upvoteAlreadyVotedQuestionReturnsDuplicateVoteMessage() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("How to learn Java?");
        question.setDescription("I want to learn Java programming.");
        question.setCategory("Programming");

        User user = new User();
        user.setId(2L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(userRepository.findById(2L))
                .thenReturn(java.util.Optional.of(user));

        org.mockito.Mockito.doThrow(
                new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.CONFLICT,
                        "You have already voted on this question"))
                .when(voteService).castVote(2L, 1L, "UPVOTE");

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/1/upvote")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("You have already voted on this question"));

        org.mockito.Mockito.verify(voteService).castVote(2L, 1L, "UPVOTE");
        org.mockito.Mockito.verify(questionVoteRepository, org.mockito.Mockito.never())
                .save(org.mockito.ArgumentMatchers.any(com.example.entity.QuestionVote.class));
    }

    @Test
    void downvoteNonexistentQuestionReturnsNotFoundMessage() throws Exception {
        when(questionRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/999/downvote")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("Question not found"));

        org.mockito.Mockito.verify(voteService, org.mockito.Mockito.never())
                .castVote(1L, 999L, "DOWNVOTE");
    }

    @Test
    void downvoteWithNonexistentUserReturnsNotFoundMessage() throws Exception {
        Question question = new Question();
        question.setId(1L);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(userRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/1/downvote")
                        .param("userId", "999"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("User not found"));

        org.mockito.Mockito.verify(voteService, org.mockito.Mockito.never())
                .castVote(999L, 1L, "DOWNVOTE");
    }

    @Test
    void downvoteQuestionSuccessfully() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");
        question.setDescription("Java description");
        question.setCategory("Programming");
        question.setDownvotes(0);

        User user = new User();
        user.setId(2L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(userRepository.findById(2L))
                .thenReturn(java.util.Optional.of(user));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/1/downvote")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("Question downvoted successfully"));

        org.mockito.Mockito.verify(voteService).castVote(2L, 1L, "DOWNVOTE");
        org.mockito.Mockito.verify(questionVoteRepository, org.mockito.Mockito.never())
                .save(org.mockito.ArgumentMatchers.any(com.example.entity.QuestionVote.class));
    }

@Test
void downvoteAlreadyVotedQuestionReturnsDuplicateVoteMessage() throws Exception {
    Question question = new Question();
    question.setId(1L);
    question.setTitle("Java Question");
    question.setDescription("Java description");
    question.setCategory("Programming");

    User user = new User();
    user.setId(2L);
    user.setName("Test User");
    user.setEmail("test@example.com");

    when(questionRepository.findById(1L))
            .thenReturn(java.util.Optional.of(question));
    when(userRepository.findById(2L))
            .thenReturn(java.util.Optional.of(user));

    org.mockito.Mockito.doThrow(
            new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.CONFLICT,
                    "You have already voted on this question"))
            .when(voteService).castVote(2L, 1L, "DOWNVOTE");

    mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                    .put("/questions/1/downvote")
                    .param("userId", "2"))
            .andExpect(status().isConflict());

    org.mockito.Mockito.verify(voteService).castVote(2L, 1L, "DOWNVOTE");
    org.mockito.Mockito.verify(questionVoteRepository, org.mockito.Mockito.never())
            .save(org.mockito.ArgumentMatchers.any(com.example.entity.QuestionVote.class));
}

    @Test
    void downvoteQuestionDoesNotReduceReputationBelowZero() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");
        question.setDescription("Java description");
        question.setCategory("Programming");
        question.setDownvotes(0);

        User voter = new User();
        voter.setId(2L);
        voter.setName("Voter");
        voter.setEmail("voter@example.com");

        User owner = new User();
        owner.setId(1L);
        owner.setName("Question Owner");
        owner.setEmail("owner@example.com");
        owner.setReputation(0);
        question.setUser(owner);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(userRepository.findById(2L))
                .thenReturn(java.util.Optional.of(voter));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/1/downvote")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("Question downvoted successfully"));

        org.mockito.Mockito.verify(voteService).castVote(2L, 1L, "DOWNVOTE");
        org.junit.jupiter.api.Assertions.assertEquals(0, owner.getReputation());
        org.mockito.Mockito.verify(reputationHistoryRepository, org.mockito.Mockito.never())
                .save(org.mockito.ArgumentMatchers.any(com.example.entity.ReputationHistory.class));
    }

    @Test
    void downvoteQuestionCreatesNotificationForOwner() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");
        question.setDescription("Java description");
        question.setCategory("Programming");
        question.setDownvotes(0);

        User voter = new User();
        voter.setId(2L);
        voter.setName("Voter");
        voter.setEmail("voter@example.com");

        User owner = new User();
        owner.setId(1L);
        owner.setName("Question Owner");
        owner.setEmail("owner@example.com");
        owner.setReputation(0);
        question.setUser(owner);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(userRepository.findById(2L))
                .thenReturn(java.util.Optional.of(voter));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/1/downvote")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("Question downvoted successfully"));

        org.mockito.Mockito.verify(voteService).castVote(2L, 1L, "DOWNVOTE");
        org.mockito.Mockito.verify(notificationRepository)
                .save(org.mockito.ArgumentMatchers.argThat(
                        notification -> notification.getUser().getId().equals(1L)
                                && notification.getMessage().equals("Someone downvoted your question")
                                && !notification.isRead()));
    }

    @Test
    void upvoteQuestionCreatesNotificationForOwner() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");
        question.setDescription("Java description");
        question.setCategory("Programming");
        question.setUpvotes(0);
        question.setDownvotes(0);

        User voter = new User();
        voter.setId(2L);
        voter.setName("Voter");
        voter.setEmail("voter@example.com");

        User owner = new User();
        owner.setId(1L);
        owner.setName("Question Owner");
        owner.setEmail("owner@example.com");
        owner.setReputation(0);
        question.setUser(owner);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(userRepository.findById(2L))
                .thenReturn(java.util.Optional.of(voter));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/1/upvote")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("Question upvoted successfully"));

        org.mockito.Mockito.verify(voteService).castVote(2L, 1L, "UPVOTE");
        org.mockito.Mockito.verify(notificationRepository)
                .save(org.mockito.ArgumentMatchers.argThat(
                        notification -> notification.getUser().getId().equals(1L)
                                && notification.getMessage().equals("Someone upvoted your question")
                                && !notification.isRead()));
    }

    @Test
    void upvoteOwnQuestionReturnsErrorMessage() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");
        question.setDescription("Java description");
        question.setCategory("Programming");
        question.setUpvotes(0);
        question.setDownvotes(0);

        User owner = new User();
        owner.setId(1L);
        owner.setName("Question Owner");
        owner.setEmail("owner@example.com");
        question.setUser(owner);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(owner));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/1/upvote")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("You cannot upvote your own question"));

        org.mockito.Mockito.verify(voteService, org.mockito.Mockito.never())
                .castVote(1L, 1L, "UPVOTE");
    }

    @Test
    void followQuestionSuccessfully() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");
        question.setDescription("Java description");
        question.setCategory("Programming");

        User user = new User();
        user.setId(2L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(userRepository.findById(2L))
                .thenReturn(java.util.Optional.of(user));
        when(questionFollowRepository.findByUserIdAndQuestionId(2L, 1L))
                .thenReturn(java.util.Optional.empty());

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/questions/1/follow")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("Question followed successfully"));

        org.mockito.Mockito.verify(questionFollowRepository)
                .save(org.mockito.ArgumentMatchers.any(com.example.entity.QuestionFollow.class));
    }

    @Test
    void followOwnQuestionReturnsErrorMessage() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");
        question.setDescription("Java description");
        question.setCategory("Programming");

        User owner = new User();
        owner.setId(1L);
        owner.setName("Question Owner");
        owner.setEmail("owner@example.com");
        question.setUser(owner);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(owner));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/questions/1/follow")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("You cannot follow your own question"));

        org.mockito.Mockito.verify(questionFollowRepository, org.mockito.Mockito.never())
                .save(org.mockito.ArgumentMatchers.any(com.example.entity.QuestionFollow.class));
    }

    @Test
    void followAlreadyFollowedQuestionReturnsErrorMessage() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");
        question.setDescription("Java description");
        question.setCategory("Programming");

        User user = new User();
        user.setId(2L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        com.example.entity.QuestionFollow existingFollow = new com.example.entity.QuestionFollow();

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(userRepository.findById(2L))
                .thenReturn(java.util.Optional.of(user));
        when(questionFollowRepository.findByUserIdAndQuestionId(2L, 1L))
                .thenReturn(java.util.Optional.of(existingFollow));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/questions/1/follow")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("You are already following this question"));

        org.mockito.Mockito.verify(questionFollowRepository, org.mockito.Mockito.never())
                .save(org.mockito.ArgumentMatchers.any(com.example.entity.QuestionFollow.class));
    }

    @Test
    void followNonexistentQuestionReturnsNotFoundMessage() throws Exception {
        when(questionRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());
        when(userRepository.findById(2L))
                .thenReturn(java.util.Optional.of(new User()));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/questions/999/follow")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("Question not found"));

        org.mockito.Mockito.verify(questionFollowRepository, org.mockito.Mockito.never())
                .save(org.mockito.ArgumentMatchers.any(com.example.entity.QuestionFollow.class));
    }

    @Test
    void unfollowQuestionSuccessfully() throws Exception {
        com.example.entity.QuestionFollow follow = new com.example.entity.QuestionFollow();

        when(questionFollowRepository.findByUserIdAndQuestionId(2L, 1L))
                .thenReturn(java.util.Optional.of(follow));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .delete("/questions/1/follow")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("Question unfollowed successfully"));

        org.mockito.Mockito.verify(questionFollowRepository).delete(follow);
    }

    @Test
    void unfollowQuestionWhenNotFollowingReturnsErrorMessage() throws Exception {
        when(questionFollowRepository.findByUserIdAndQuestionId(2L, 1L))
                .thenReturn(java.util.Optional.empty());

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .delete("/questions/1/follow")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("You are not following this question"));

        org.mockito.Mockito.verify(questionFollowRepository, org.mockito.Mockito.never())
                .delete(org.mockito.ArgumentMatchers.any(com.example.entity.QuestionFollow.class));
    }

    @Test
    void isFollowingQuestionReturnsTrueWhenFollowing() throws Exception {
        when(questionFollowRepository.existsByUserIdAndQuestionId(2L, 1L))
                .thenReturn(true);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/questions/1/is-following")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("true"));
    }

    @Test
    void isFollowingQuestionReturnsFalseWhenNotFollowing() throws Exception {
        when(questionFollowRepository.existsByUserIdAndQuestionId(2L, 1L))
                .thenReturn(false);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/questions/1/is-following")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("false"));
    }

    @Test
    void getQuestionFollowersCountReturnsCorrectCount() throws Exception {
        when(questionFollowRepository.countByQuestionId(1L))
                .thenReturn(5L);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/questions/1/followers-count"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.questionId").value(1))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.followersCount").value(5));
    }

    @Test
    void getQuestionFollowersReturnsList() throws Exception {
        com.example.entity.QuestionFollow follow = new com.example.entity.QuestionFollow();

        User user = new User();
        user.setId(2L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");

        follow.setUser(user);
        follow.setQuestion(question);

        when(questionFollowRepository.findByQuestionId(1L))
                .thenReturn(List.of(follow));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/questions/1/followers"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$[0].user.id").value(2))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$[0].question.id").value(1));
    }

    @Test
    void getQuestionFollowersReturnsEmptyListWhenNoFollowers() throws Exception {
        when(questionFollowRepository.findByQuestionId(1L))
                .thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/questions/1/followers"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.length()").value(0));
    }

    @Test
    void followQuestionCreatesNotificationForOwner() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");
        question.setDescription("Java description");
        question.setCategory("Programming");

        User voter = new User();
        voter.setId(2L);
        voter.setName("Follower");
        voter.setEmail("follower@example.com");

        User owner = new User();
        owner.setId(1L);
        owner.setName("Question Owner");
        owner.setEmail("owner@example.com");
        question.setUser(owner);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(userRepository.findById(2L))
                .thenReturn(java.util.Optional.of(voter));
        when(questionFollowRepository.findByUserIdAndQuestionId(2L, 1L))
                .thenReturn(java.util.Optional.empty());

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/questions/1/follow")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("Question followed successfully"));

        org.mockito.Mockito.verify(notificationRepository)
                .save(org.mockito.ArgumentMatchers.argThat(
                        notification -> notification.getUser().getId().equals(1L)
                                && notification.getMessage().equals("Someone followed your question")
                                && !notification.isRead()));
    }

    @Test
    void shareQuestionSuccessfully() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");
        question.setDescription("Java description");
        question.setCategory("Programming");
        question.setShares(0);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/1/share"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("Question shared successfully"));

        org.junit.jupiter.api.Assertions.assertEquals(1, question.getShares());
        org.mockito.Mockito.verify(questionRepository).save(question);
    }

    @Test
    void shareNonexistentQuestionReturnsNotFoundMessage() throws Exception {
        when(questionRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/999/share"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("Question not found"));

        org.mockito.Mockito.verify(questionRepository, org.mockito.Mockito.never())
                .save(org.mockito.ArgumentMatchers.any(Question.class));
    }

    @Test
    void getQuestionViewsByUserReturnsCorrectCount() throws Exception {
        when(questionViewRepository.countByQuestionIdAndUserId(1L, 2L))
                .thenReturn(3L);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/questions/1/views-by-user")
                        .param("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string("3"));
    }

    @Test
    void getQuestionAnalyticsReturnsCorrectData() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");
        question.setViews(10);
        question.setUpvotes(4);
        question.setDownvotes(1);
        question.setShares(2);
        question.setStatus("OPEN");

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(answerRepository.findByQuestionId(1L))
                .thenReturn(java.util.Collections.emptyList());
        when(questionFollowRepository.countByQuestionId(1L))
                .thenReturn(3L);
        when(commentRepository.countByQuestionId(1L))
                .thenReturn(2L);
        when(answerRepository.findByQuestionIdAndAcceptedTrue(1L))
                .thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/questions/1/analytics"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.views").value(10))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.upvotes").value(4))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.downvotes").value(1))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.answerCount").value(0))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.followersCount").value(3))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.shares").value(2))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.commentCount").value(2))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.acceptedAnswer").value(false))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.status").value("OPEN"));
    }

    @Test
    void getQuestionAnalyticsForNonexistentQuestionReturnsEmptyBody() throws Exception {
        when(questionRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/questions/999/analytics"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string(""));
    }

    @Test
    void getQuestionEngagementReturnsCorrectScore() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");
        question.setViews(10);
        question.setUpvotes(4);
        question.setShares(2);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(answerRepository.findByQuestionId(1L))
                .thenReturn(java.util.Collections.emptyList());
        when(commentRepository.countByQuestionId(1L))
                .thenReturn(2L);
        when(questionFollowRepository.countByQuestionId(1L))
                .thenReturn(3L);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/questions/1/engagement"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.views").value(10))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.upvotes").value(4))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.answers").value(0))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.comments").value(2))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.followers").value(3))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.shares").value(2))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.engagementScore").value(34));
    }

    @Test
    void getQuestionEngagementForNonexistentQuestionReturnsEmptyBody() throws Exception {
        when(questionRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/questions/999/engagement"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .content().string(""));
    }

    @Test
    void getQuestionEngagementWithNoAnswersReturnsZero() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Java Question");
        question.setViews(5);
        question.setUpvotes(2);
        question.setShares(0);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));
        when(answerRepository.findByQuestionId(1L))
                .thenReturn(java.util.Collections.emptyList());
        when(commentRepository.countByQuestionId(1L))
                .thenReturn(0L);
        when(questionFollowRepository.countByQuestionId(1L))
                .thenReturn(0L);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/questions/1/engagement"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.answers").value(0))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.comments").value(0))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.followers").value(0));
    }

    @Test
    void getQuestionEngagementWithNoActivityReturnsZeroScore() throws Exception {
        Question question = new Question();
        question.setId(2L);
        question.setTitle("New Question");
        question.setViews(0);
        question.setUpvotes(0);
        question.setShares(0);

        when(questionRepository.findById(2L))
                .thenReturn(java.util.Optional.of(question));
        when(answerRepository.findByQuestionId(2L))
                .thenReturn(java.util.Collections.emptyList());
        when(commentRepository.countByQuestionId(2L))
                .thenReturn(0L);
        when(questionFollowRepository.countByQuestionId(2L))
                .thenReturn(0L);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/questions/2/engagement"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.engagementScore").value(0));
    }

    @Test
    void deleteQuestionByAnotherUserReturnsForbidden() throws Exception {
        Question question = new Question();
        User owner = new User();
        owner.setId(1L);
        question.setUser(owner);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .delete("/questions/1")
                        .param("userId", "2"))
                .andExpect(status().isForbidden());

        org.mockito.Mockito.verify(questionRepository, org.mockito.Mockito.never())
                .delete(question);
    }

    @Test
    void deleteQuestionWithInvalidIdReturnsBadRequest() throws Exception {
        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .delete("/questions/0")
                        .param("userId", "1"))
                .andExpect(status().isBadRequest());

        org.mockito.Mockito.verify(questionRepository, org.mockito.Mockito.never())
                .delete(org.mockito.ArgumentMatchers.any(Question.class));
    }

    @Test
    void updateQuestionByAnotherUserReturnsForbidden() throws Exception {
        Question question = new Question();
        question.setId(1L);

        User owner = new User();
        owner.setId(1L);
        question.setUser(owner);

        when(questionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(question));

        String updatedQuestionJson = """
                {
                    "title": "Hacked title",
                    "description": "Changed description",
                    "category": "Programming",
                    "tags": "Java"
                }
                """;

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/questions/1")
                        .param("userId", "2")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(updatedQuestionJson))
                .andExpect(status().isForbidden());

        org.mockito.Mockito.verify(questionRepository, org.mockito.Mockito.never())
                .save(org.mockito.ArgumentMatchers.any(Question.class));
    }

    @Test
    void createQuestionWithTooLongTitleReturnsBadRequest() throws Exception {
        String longTitle = "A".repeat(151);

        String questionJson = """
                {
                    "title": "%s",
                    "description": "How does Java work?",
                    "category": "Programming",
                    "tags": "Java"
                }
                """.formatted(longTitle);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/questions")
                        .param("userId", "1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(questionJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createQuestionWithBlankTitleReturnsBadRequest() throws Exception {
        String questionJson = """
                {
                    "title": "   ",
                    "description": "How does Java work?",
                    "category": "Programming",
                    "tags": "Java"
                }
                """;

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/questions")
                        .param("userId", "1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(questionJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createQuestionWithTooLongDescriptionReturnsBadRequest() throws Exception {
        String longDescription = "A".repeat(5001);

        String questionJson = """
                {
                    "title": "Java question",
                    "description": "%s",
                    "category": "Programming",
                    "tags": "Java"
                }
                """.formatted(longDescription);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/questions")
                        .param("userId", "1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(questionJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createQuestionWithTooLongCategoryReturnsBadRequest() throws Exception {
        String longCategory = "A".repeat(51);

        String questionJson = """
                {
                    "title": "Java question",
                    "description": "How does Java work?",
                    "category": "%s",
                    "tags": "Java"
                }
                """.formatted(longCategory);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/questions")
                        .param("userId", "1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(questionJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createQuestionWithTooLongTagsReturnsBadRequest() throws Exception {
        String longTags = "A".repeat(201);

        String questionJson = """
                {
                    "title": "Java question",
                    "description": "How does Java work?",
                    "category": "Programming",
                    "tags": "%s"
                }
                """.formatted(longTags);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/questions")
                        .param("userId", "1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(questionJson))
                .andExpect(status().isBadRequest());
    }
}
