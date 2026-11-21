
package com.example.controller;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.entity.User;
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

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private QuestionRepository questionRepository;

    @MockitoBean
    private QuestionViewRepository questionViewRepository;

    @MockitoBean
    private AnswerRepository answerRepository;

    @MockitoBean
    private NotificationRepository notificationRepository;

    @MockitoBean
    private BookmarkRepository bookmarkRepository;

    @MockitoBean
    private UserFollowRepository userFollowRepository;

    @MockitoBean
    private QuestionFollowRepository questionFollowRepository;

    @MockitoBean
    private NotificationPreferenceRepository notificationPreferenceRepository;

    @MockitoBean
    private ReputationHistoryRepository reputationHistoryRepository;

    @MockitoBean
    private QuestionVoteRepository questionVoteRepository;

    @MockitoBean
    private AnswerVoteRepository answerVoteRepository;

    @MockitoBean
    private CommentRepository commentRepository;

    @Test
    void registerUserWithNewEmail() throws Exception {

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("Test123!"))
                .thenReturn("encoded-password");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    user.setId(1L);
                    return user;
                });

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Test User",
                        "email": "test@example.com",
                        "password": "Test123!"
                    }
                    """))
                .andExpect(status().isOk());
    }
    
    @Test
    void registerUserWithExistingEmail() throws Exception {

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(new User()));

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Another User",
                        "email": "test@example.com",
                        "password": "Test123!"
                    }
                    """))
                .andExpect(status().isConflict());
    }
    
    
    @Test
    void loginUserWithInvalidPassword() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("encoded-password");
        user.setReputation(0);
        user.setRole("USER");
        user.setActive(true);

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("Wrong123!", "encoded-password"))
                .thenReturn(false);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "email": "test@example.com",
                        "password": "Wrong123!"
                    }
                    """))
                .andExpect(status().isUnauthorized())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().string(""));
    }
    @Test
    void loginUserWithInactiveAccount() throws Exception {

        User user = new User();
        user.setId(2L);
        user.setName("Inactive User");
        user.setEmail("inactive@example.com");
        user.setPassword("encoded-password");
        user.setReputation(0);
        user.setRole("USER");
        user.setActive(false);

        when(userRepository.findByEmail("inactive@example.com"))
                .thenReturn(Optional.of(user));

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "email": "inactive@example.com",
                        "password": "Test123!"
                    }
                    """))
                .andExpect(status().isUnauthorized());
    }
    
    @Test
    void loginUserWithValidCredentials() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("encoded-password");
        user.setReputation(0);
        user.setRole("USER");
        user.setActive(true);

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("Test123!", "encoded-password"))
                .thenReturn(true);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "email": "test@example.com",
                        "password": "Test123!"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.name").value("Test User"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.role").value("USER"));
    }
    
    @Test
    void registerUserWithInvalidEmail() throws Exception {

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Test User",
                        "email": "invalid-email",
                        "password": "Test123!"
                    }
                    """))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void registerUserWithMissingName() throws Exception {

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "",
                        "email": "test@example.com",
                        "password": "Test123!"
                    }
                    """))
                .andExpect(status().isBadRequest());
    }
}