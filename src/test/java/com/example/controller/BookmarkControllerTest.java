
package com.example.controller;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.entity.Bookmark;
import com.example.entity.Question;
import com.example.entity.User;
import com.example.repository.BookmarkRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.UserRepository;
import com.example.service.BookmarkService;

@WebMvcTest(BookmarkController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookmarkRepository bookmarkRepository;

    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
private BookmarkService bookmarkService;

    @MockitoBean
    private QuestionRepository questionRepository;

  
@Test
void getUserBookmarksReturnsEmptyList() throws Exception {
    when(bookmarkService.getUserBookmarks(1L))
            .thenReturn(Collections.emptyList());

    mockMvc.perform(get("/bookmarks/user/1"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));
}


@Test
void addBookmarkWithNonexistentUserReturnsEmptyResponse() throws Exception {
    when(bookmarkService.addBookmark(999L, 1L))
            .thenThrow(new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "User not found"));

    mockMvc.perform(post("/bookmarks")
                    .param("userId", "999")
                    .param("questionId", "1"))
            .andExpect(status().isNotFound());

    verify(bookmarkService).addBookmark(999L, 1L);
}



@Test
void deleteNonexistentBookmarkReturnsNotFoundMessage() throws Exception {
    when(bookmarkService.deleteBookmark(1L, 1L))
            .thenReturn("Bookmark not found");

    mockMvc.perform(delete("/bookmarks")
                    .param("userId", "1")
                    .param("questionId", "1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Bookmark not found"));

    verify(bookmarkService).deleteBookmark(1L, 1L);
}


@Test
void addBookmarkSuccessfully() throws Exception {
    User user = new User();
    user.setId(1L);

    Question question = new Question();
    question.setId(1L);
    question.setTitle("Java Question");

    Bookmark bookmark = new Bookmark();
    bookmark.setUser(user);
    bookmark.setQuestion(question);

    when(bookmarkService.addBookmark(1L, 1L))
            .thenReturn(bookmark);

    mockMvc.perform(post("/bookmarks")
                    .param("userId", "1")
                    .param("questionId", "1"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    org.hamcrest.Matchers.containsString("Java Question")));

    verify(bookmarkService).addBookmark(1L, 1L);
}



@Test
void addBookmarkWhenLimitReachedDoesNotSave() throws Exception {
    when(bookmarkService.addBookmark(1L, 1L))
            .thenThrow(new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Bookmark limit reached"));

    mockMvc.perform(post("/bookmarks")
                    .param("userId", "1")
                    .param("questionId", "1"))
            .andExpect(status().isBadRequest());

    verify(bookmarkService).addBookmark(1L, 1L);
}


@Test
void addDuplicateBookmarkDoesNotSaveAgain() throws Exception {
    when(bookmarkService.addBookmark(1L, 1L))
            .thenThrow(new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.CONFLICT,
                    "Bookmark already exists"));

    mockMvc.perform(post("/bookmarks")
                    .param("userId", "1")
                    .param("questionId", "1"))
            .andExpect(status().isConflict());

    verify(bookmarkService).addBookmark(1L, 1L);
}





@Test
void deleteExistingBookmarkSuccessfully() throws Exception {
    when(bookmarkService.deleteBookmark(1L, 1L))
            .thenReturn("Bookmark removed successfully");

    mockMvc.perform(delete("/bookmarks")
                    .param("userId", "1")
                    .param("questionId", "1"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "Bookmark removed successfully"));

    verify(bookmarkService).deleteBookmark(1L, 1L);
}



@Test
void getUserBookmarksReturnsSavedBookmark() throws Exception {
    User user = new User();
    user.setId(1L);

    Question question = new Question();
    question.setId(1L);
    question.setTitle("Java Question");

    Bookmark bookmark = new Bookmark();
    bookmark.setUser(user);
    bookmark.setQuestion(question);

    when(bookmarkService.getUserBookmarks(1L))
            .thenReturn(Collections.singletonList(bookmark));

    mockMvc.perform(get("/bookmarks/user/1"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    org.hamcrest.Matchers.containsString("Java Question")));
}


@Test
void addBookmarkWithNonexistentQuestionReturnsEmptyResponse() throws Exception {
    when(bookmarkService.addBookmark(1L, 1L))
            .thenThrow(new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Question not found"));

    mockMvc.perform(post("/bookmarks")
                    .param("userId", "1")
                    .param("questionId", "1"))
            .andExpect(status().isNotFound());

    verify(bookmarkService).addBookmark(1L, 1L);
}



@Test
void deleteBookmarkTwiceReturnsNotFound() throws Exception {
    when(bookmarkService.deleteBookmark(1L, 1L))
            .thenThrow(new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Bookmark not found"));

    mockMvc.perform(delete("/bookmarks")
                    .param("userId", "1")
                    .param("questionId", "1"))
            .andExpect(status().isNotFound());
}


@Test
void getUserBookmarksReturnsEmptyListWhenNoneExist() throws Exception {
    when(bookmarkService.getUserBookmarks(99L))
            .thenReturn(Collections.emptyList());

    mockMvc.perform(get("/bookmarks/user/99"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));

    verify(bookmarkService).getUserBookmarks(99L);
}


@Test
void checkBookmarkReturnsTrueWhenBookmarkExists() throws Exception {
    when(bookmarkService.checkBookmark(1L, 2L))
            .thenReturn(true);

    mockMvc.perform(get("/bookmarks/check")
                    .param("userId", "1")
                    .param("questionId", "2"))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
}


@Test
void checkBookmarkReturnsFalseWhenBookmarkDoesNotExist() throws Exception {

    when(bookmarkRepository.findByUserIdAndQuestionId(1L, 2L))
            .thenReturn(Optional.empty());

    mockMvc.perform(get("/bookmarks/check")
                    .param("userId", "1")
                    .param("questionId", "2"))
            .andExpect(status().isOk())
            .andExpect(content().string("false"));
}


@Test
void deleteAllUserBookmarksWhenNoneExist() throws Exception {
    when(bookmarkService.deleteAllUserBookmarks(99L))
            .thenReturn("No bookmarks found for this user");

    mockMvc.perform(delete("/bookmarks/user/99"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "No bookmarks found for this user"));
}



@Test
void deleteAllUserBookmarksSuccessfully() throws Exception {
    when(bookmarkService.deleteAllUserBookmarks(3L))
            .thenReturn("All bookmarks removed successfully");

    mockMvc.perform(delete("/bookmarks/user/3"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "All bookmarks removed successfully"));

    verify(bookmarkService).deleteAllUserBookmarks(3L);
}


@Test
void getBookmarkByIdReturnsBookmarkWhenExists() throws Exception {
    Bookmark bookmark = new Bookmark();

    when(bookmarkRepository.findById(1L))
            .thenReturn(Optional.of(bookmark));

    mockMvc.perform(get("/bookmarks/1"))
            .andExpect(status().isOk());
}

@Test
void deleteBookmarkByIdWhenExists() throws Exception {
    when(bookmarkService.deleteBookmarkById(1L, 1L))
            .thenReturn("Bookmark deleted successfully");

    mockMvc.perform(delete("/bookmarks/1")
                    .param("userId", "1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Bookmark deleted successfully"));

    verify(bookmarkService).deleteBookmarkById(1L, 1L);
}


@Test
void deleteBookmarkByIdWhenNotFound() throws Exception {
    when(bookmarkService.deleteBookmarkById(999L, 1L))
            .thenReturn("Bookmark not found");

    mockMvc.perform(delete("/bookmarks/999")
                    .param("userId", "1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Bookmark not found"));

    verify(bookmarkService).deleteBookmarkById(999L, 1L);
}


@Test
void countBookmarksForQuestionReturnsCount() throws Exception {
    when(bookmarkService.countBookmarksForQuestion(1L))
            .thenReturn(3L);

    mockMvc.perform(get("/bookmarks/count/question/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("3"));
}


@Test
void countBookmarksForInvalidQuestionIdReturnsZero() throws Exception {
    mockMvc.perform(get("/bookmarks/count/question/0"))
            .andExpect(status().isOk())
            .andExpect(content().string("0"));

    verify(bookmarkRepository, never()).countByQuestionId(0L);
}

@Test
void countBookmarksForUserWithNoBookmarksReturnsZero() throws Exception {
    when(bookmarkRepository.countByUserId(999L)).thenReturn(0L);

    mockMvc.perform(get("/bookmarks/count/999"))
            .andExpect(status().isOk())
            .andExpect(content().string("0"));
}


@Test
void countBookmarksForUserWithBookmarksReturnsCount() throws Exception {
    when(bookmarkService.countUserBookmarks(3L))
            .thenReturn(5L);

    mockMvc.perform(get("/bookmarks/count/3"))
            .andExpect(status().isOk())
            .andExpect(content().string("5"));
}



@Test
void deleteAllUserBookmarksDeletesOnlySelectedUserBookmarks() throws Exception {
    when(bookmarkService.deleteAllUserBookmarks(3L))
            .thenReturn("All bookmarks removed successfully");

    mockMvc.perform(delete("/bookmarks/user/3"))
            .andExpect(status().isOk())
            .andExpect(content().string("All bookmarks removed successfully"));

    verify(bookmarkService).deleteAllUserBookmarks(3L);
}


@Test
void countBookmarksForInvalidUserIdReturnsZero() throws Exception {
    mockMvc.perform(get("/bookmarks/count/0"))
            .andExpect(status().isOk())
            .andExpect(content().string("0"));

    verify(bookmarkRepository, never()).countByUserId(0L);
}


@Test
void checkBookmarkWithInvalidIdsReturnsFalse() throws Exception {
    when(bookmarkService.checkBookmark(0L, 1L))
            .thenReturn(false);

    mockMvc.perform(get("/bookmarks/check")
                    .param("userId", "0")
                    .param("questionId", "1"))
            .andExpect(status().isOk())
            .andExpect(content().string("false"));

    verify(bookmarkService).checkBookmark(0L, 1L);
}




@Test
void addBookmarkWithInvalidUserIdDoesNotSave() throws Exception {
    when(bookmarkService.addBookmark(0L, 1L))
            .thenThrow(new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "User ID and question ID must be positive"));

    mockMvc.perform(post("/bookmarks")
                    .param("userId", "0")
                    .param("questionId", "1"))
            .andExpect(status().isBadRequest());

    verify(bookmarkService).addBookmark(0L, 1L);
}


@Test
void deleteBookmarkWithInvalidIdsReturnsBadRequest() throws Exception {
    when(bookmarkService.deleteBookmark(0L, 1L))
            .thenThrow(new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "User ID and question ID must be positive"));

    mockMvc.perform(delete("/bookmarks")
                    .param("userId", "0")
                    .param("questionId", "1"))
            .andExpect(status().isBadRequest());

    verify(bookmarkService).deleteBookmark(0L, 1L);
}




}