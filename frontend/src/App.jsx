
import { useEffect, useState } from "react";
const API_BASE_URL = import.meta.env.PROD
  ? "https://queryhub-xhvb.onrender.com"
  : "";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  PieChart,
  Pie,
  Cell,
  Legend,
} from "recharts";
import "./App.css";
function apiFetch(url, options) {
  return fetch(`${API_BASE_URL}${url}`, options);
}

function App() {
  const [search, setSearch] = useState("");
  const [answerSort, setAnswerSort] = useState("ACCEPTED");
  const [questions, setQuestions] = useState([]);
  const [answerPage, setAnswerPage] = useState({});
const answersPerPage = 5;
  const [drafts, setDrafts] = useState([]);
  const [answerBookmarks, setAnswerBookmarks] = useState([]);
const [answerBookmarkLoading, setAnswerBookmarkLoading] = useState(false);
const [showSavedAnswers, setShowSavedAnswers] = useState(false);
  const [scheduledQuestions, setScheduledQuestions] = useState([]);
const [showScheduledQuestions, setShowScheduledQuestions] = useState(false);
const [scheduledLoading, setScheduledLoading] = useState(false);
const [closedQuestions, setClosedQuestions] = useState([]);
const [showClosedQuestions, setShowClosedQuestions] = useState(false);
const [closedLoading, setClosedLoading] = useState(false);
const [closedError, setClosedError] = useState("");
const [draftsLoading, setDraftsLoading] = useState(false);
const [draftsError, setDraftsError] = useState("");
const [showDrafts, setShowDrafts] = useState(false);
const [editingDraftId, setEditingDraftId] = useState(null);
const [autoSaveMessage, setAutoSaveMessage] = useState("");
const [autoSaving, setAutoSaving] = useState(false);
const [showDraftRecovery, setShowDraftRecovery] = useState(false);
const [recoveryDraft, setRecoveryDraft] = useState(null);
  const [adminReportStats, setAdminReportStats] = useState(null);
const [adminReportLoading, setAdminReportLoading] = useState(false);
const [adminReports, setAdminReports] = useState([]);
const [adminReportsLoading, setAdminReportsLoading] = useState(false);
const [adminReportFilter, setAdminReportFilter] = useState("ALL");
const [adminUsers, setAdminUsers] = useState([]);
const [showEditHistory, setShowEditHistory] = useState(false);
const [editHistory, setEditHistory] = useState([]);
const [editHistoryLoading, setEditHistoryLoading] = useState(false);
const [compareHistory, setCompareHistory] = useState(null);
const [adminActivityLogs, setAdminActivityLogs] = useState([]);
const [adminActivityLogsLoading, setAdminActivityLogsLoading] = useState(false);
const [adminActivityPage, setAdminActivityPage] = useState(0);
const [adminActivityTotalPages, setAdminActivityTotalPages] = useState(0);
const [adminActivitySearch, setAdminActivitySearch] = useState("");
const [adminActivityStartDate, setAdminActivityStartDate] = useState("");
const [adminActivityEndDate, setAdminActivityEndDate] = useState("");
const [adminActivityLogsError, setAdminActivityLogsError] = useState("");
const [adminActivityFilter, setAdminActivityFilter] = useState("ALL");
const [adminUsersLoading, setAdminUsersLoading] = useState(false);
const [adminUsersError, setAdminUsersError] = useState("");
const [adminUserSearch, setAdminUserSearch] = useState("");
const [adminUserFilter, setAdminUserFilter] = useState("ALL");
const [adminReportError, setAdminReportError] = useState("");
const [adminDashboardStats, setAdminDashboardStats] = useState(null);
const [adminActivityStats, setAdminActivityStats] = useState(null);
const [adminReputationStats, setAdminReputationStats] = useState(null);
const [adminAnalyticsLoading, setAdminAnalyticsLoading] = useState(false);
const [adminAnalyticsError, setAdminAnalyticsError] = useState("");
  const [bookmarks, setBookmarks] = useState([]);
  const [followerCounts, setFollowerCounts] = useState({});
  const [followedQuestions, setFollowedQuestions] = useState([]);
const [followLoading, setFollowLoading] = useState(false);
const [followError, setFollowError] = useState("");
const [bookmarkLoading, setBookmarkLoading] = useState(false);
const [bookmarkError, setBookmarkError] = useState("");
const [showReportForm, setShowReportForm] = useState(false);
const [reportTargetType, setReportTargetType] = useState("");
const [reportTargetId, setReportTargetId] = useState(null);
const [reportReason, setReportReason] = useState("");
const [reportError, setReportError] = useState("");
const [reportSuccess, setReportSuccess] = useState("");
const [answerSearch, setAnswerSearch] = useState({});
const [reportLoading, setReportLoading] = useState(false);
  const [answerComments, setAnswerComments] = useState({});
const [commentDrafts, setCommentDrafts] = useState({});
const [commentLoading, setCommentLoading] = useState({});
const [commentErrors, setCommentErrors] = useState({});
const [visibleComments, setVisibleComments] = useState({});
const [editingCommentId, setEditingCommentId] = useState(null);
const [editedCommentContent, setEditedCommentContent] = useState("");
  const [category, setCategory] = useState("All");
  const [sortBy, setSortBy] = useState("newest");
  const [statusHistory, setStatusHistory] = useState([]);
const [showStatusHistory, setShowStatusHistory] = useState(false);
const [statusHistoryLoading, setStatusHistoryLoading] = useState(false);
const [statusHistoryError, setStatusHistoryError] = useState("");
const [selectedStatusQuestion, setSelectedStatusQuestion] = useState(null);
  const [trendingQuestions, setTrendingQuestions] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [showProfile, setShowProfile] = useState(false);
  const [hideReadNotifications, setHideReadNotifications] = useState(false);
  const [showNotifications, setShowNotifications] = useState(false);
  const [notifications, setNotifications] = useState([]);
  const [unreadCount, setUnreadCount] = useState(0);
  const [notificationLoading, setNotificationLoading] = useState(false);
  const [notificationError, setNotificationError] = useState("");
  const [notificationPage, setNotificationPage] = useState(1);
  const [notificationSearch, setNotificationSearch] = useState("");
  const [notificationFilter, setNotificationFilter] = useState("all");
  const [notificationSort, setNotificationSort] = useState("newest");
  const [newTitle, setNewTitle] = useState("");
  const [newDescription, setNewDescription] = useState("");
  const [newCategory, setNewCategory] = useState("Java");
  const [newTags, setNewTags] = useState("");
  const [expiryDate, setExpiryDate] = useState("");
  const [leaderboardUsers, setLeaderboardUsers] = useState([]);
const [leaderboardLoading, setLeaderboardLoading] = useState(false);
const [leaderboardError, setLeaderboardError] = useState("");
const [showLeaderboard, setShowLeaderboard] = useState(false);
  const [userId, setUserId] = useState(
    localStorage.getItem("queryhubUserId") || ""
  );
  const [authMode, setAuthMode] = useState("login");
  const [showAuth, setShowAuth] = useState(false);
const [authName, setAuthName] = useState("");

const [answerEditHistory, setAnswerEditHistory] = useState([]);
const [compareAnswerHistory, setCompareAnswerHistory] = useState(null);
const [currentAnswerContent, setCurrentAnswerContent] = useState("");
const [showAnswerEditHistory, setShowAnswerEditHistory] = useState(false);
const [answerEditHistoryLoading, setAnswerEditHistoryLoading] = useState(false);
const [answerEditHistoryError, setAnswerEditHistoryError] = useState("");
const [selectedHistoryAnswer, setSelectedHistoryAnswer] = useState(null);

const [editingAnswerId, setEditingAnswerId] = useState(null);
const [editingQuestionId, setEditingQuestionId] = useState(null);
const [editedQuestionTitle, setEditedQuestionTitle] = useState("");
const [editedQuestionDescription, setEditedQuestionDescription] = useState("");
const [editedQuestionCategory, setEditedQuestionCategory] = useState("");
const [editedAnswerContent, setEditedAnswerContent] = useState("");
const [authEmail, setAuthEmail] = useState("");
const [authPassword, setAuthPassword] = useState("");
const [authLoading, setAuthLoading] = useState(false);
const [authError, setAuthError] = useState("");
const [loggedInUser, setLoggedInUser] = useState(
  JSON.parse(localStorage.getItem("queryhubUser") || "null")
);

  const [items, setItems] = useState([]);
  const [questionComments, setQuestionComments] = useState({});
const [questionCommentDrafts, setQuestionCommentDrafts] = useState({});
const [questionCommentLoading, setQuestionCommentLoading] = useState({});
const [questionCommentErrors, setQuestionCommentErrors] = useState({});
const [visibleQuestionComments, setVisibleQuestionComments] = useState({});
const [editingQuestionCommentId, setEditingQuestionCommentId] = useState(null);
const [editedQuestionCommentContent, setEditedQuestionCommentContent] = useState("");
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");
  const [formMessage, setFormMessage] = useState("");

  const [expandedQuestionId, setExpandedQuestionId] = useState(null);
  const [answersByQuestion, setAnswersByQuestion] = useState({});
  const [answerDrafts, setAnswerDrafts] = useState({});
  const [answerLoading, setAnswerLoading] = useState({});
  const [answerSubmitting, setAnswerSubmitting] = useState({});
  const [answerErrors, setAnswerErrors] = useState({});
  const [answerVoting, setAnswerVoting] = useState({});

  const [profile, setProfile] = useState(null);
  const [badges, setBadges] = useState([]);
const [badgeLoading, setBadgeLoading] = useState(false);
const [badgeError, setBadgeError] = useState("");
  const [profileLoading, setProfileLoading] = useState(false);
  const [profileError, setProfileError] = useState("");
  const [myQuestions, setMyQuestions] = useState([]);
  const [myAnswers, setMyAnswers] = useState([]);
  const [activityLoading, setActivityLoading] = useState(false);
  const [activityError, setActivityError] = useState("");
  async function handleAuth(event) {
  event.preventDefault();
  setAuthError("");

  if (!authEmail.trim() || !authPassword.trim()) {
    setAuthError("Please enter your email and password.");
    return;
  }

  if (authMode === "register" && !authName.trim()) {
    setAuthError("Please enter your name.");
    return;
  }

  setAuthLoading(true);

  try {
   const endpoint =
  authMode === "login"
    ? "/users/login"
    : "/users/register";

    const requestBody =
      authMode === "login"
        ? {
            email: authEmail.trim(),
            password: authPassword,
          }
        : {
            name: authName.trim(),
            email: authEmail.trim(),
            password: authPassword,
          };

    const response = await apiFetch(endpoint, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
    });

    if (!response.ok) {
      if (response.status === 401) {
        throw new Error("Invalid email or password.");
      }

      if (response.status === 409) {
        throw new Error("This email is already registered. Please log in.");
      }

      throw new Error(
        (await response.text()) || `Request failed (${response.status}).`
      );
    }

    const user = await response.json();

    localStorage.setItem("queryhubUserId", String(user.id));
    localStorage.setItem("queryhubUser", JSON.stringify(user));

    setUserId(String(user.id));
    setLoggedInUser(user);
    setAuthPassword("");
    setAuthError("");
  setShowNotifications(false);

await fetchQuestions();
await fetchBookmarks();
await fetchQuestions();
await fetchBookmarks();
await fetchFollowedQuestions();

  } catch (err) {
    setAuthError(err.message || "Authentication failed.");
  } finally {
    setAuthLoading(false);
  }
}
async function saveDraft() {
  const parsedUserId = Number(userId);

  if (!parsedUserId || !loggedInUser) {
    setFormMessage("Please login to save a draft.");
    return;
  }

  if (
    !newTitle.trim() ||
    !newDescription.trim() ||
    !newCategory.trim()
  ) {
    setFormMessage("Please enter title, description and category to save a draft.");
    return;
  }

  setSubmitting(true);

  try {
    const draftData = {
      title: newTitle.trim(),
      description: newDescription.trim(),
      category: newCategory,
      tags: newTags.trim(),
    };

 const isEditing = editingDraftId !== null;

const response = await apiFetch(
  isEditing
    ? `/api/questions/drafts/${editingDraftId}?userId=${parsedUserId}`
    : `/api/questions/drafts?userId=${parsedUserId}`,
  {
    method: isEditing ? "PUT" : "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(draftData),
  }
);

    if (!response.ok) {
      throw new Error("Could not save draft.");
    }

    setNewTitle("");
    setNewDescription("");
    setNewCategory("Java");
    setNewTags("");
    setEditingDraftId(null);
await fetchDrafts();
    setFormMessage(
  isEditing ? "Draft updated successfully!" : "Draft saved successfully!"
);
    setShowForm(false);

  } catch (err) {
    setFormMessage(err.message || "Unable to save draft.");
  } finally {
    setSubmitting(false);
  }
}
  const categories = [
    "All",
    "Java",
    "Database",
    "Spring Boot",
    "Web Development",
  ];
  async function autoSaveDraft() {
  const parsedUserId = Number(userId);

 if (
  !parsedUserId ||
  !loggedInUser ||
  submitting ||
  !newTitle.trim() ||
  !newDescription.trim() ||
  !newCategory.trim()
) {
  return;
}

  setAutoSaving(true);

  try {
    const draftData = {
      title: newTitle.trim(),
      description: newDescription.trim(),
      category: newCategory,
      tags: newTags.trim(),
    };

    const isEditing = editingDraftId !== null;

    const response = await apiFetch(
      isEditing
        ? `/api/questions/drafts/${editingDraftId}?userId=${parsedUserId}`
        : `/api/questions/drafts?userId=${parsedUserId}`,
      {
        method: isEditing ? "PUT" : "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(draftData),
      }
    );

    if (!response.ok) {
      throw new Error("Auto-save failed");
    }

    const savedDraft = await response.json();

    if (!isEditing) {
      setEditingDraftId(savedDraft.id);
    }

    setAutoSaveMessage("Draft auto-saved!");
  } catch (error) {
    setAutoSaveMessage("Auto-save failed.");
  } finally {
    setAutoSaving(false);
  }
}
async function fetchAnswerBookmarks() {
  const parsedUserId = Number(userId);

  if (!parsedUserId) {
    setAnswerBookmarks([]);
    return;
  }

  setAnswerBookmarkLoading(true);

  try {
    const response = await apiFetch(
      `/api/answer-bookmarks/user/${parsedUserId}`
    );

    if (!response.ok) {
      throw new Error("Could not load saved answers.");
    }

    const data = await response.json();
    setAnswerBookmarks(data);
  } catch (error) {
    console.error(error);
  } finally {
    setAnswerBookmarkLoading(false);
  }
}
async function toggleAnswerBookmark(answerId) {
  const parsedUserId = Number(userId);

  if (!parsedUserId) {
    alert("Please login to bookmark answers.");
    return;
  }

  const isBookmarked = answerBookmarks.some(
    (bookmark) => Number(bookmark.answer?.id) === Number(answerId)
  );

  try {
    const url = isBookmarked
      ? `/api/answer-bookmarks?userId=${parsedUserId}&answerId=${answerId}`
      : `/api/answer-bookmarks?userId=${parsedUserId}&answerId=${answerId}`;

    const response = await apiFetch(url, {
      method: isBookmarked ? "DELETE" : "POST",
    });

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not update answer bookmark.");
    }

    await fetchAnswerBookmarks();
  } catch (error) {
    alert(error.message);
  }
}
async function fetchDrafts() {
  const parsedUserId = Number(userId);

  if (!parsedUserId) {
    setDraftsError("Please login to view your drafts.");
    return;
  }

  setDraftsLoading(true);
  setDraftsError("");

  try {
    const response = await apiFetch(
      `/api/questions/drafts/${parsedUserId}`
    );

    if (!response.ok) {
      throw new Error("Could not load drafts.");
    }

    const data = await response.json();
    setDrafts(data);
  } catch (err) {
    setDraftsError(err.message || "Unable to load drafts.");
  } finally {
    setDraftsLoading(false);
  }
}
async function fetchScheduledQuestions() {
  const parsedUserId = Number(userId);

  if (!parsedUserId) {
    setDraftsError("Please login to view scheduled questions.");
    return;
  }

  setScheduledLoading(true);
  setDraftsError("");

  try {
    const response = await apiFetch(
      `/api/questions/scheduled/${parsedUserId}`
    );

    if (!response.ok) {
      throw new Error("Could not load scheduled questions.");
    }

    const data = await response.json();
    setScheduledQuestions(data);
  } catch (error) {
    setDraftsError(error.message || "Unable to load scheduled questions.");
  } finally {
    setScheduledLoading(false);
  }
}
async function fetchClosedQuestions() {
  const parsedUserId = Number(userId);

  if (!parsedUserId) {
    setClosedError("Please login to view your closed questions.");
    return;
  }

  setClosedLoading(true);
  setClosedError("");

  try {
    const response = await apiFetch(
      `/api/questions/closed/${parsedUserId}`
    );

    if (!response.ok) {
      throw new Error("Could not load closed questions.");
    }

    const data = await response.json();
    setClosedQuestions(data);
  } catch (error) {
    setClosedError(error.message);
  } finally {
    setClosedLoading(false);
  }
}
async function fetchStatusHistory(questionId) {
  setStatusHistoryLoading(true);
  setStatusHistoryError("");
  setSelectedStatusQuestion(questionId);
  setShowStatusHistory(true);

  try {
    const response = await apiFetch(
     `/api/questions/status-history/${questionId}`
    );

  if (!response.ok) {
  const message = await response.text();
  throw new Error(
    `Status: ${response.status} - ${message}`
  );
}

    const data = await response.json();
    setStatusHistory(data);
  } catch (error) {
    setStatusHistoryError(error.message);
  } finally {
    setStatusHistoryLoading(false);
  }
}
async function reopenQuestion(questionId) {
  const parsedUserId = Number(userId);

  if (!parsedUserId) {
    setClosedError("Please login to reopen a question.");
    return;
  }

  try {
    const response = await apiFetch(
      `/api/questions/closed/${questionId}/reopen?userId=${parsedUserId}`,
      {
        method: "PUT",
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not reopen question.");
    }

    await fetchClosedQuestions();
    await fetchQuestions();

    setClosedError("Question reopened successfully!");
  } catch (error) {
    setClosedError(error.message);
  }
}
async function cancelScheduledQuestion(questionId) {
  const parsedUserId = Number(userId);

  if (!parsedUserId) {
    setDraftsError("Please login to cancel scheduling.");
    return;
  }

  try {
    const response = await apiFetch(
      `/api/questions/scheduled/${questionId}/cancel?userId=${parsedUserId}`,
      {
        method: "PUT",
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not cancel scheduling.");
    }

    await fetchScheduledQuestions();
    await fetchDrafts();

    setDraftsError("Scheduling cancelled. Question moved to My Drafts!");
  } catch (error) {
    setDraftsError(error.message || "Unable to cancel scheduling.");
  }
}
async function checkDraftRecovery() {
  const parsedUserId = Number(userId);

  if (!parsedUserId || !loggedInUser) {
    return;
  }

  try {
    const response = await apiFetch(
      `/api/questions/drafts/${parsedUserId}`
    );

    if (!response.ok) {
      throw new Error("Could not check saved drafts.");
    }

    const data = await response.json();

    if (data.length > 0) {
      const latestDraft = data.sort(
        (a, b) => new Date(b.id) - new Date(a.id)
      )[0];

      setRecoveryDraft(latestDraft);
      setShowDraftRecovery(true);
    }
  } catch (error) {
    console.error("Draft recovery error:", error);
  }
}
async function publishDraft(draftId) {
  const parsedUserId = Number(userId);

  if (!parsedUserId) {
    setDraftsError("Please login to publish a draft.");
    return;
  }

  try {
    const response = await apiFetch(
      `/api/questions/drafts/${draftId}/publish?userId=${parsedUserId}`,
      {
        method: "PUT",
      }
    );

    if (!response.ok) {
      throw new Error("Could not publish draft.");
    }

    await fetchDrafts();
    await fetchQuestions();

    setDraftsError("Draft published successfully!");
  } catch (err) {
    setDraftsError(err.message || "Unable to publish draft.");
  }
}
async function scheduleDraft(draftId, scheduledAt) {
  const parsedUserId = Number(userId);

  if (!parsedUserId) {
    setDraftsError("Please login to schedule a question.");
    return;
  }

  try {
    const response = await apiFetch(
      `/api/questions/drafts/${draftId}/schedule?userId=${parsedUserId}&scheduledAt=${encodeURIComponent(scheduledAt)}`,
      {
        method: "PUT",
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not schedule question.");
    }

    await fetchDrafts();

    setDraftsError("Question scheduled successfully!");
  } catch (err) {
    setDraftsError(err.message || "Unable to schedule question.");
  }
}
async function deleteDraft(draftId) {
  const parsedUserId = Number(userId);

  if (!parsedUserId) {
    setDraftsError("Please login to delete a draft.");
    return;
  }

  try {
    const response = await apiFetch(
      `/api/questions/drafts/${draftId}?userId=${parsedUserId}`,
      {
        method: "DELETE",
      }
    );

    if (!response.ok) {
      throw new Error("Could not delete draft.");
    }

    await fetchDrafts();
    setDraftsError("Draft deleted successfully!");
  } catch (err) {
    setDraftsError(err.message || "Unable to delete draft.");
  }
}
function editDraft(draft) {
  setEditingDraftId(draft.id);
  setNewTitle(draft.title || "");
  setNewDescription(draft.description || "");
  setNewCategory(draft.category || "Java");
  setNewTags(draft.tags || "");

  setShowDrafts(false);
  setShowForm(true);
  setFormMessage("");
}
  async function fetchQuestions() {
    setError("");

    try {
      const response = await apiFetch("/api/questions");

      if (!response.ok) {
        throw new Error(`Request failed: ${response.status}`);
      }

      const data = await response.json();

      if (!Array.isArray(data)) {
        throw new Error("The API did not return a list of questions.");
      }

      setItems(data);
            const params = new URLSearchParams(window.location.search);
      const sharedQuestionId = params.get("question");

      if (sharedQuestionId) {
        const sharedQuestion = data.find(
          (question) => String(question.id) === String(sharedQuestionId)
        );

if (sharedQuestion) {
  const questionId = Number(sharedQuestionId);

  setSearch(sharedQuestion.title || "");
  setCategory("All");
  setSortBy("newest");

  setExpandedQuestionId(questionId);


}
      }
    } catch (err) {
      setError(err.message || "Could not load questions.");
    } finally {
      setLoading(false);
    }
  }
  async function fetchAdminReportStats() {
  setAdminReportLoading(true);
  setAdminReportError("");

  try {
    const response = await apiFetch("/api/admin/reports/dashboard");

    if (!response.ok) {
      throw new Error("Could not load admin report statistics.");
    }

    const data = await response.json();
    setAdminReportStats(data);
  } catch (error) {
    setAdminReportError(error.message);
  } finally {
    setAdminReportLoading(false);
  }
}
async function fetchAdminReports() {
  setAdminReportsLoading(true);
  setAdminReportError("");

  try {
    const url =
      adminReportFilter === "ALL"
        ? "/api/admin/reports"
        : `/api/admin/reports/status?status=${adminReportFilter}`;

    const response = await apiFetch(url);

    if (!response.ok) {
      throw new Error("Could not load reports.");
    }

    const data = await response.json();
    setAdminReports(data);
  } catch (error) {
    setAdminReportError(error.message);
  } finally {
    setAdminReportsLoading(false);
  }
}
async function updateReportStatus(reportId, status) {
  try {
    const response = await apiFetch(
      `/api/admin/reports/${reportId}/status?status=${status}`,
      {
        method: "PUT",
      }
    );

    if (!response.ok) {
      throw new Error("Could not update report status.");
    }

    await fetchAdminReports();
  } catch (error) {
    setAdminReportError(error.message);
  }
}
async function deleteAdminReport(reportId) {
  const confirmDelete = window.confirm(
    "Are you sure you want to delete this report?"
  );

  if (!confirmDelete) {
    return;
  }

  try {
    const response = await apiFetch(
      `/api/admin/reports/${reportId}`,
      {
        method: "DELETE",
      }
    );

    if (!response.ok) {
      throw new Error("Could not delete report.");
    }

    await fetchAdminReports();
  } catch (error) {
    setAdminReportError(error.message);
  }
}
async function fetchAdminUsers() {
  setAdminUsersLoading(true);
  setAdminUsersError("");

  try {
    let url = "/api/admin/users";

    if (adminUserSearch.trim()) {
      url = `/api/admin/users/search?keyword=${encodeURIComponent(adminUserSearch.trim())}`;
    } else if (adminUserFilter === "ACTIVE") {
      url = "/api/admin/users/active";
    } else if (adminUserFilter === "INACTIVE") {
      url = "/api/admin/users/inactive";
    } else if (adminUserFilter === "ADMIN" || adminUserFilter === "USER") {
      url = `/api/admin/users/role?role=${adminUserFilter}`;
    }

    const response = await apiFetch(url);

    if (!response.ok) {
      throw new Error("Could not load users.");
    }

    const data = await response.json();
    setAdminUsers(data);
  } catch (error) {
    setAdminUsersError(error.message);
  } finally {
    setAdminUsersLoading(false);
  }
}
async function fetchAdminAnalytics() {
  setAdminAnalyticsLoading(true);
  setAdminAnalyticsError("");

  try {
    const [dashboardResponse, activityResponse, reputationResponse] =
      await Promise.all([
        apiFetch("/api/admin/dashboard"),
        apiFetch("/api/admin/users/activity-dashboard"),
        apiFetch("/api/admin/users/reputation-statistics"),
      ]);

    if (
      !dashboardResponse.ok ||
      !activityResponse.ok ||
      !reputationResponse.ok
    ) {
      throw new Error("Could not load analytics data.");
    }

    const dashboardData = await dashboardResponse.json();
    const activityData = await activityResponse.json();
    const reputationData = await reputationResponse.json();

    setAdminDashboardStats(dashboardData);
    setAdminActivityStats(activityData);
    setAdminReputationStats(reputationData);
  } catch (error) {
    setAdminAnalyticsError(error.message);
  } finally {
    setAdminAnalyticsLoading(false);
  }
}
async function fetchAdminActivityLogs() {
  setAdminActivityLogsLoading(true);
  setAdminActivityLogsError("");

  try {
    let endpoint;

    if (adminActivityStartDate || adminActivityEndDate) {
      if (!adminActivityStartDate || !adminActivityEndDate) {
        throw new Error("Please select both start and end dates.");
      }

      if (adminActivityStartDate > adminActivityEndDate) {
        throw new Error("Start date cannot be after end date.");
      }

      const start = `${adminActivityStartDate}T00:00:00`;
      const end = `${adminActivityEndDate}T23:59:59`;

      endpoint = `/api/admin/activity-logs/by-date?start=${encodeURIComponent(start)}&end=${encodeURIComponent(end)}`;
    } else if (adminActivitySearch.trim()) {
      endpoint = `/api/admin/activity-logs/by-admin?email=${encodeURIComponent(adminActivitySearch.trim())}`;
    } else if (adminActivityFilter === "ALL") {
      endpoint = "/api/admin/activity-logs";
    } else {
      endpoint = `/api/admin/activity-logs/by-action?action=${encodeURIComponent(adminActivityFilter)}`;
    }

    const response = await apiFetch(endpoint);

    if (!response.ok) {
      throw new Error("Could not load activity logs.");
    }

    const data = await response.json();

    setAdminActivityLogs(data);
    setAdminActivityTotalPages(Math.ceil(data.length / 10));
    setAdminActivityPage(0);
  } catch (error) {
    setAdminActivityLogsError(error.message);
  } finally {
    setAdminActivityLogsLoading(false);
  }
}
function exportAdminActivityLogs() {
  if (adminActivityLogs.length === 0) {
    alert("No activity logs available to export.");
    return;
  }

  const headers = [
    "ID",
    "Action",
    "Admin Email",
    "Target Type",
    "Target ID",
    "Details",
    "Created At",
  ];

  const rows = adminActivityLogs.map((log) => [
    log.id,
    log.action,
    log.adminEmail,
    log.targetType || "N/A",
    log.targetId ?? "N/A",
    log.details || "N/A",
    log.createdAt
      ? new Date(log.createdAt).toLocaleString()
      : "N/A",
  ]);

  const csvContent = [headers, ...rows]
    .map((row) =>
      row
        .map((value) =>
          `"${String(value ?? "").replace(/"/g, '""')}"`
        )
        .join(",")
    )
    .join("\n");

  const blob = new Blob([csvContent], {
    type: "text/csv;charset=utf-8;",
  });

  const url = URL.createObjectURL(blob);
  const link = document.createElement("a");

  link.href = url;
  link.download = "queryhub-admin-activity-logs.csv";
  link.click();

  URL.revokeObjectURL(url);
}
async function updateAdminUserStatus(userId, active) {
  try {
    const response = await apiFetch(
      `/api/admin/users/${userId}/active?active=${active}`,
      {
        method: "PUT",
      }
    );

    if (!response.ok) {
      throw new Error("Could not update user status.");
    }

    await fetchAdminUsers();
  } catch (error) {
    setAdminUsersError(error.message);
  }
}async function updateAdminUserRole(userId, role) {
  try {
    const response = await apiFetch(
      `/api/admin/users/${userId}/role?role=${role}`,
      {
        method: "PUT",
      }
    );

    if (!response.ok) {
      throw new Error("Could not update user role.");
    }

    await fetchAdminUsers();
  } catch (error) {
    setAdminUsersError(error.message);
  }
}
  async function submitReport() {
  const userId = localStorage.getItem("queryhubUserId");

  if (!userId) {
    setAuthMode("login");
    setAuthError("Please log in to report content.");
    setShowAuth(true);
    return;
  }

  if (!reportReason.trim()) {
    setReportError("Please select or enter a reason.");
    return;
  }

  setReportLoading(true);
  setReportError("");
  setReportSuccess("");

  try {
    const response = await apiFetch("/api/reports", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: Number(userId),
        targetType: reportTargetType,
        targetId: reportTargetId,
        reason: reportReason.trim(),
        status: "PENDING",
      }),
    });

    if (!response.ok) {
      throw new Error("Could not submit your report.");
    }

   const responseText = await response.text();

if (!responseText) {
  throw new Error("You have already submitted this report.");
}

const data = JSON.parse(responseText);

    setReportSuccess("Report submitted successfully!");
    setReportReason("");
    setShowReportForm(false);
  } catch (error) {
    setReportError(error.message || "Unable to submit report.");
  } finally {
    setReportLoading(false);
  }
}
    async function fetchTrendingQuestions() {
    try {
      const response = await apiFetch("/api/questions/trending");

      if (!response.ok) {
        throw new Error("Could not load trending questions.");
      }

      const data = await response.json();
      setTrendingQuestions(data);
    } catch (error) {
      console.error("Trending questions error:", error);
    }
  }

useEffect(() => {
  fetchQuestions();
  fetchBookmarks();
  fetchFollowedQuestions();
  fetchTrendingQuestions();
}, []);
useEffect(() => {
  const params = new URLSearchParams(window.location.search);
  const sharedQuestionId = params.get("question");
  const sharedAnswerId = params.get("answer");

  if (!sharedQuestionId || !sharedAnswerId) {
    return;
  }

  const questionId = Number(sharedQuestionId);

  const questionExists = items.some(
    (question) => Number(question.id) === questionId
  );

  if (!questionExists) {
    return;
  }

  if (
    Object.prototype.hasOwnProperty.call(
      answersByQuestion,
      questionId
    )
  ) {
    return;
  }

  fetchAnswers(questionId);
}, [items, answersByQuestion]);
useEffect(() => {
  if (loggedInUser && userId) {
    fetchAnswerBookmarks();
  } else {
    setAnswerBookmarks([]);
  }
}, [loggedInUser, userId]);
useEffect(() => {
  if (showDrafts) {
    document.getElementById("my-drafts")?.scrollIntoView({
      behavior: "smooth",
      block: "start",
    });
  }

  if (showScheduledQuestions) {
    document.getElementById("my-scheduled-questions")?.scrollIntoView({
      behavior: "smooth",
      block: "start",
    });
  }
  if (showClosedQuestions) {
  document.getElementById("my-closed-questions")?.scrollIntoView({
    behavior: "smooth",
    block: "start",
  });
}
}, [showDrafts, showScheduledQuestions, showClosedQuestions]);
useEffect(() => {
  questions.forEach((question) => {
    fetchFollowerCount(question.id);
  });
}, [questions]);
useEffect(() => {
 if (!showForm || !loggedInUser || submitting) {
  return;
}

  if (
    !newTitle.trim() ||
    !newDescription.trim() ||
    !newCategory.trim()
  ) {
    return;
  }

  const autoSaveTimer = setTimeout(() => {
    autoSaveDraft();
  }, 5000);

  return () => clearTimeout(autoSaveTimer);
}, [
  newTitle,
  newDescription,
  newCategory,
  newTags,
  showForm,
  loggedInUser,
  submitting,
]);
useEffect(() => {
  const params = new URLSearchParams(window.location.search);
  const sharedQuestionId = params.get("question");
  const sharedAnswerId = params.get("answer");
  if (!sharedQuestionId) {
    return;
  }

  const timer = setTimeout(() => {
    const questionElement = document.getElementById(
      `question-${sharedQuestionId}`
    );

    if (questionElement) {
      questionElement.scrollIntoView({
        behavior: "smooth",
        block: "center",
      });
    }
  }, 300);

  return () => clearTimeout(timer);
}, [questions, search, category]);
useEffect(() => {
  const params = new URLSearchParams(window.location.search);
  const sharedQuestionId = params.get("question");
  const sharedAnswerId = params.get("answer");

  if (!sharedQuestionId || !sharedAnswerId) {
    return;
  }

  const questionId = Number(sharedQuestionId);

  if (Number(expandedQuestionId) !== questionId) {
    return;
  }

  if (answerLoading[questionId]) {
    return;
  }

  const answers = answersByQuestion[questionId] || [];

  const visibleAnswers = [...answers]
    .filter((answer) =>
      (answer.content || "")
        .toLowerCase()
        .includes(
          (answerSearch[questionId] || "").trim().toLowerCase()
        )
    )
    .sort((a, b) => {
      if (a.pinned !== b.pinned) {
        return Number(b.pinned) - Number(a.pinned);
      }

      switch (answerSort) {
        case "UPVOTES":
          return (b.upvotes ?? 0) - (a.upvotes ?? 0);
        case "NEWEST":
          return Number(b.id) - Number(a.id);
        case "OLDEST":
          return Number(a.id) - Number(b.id);
        case "ACCEPTED":
        default:
          return Number(Boolean(b.accepted)) -
            Number(Boolean(a.accepted));
      }
    });

  const answerIndex = visibleAnswers.findIndex(
    (answer) => String(answer.id) === String(sharedAnswerId)
  );
  console.log("SHARED ANSWER DEBUG", {
  sharedQuestionId,
  sharedAnswerId,
  expandedQuestionId,
  answerLoading: answerLoading[questionId],
  totalAnswers: answers.length,
  answerIndex,
  targetPage: answerIndex >= 0
    ? Math.floor(answerIndex / answersPerPage)
    : "NOT FOUND",
  currentPage: answerPage[questionId] || 0,
});

  if (answerIndex === -1) {
    return;
  }

  const targetPage = Math.floor(answerIndex / answersPerPage);

  if ((answerPage[questionId] || 0) !== targetPage) {
    setAnswerPage((previous) => ({
      ...previous,
      [questionId]: targetPage,
    }));
    return;
  }

  const timer = setTimeout(() => {
    document
      .getElementById(`answer-${sharedAnswerId}`)
      ?.scrollIntoView({
        behavior: "smooth",
        block: "center",
      });
  }, 500);

  return () => clearTimeout(timer);
}, [
  answersByQuestion,
  expandedQuestionId,
  answerLoading,
  answerPage,
  answerSort,
  answerSearch,
]);
useEffect(() => {
  if (loggedInUser && userId) {
    checkDraftRecovery();
  }
}, [loggedInUser, userId]);

  useEffect(() => {
    const updateUnreadCount = async () => {
      const currentUserId = Number(
        localStorage.getItem("queryhubUserId")
      );

      if (!Number.isInteger(currentUserId) || currentUserId <= 0) {
        setUnreadCount(0);
        return;
      }

      try {
        const response = await apiFetch(
          `/api/notifications/user/${currentUserId}/unread-count`
        );

        if (!response.ok) {
          return;
        }

        const count = await response.json();
        setUnreadCount(Number(count) || 0);
      } catch (err) {
        console.error("Could not refresh unread notification count:", err);
      }
    };

    updateUnreadCount();

    const intervalId = setInterval(updateUnreadCount, 10000);

    return () => clearInterval(intervalId);
  }, []);

  function getTags(tags) {
    if (Array.isArray(tags)) {
      return tags;
    }

    if (typeof tags === "string") {
      return tags
        .split(",")
        .map((tag) => tag.trim())
        .filter(Boolean);
    }

    return [];
  }

 
  const filteredQuestions = items.filter((question) => {
  const title = question.title || "";
  const description = question.description || "";
  const tags = getTags(question.tags);
  const searchText = search.toLowerCase();

  const matchesSearch =
    title.toLowerCase().includes(searchText) ||
    description.toLowerCase().includes(searchText) ||
    tags.some((tag) => tag.toLowerCase().includes(searchText));

  const matchesCategory =
    category === "All" || question.category === category;

  return matchesSearch && matchesCategory;
});


const sortedQuestions = [...filteredQuestions].sort((a, b) => {
  if (sortBy === "views") {
    return (b.views || 0) - (a.views || 0);
  }

  if (sortBy === "upvotes") {
    return (b.upvotes || 0) - (a.upvotes || 0);
  }

  return (b.id || 0) - (a.id || 0);
});
const paginatedAdminActivityLogs = adminActivityLogs.slice(
  adminActivityPage * 10,
  adminActivityPage * 10 + 10
);
async function fetchFollowedQuestions() {
  const currentUserId = localStorage.getItem("queryhubUserId");

  if (!currentUserId) {
    setFollowedQuestions([]);
    return;
  }

  setFollowLoading(true);
  setFollowError("");

  try {
    const response = await apiFetch(
      `/api/question-follows/user/${currentUserId}`
    );

    if (!response.ok) {
      throw new Error("Failed to load followed questions.");
    }

    const data = await response.json();
    setFollowedQuestions(data);
  } catch (error) {
    setFollowError(error.message || "Unable to load followed questions.");
  } finally {
    setFollowLoading(false);
  }
}
async function fetchFollowerCount(questionId) {
  try {
    const response = await apiFetch(
      `/api/question-follows/count/${questionId}`
    );

    if (!response.ok) {
      throw new Error("Could not load follower count.");
    }

    const count = await response.json();

    setFollowerCounts((previous) => ({
      ...previous,
      [questionId]: count,
    }));
  } catch (error) {
    console.error("Follower count error:", error);
  }
}
async function toggleQuestionFollow(questionId) {
  const currentUserId = localStorage.getItem("queryhubUserId");

  if (!currentUserId) {
    setAuthMode("login");
    setAuthError("Please log in to follow a question.");
    setShowAuth(true);
    return;
  }

  const isFollowing = followedQuestions.some(
    (follow) =>
      String(follow.question?.id) === String(questionId)
  );

  setFollowLoading(true);
  setFollowError("");

  try {
    const response = await apiFetch(
      `/api/question-follows/${questionId}?userId=${currentUserId}`,
      {
        method: isFollowing ? "DELETE" : "POST",
      }
    );

    if (!response.ok) {
      throw new Error("Could not update question follow status.");
    }

    await fetchFollowedQuestions();
    await fetchFollowerCount(questionId);
  } catch (error) {
    setFollowError(error.message || "Unable to follow question.");
  } finally {
    setFollowLoading(false);
  }
}
  async function fetchLeaderboard() {
  setLeaderboardLoading(true);
  setLeaderboardError("");

  try {
    const response = await apiFetch("/api/leaderboard");

    if (!response.ok) {
      throw new Error("Could not load leaderboard.");
    }

    const data = await response.json();

    setLeaderboardUsers(data);
  } catch (err) {
    setLeaderboardError(
      err.message || "Something went wrong."
    );
  } finally {
    setLeaderboardLoading(false);
  }
}
async function increaseQuestionViews(questionId) {
  const userId = localStorage.getItem("queryhubUserId");

  if (!userId) {
    alert("Please log in to view this question.");
    return;
  }

  try {
    const response = await apiFetch(
      `/api/questions/${questionId}?userId=${userId}`
    );

    if (!response.ok) {
      throw new Error("Unable to update question views.");
    }

    const updatedQuestion = await response.json();

    setItems((previous) =>
      previous.map((question) =>
        String(question.id) === String(questionId)
          ? updatedQuestion
          : question
      )
    );
  } catch (error) {
    console.error("View count error:", error);
  }
}
  async function fetchNotifications() {
    const currentUserId = Number(
      localStorage.getItem("queryhubUserId")
    );

    if (!Number.isInteger(currentUserId) || currentUserId <= 0) {
      setNotifications([]);
      setUnreadCount(0);
      return;
    }

    setNotificationLoading(true);
    setNotificationError("");

    try {
      const [notificationsResponse, countResponse] = await Promise.all([
        apiFetch(`/api/notifications/user/${currentUserId}`),
        apiFetch(`/api/notifications/user/${currentUserId}/unread-count`),
      ]);

      if (!notificationsResponse.ok) {
        throw new Error(
          `Could not load notifications (${notificationsResponse.status}).`
        );
      }

      if (!countResponse.ok) {
        throw new Error(
          `Could not load unread count (${countResponse.status}).`
        );
      }

      const notificationData = await notificationsResponse.json();
      const countData = await countResponse.json();

      if (!Array.isArray(notificationData)) {
        throw new Error("The notifications API did not return a list.");
      }

      setNotifications(notificationData);
      setUnreadCount(Number(countData) || 0);
    } catch (err) {
      setNotificationError(
        err.message || "Could not load notifications."
      );
    } finally {
      setNotificationLoading(false);
    }
  }
  async function fetchBookmarks() {
 const userId = localStorage.getItem("queryhubUserId");

  if (!userId) {
    setBookmarks([]);
    return;
  }

  setBookmarkLoading(true);
  setBookmarkError("");

  try {
    const response = await apiFetch(`/api/bookmarks/user/${userId}`);
    console.log("Bookmark fetch status:", response.status, "User ID:", userId);

    if (!response.ok) {
      throw new Error("Failed to load bookmarks");
    }

   const data = await response.json();
   console.log("Fetched bookmark data:", data);
setBookmarks(data);
return data;
 } catch (error) {
  console.error("Bookmark loading error:", error);
  setBookmarkError(error.message || "Unable to load bookmarks.");
  return [];
}finally {
    setBookmarkLoading(false);
  }
}

  async function deleteNotification(notificationId) {
    const currentUserId = Number(
      localStorage.getItem("queryhubUserId")
    );

    if (!Number.isInteger(currentUserId) || currentUserId <= 0) {
      setNotificationError("Please enter your existing user ID first.");
      return;
    }

    try {
      const response = await apiFetch(
        `/api/notifications/${notificationId}?userId=${currentUserId}`,
        { method: "DELETE" }
      );

      if (!response.ok) {
        throw new Error(
          `Could not delete notification (${response.status}).`
        );
      }

      await fetchNotifications();
      setNotificationError("");
    } catch (err) {
      setNotificationError(
        err.message || "Could not delete notification."
      );
    }
  }

  async function markNotificationAsRead(notificationId) {
    const currentUserId = Number(
      localStorage.getItem("queryhubUserId")
    );

    if (!Number.isInteger(currentUserId) || currentUserId <= 0) {
      setNotificationError("Please enter your existing user ID first.");
      return;
    }

    try {
      const response = await apiFetch(
        `/api/notifications/${notificationId}/read?userId=${currentUserId}`,
        { method: "PUT" }
      );

      if (!response.ok) {
        throw new Error(
          `Could not mark notification as read (${response.status}).`
        );
      }

      await fetchNotifications();
    } catch (err) {
      setNotificationError(
        err.message || "Could not mark notification as read."
      );
    }
  }

  async function deleteAllNotifications() {
    const currentUserId = Number(
      localStorage.getItem("queryhubUserId")
    );

    if (!Number.isInteger(currentUserId) || currentUserId <= 0) {
      setNotificationError("Please enter your existing user ID first.");
      return;
    }

    const confirmed = window.confirm(
      "Are you sure you want to delete all your notifications?"
    );

    if (!confirmed) {
      return;
    }

    try {
      const response = await apiFetch(
        `/api/notifications/user/${currentUserId}/all`,
        { method: "DELETE" }
      );

      if (!response.ok) {
        throw new Error(
          `Could not delete all notifications (${response.status}).`
        );
      }

      await fetchNotifications();
      setNotificationError("");
    } catch (err) {
      setNotificationError(
        err.message || "Could not delete all notifications."
      );
    }
  }

  async function markAllNotificationsAsRead() {
    const currentUserId = Number(
      localStorage.getItem("queryhubUserId")
    );

    if (!Number.isInteger(currentUserId) || currentUserId <= 0) {
      setNotificationError("Please enter your existing user ID first.");
      return;
    }

    try {
      const response = await apiFetch(
        `/api/notifications/user/${currentUserId}/read-all`,
        { method: "PUT" }
      );

      if (!response.ok) {
        throw new Error(
          `Could not mark all notifications as read (${response.status}).`
        );
      }

      await fetchNotifications();
    } catch (err) {
      setNotificationError(
        err.message || "Could not mark all notifications as read."
      );
    }
  }

  async function openProfile() {
    const savedUserId = localStorage.getItem("queryhubUserId");
    const currentUserId = Number(savedUserId);

    setShowProfile(true);
    setProfile(null);
    setProfileError("");
    setActivityError("");
    fetchBadges();

    if (!Number.isInteger(currentUserId) || currentUserId <= 0) {
      setProfileError(
        "Please enter your existing user ID in the Ask Question form first."
      );
      return;
    }

    setProfileLoading(true);
    setActivityLoading(true);

    try {
      const [profileResponse, questionsResponse, answersResponse] =
        await Promise.all([
          apiFetch(`/api/users/${currentUserId}`),
          apiFetch(`/api/questions/user/${currentUserId}`),
          apiFetch(`/api/answers/user/${currentUserId}`),
        ]);

      if (!profileResponse.ok) {
        throw new Error(
          `Could not load profile (${profileResponse.status}).`
        );
      }

      const profileData = await profileResponse.json();
      setProfile(profileData);

      if (!questionsResponse.ok) {
        throw new Error(
          `Could not load your questions (${questionsResponse.status}).`
        );
      }

      if (!answersResponse.ok) {
        throw new Error(
          `Could not load your answers (${answersResponse.status}).`
        );
      }

      const questionsData = await questionsResponse.json();
      const answersData = await answersResponse.json();

      if (!Array.isArray(questionsData) || !Array.isArray(answersData)) {
        throw new Error("The questions or answers API did not return a list.");
      }

      setMyQuestions(questionsData);
      setMyAnswers(answersData);
    } catch (err) {
      setProfileError(err.message || "Could not load your profile.");
      setActivityError(err.message || "Could not load your activity.");
    } finally {
      setProfileLoading(false);
      setActivityLoading(false);
    }
  }
  async function fetchBadges() {
 const userId = localStorage.getItem("queryhubUserId");

  if (!userId) {
    setBadges([]);
    return;
  }

  setBadgeLoading(true);
  setBadgeError("");

  try {
    const response = await apiFetch(`/api/badges/user/${userId}`);

    if (!response.ok) {
      throw new Error("Failed to load badges");
    }

    const data = await response.json();
    setBadges(data);
  } catch (error) {
    setBadgeError("Unable to load badges.");
  } finally {
    setBadgeLoading(false);
  }
}
async function toggleBookmark(questionId) {
 const userId = localStorage.getItem("queryhubUserId");

  if (!userId) {
    alert("Please log in to bookmark a question.");
    return;
  }

const latestBookmarks = await fetchBookmarks();
if (!Array.isArray(latestBookmarks)) {
  console.error("Bookmarks could not be loaded.");
  return;
}

const isBookmarked = latestBookmarks.some(
  (bookmark) =>
    String(bookmark.question?.id) === String(questionId)
);

  try {
    const url = `/api/bookmarks?userId=${userId}&questionId=${questionId}`;

    const response = await apiFetch(url, {
      method: isBookmarked ? "DELETE" : "POST",
    });

    if (response.status === 409) {
      await fetchBookmarks();
      return;
    }

    if (!response.ok) {
      throw new Error("Unable to update bookmark");
    }

    await fetchBookmarks();
  } catch (error) {
    alert(error.message);
  }
}
  async function fetchAnswers(questionId) {
    setAnswerLoading((previous) => ({
      ...previous,
      [questionId]: true,
    }));

    setAnswerErrors((previous) => ({
      ...previous,
      [questionId]: "",
    }));

    try {
      const response = await apiFetch(
        `/api/answers/question/${questionId}`
      );

      if (!response.ok) {
        throw new Error(`Could not load answers (${response.status}).`);
      }

      const data = await response.json();

      if (!Array.isArray(data)) {
        throw new Error("The answers API did not return a list.");
      }

      setAnswersByQuestion((previous) => ({
        ...previous,
        [questionId]: data,
      }));
      console.log("ANSWERS FETCHED", questionId, data);
    } catch (err) {
      setAnswerErrors((previous) => ({
        ...previous,
        [questionId]: err.message || "Could not load answers.",
      }));
    } finally {
      setAnswerLoading((previous) => ({
        ...previous,
        [questionId]: false,
      }));
    }
  }
  async function fetchAnswerComments(answerId) {
  setCommentLoading((previous) => ({
    ...previous,
    [answerId]: true,
  }));

  try {
    const response = await apiFetch(
      `/api/comments/answer/${answerId}`
    );

    if (!response.ok) {
      throw new Error("Could not load comments.");
    }

    const data = await response.json();

    setAnswerComments((previous) => ({
      ...previous,
      [answerId]: data,
    }));

  } catch (error) {
    setCommentErrors((previous) => ({
      ...previous,
      [answerId]: error.message,
    }));
  } finally {
    setCommentLoading((previous) => ({
      ...previous,
      [answerId]: false,
    }));
  }
}
async function fetchQuestionComments(questionId) {
  setQuestionCommentLoading((previous) => ({
    ...previous,
    [questionId]: true,
  }));

  try {
    const response = await apiFetch(
      `/api/comments/question/${questionId}`
    );

    if (!response.ok) {
      throw new Error("Could not load question comments.");
    }

    const data = await response.json();

    setQuestionComments((previous) => ({
      ...previous,
      [questionId]: data,
    }));

  } catch (error) {
    setQuestionCommentErrors((previous) => ({
      ...previous,
      [questionId]: error.message,
    }));
  } finally {
    setQuestionCommentLoading((previous) => ({
      ...previous,
      [questionId]: false,
    }));
  }
}
async function addQuestionComment(questionId) {
  if (!loggedInUser) {
    setQuestionCommentErrors((previous) => ({
      ...previous,
      [questionId]: "Please log in to comment.",
    }));
    return;
  }

  const content = (
    questionCommentDrafts[questionId] || ""
  ).trim();

  if (!content) {
    setQuestionCommentErrors((previous) => ({
      ...previous,
      [questionId]: "Comment cannot be empty.",
    }));
    return;
  }

  try {
    const params = new URLSearchParams({
      userId: String(loggedInUser.id),
      content: content,
      questionId: String(questionId),
    });

    const response = await apiFetch(
      `/api/comments?${params.toString()}`,
      {
        method: "POST",
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not post comment.");
    }

    setQuestionCommentDrafts((previous) => ({
      ...previous,
      [questionId]: "",
    }));

    setQuestionCommentErrors((previous) => ({
      ...previous,
      [questionId]: "",
    }));

    await fetchQuestionComments(questionId);

  } catch (error) {
    setQuestionCommentErrors((previous) => ({
      ...previous,
      [questionId]: error.message || "Could not post comment.",
    }));
  }
}
async function updateQuestionComment(questionId, commentId) {
  if (!loggedInUser) {
    setQuestionCommentErrors((previous) => ({
      ...previous,
      [questionId]: "Please log in to edit a comment.",
    }));
    return;
  }

if (!editedQuestionCommentContent.trim()) {
  setQuestionCommentErrors((previous) => ({
    ...previous,
    [questionId]: "Comment cannot be empty.",
  }));
  return;
}

setQuestionCommentErrors((previous) => ({
  ...previous,
  [questionId]: "",
}));

  try {
    const params = new URLSearchParams({
      userId: String(loggedInUser.id),
      content: editedQuestionCommentContent.trim(),
    });

    const response = await apiFetch(
      `/api/comments/${commentId}?${params.toString()}`,
      {
        method: "PUT",
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not update comment.");
    }

    setEditingQuestionCommentId(null);
    setEditedQuestionCommentContent("");

    await fetchQuestionComments(questionId);

  } catch (error) {
    setQuestionCommentErrors((previous) => ({
      ...previous,
      [questionId]: error.message || "Could not update comment.",
    }));
  }
}
async function deleteQuestionComment(questionId, commentId) {
  if (!loggedInUser) {
    setQuestionCommentErrors((previous) => ({
      ...previous,
      [questionId]: "Please log in to delete a comment.",
    }));
    return;
  }

  const confirmed = window.confirm(
    "Are you sure you want to delete this comment?"
  );

  if (!confirmed) {
    return;
  }

  try {
    const response = await apiFetch(
      `/api/comments/${commentId}?userId=${loggedInUser.id}`,
      {
        method: "DELETE",
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not delete comment.");
    }

    await fetchQuestionComments(questionId);

  } catch (error) {
    setQuestionCommentErrors((previous) => ({
      ...previous,
      [questionId]: error.message || "Could not delete comment.",
    }));
  }
}
async function addComment(answerId) {
  if (!loggedInUser) {
    setCommentErrors((previous) => ({
      ...previous,
      [answerId]: "Please log in to comment.",
    }));
    return;
  }

  const content = (commentDrafts[answerId] || "").trim();

  if (!content) {
    setCommentErrors((previous) => ({
      ...previous,
      [answerId]: "Comment cannot be empty.",
    }));
    return;
  }

  try {
    const params = new URLSearchParams({
      userId: String(loggedInUser.id),
      content: content,
      answerId: String(answerId),
    });

    const response = await apiFetch(
      `/api/comments?${params.toString()}`,
      {
        method: "POST",
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not post comment.");
    }

    setCommentDrafts((previous) => ({
      ...previous,
      [answerId]: "",
    }));

    setCommentErrors((previous) => ({
      ...previous,
      [answerId]: "",
    }));

    await fetchAnswerComments(answerId);

  } catch (error) {
    setCommentErrors((previous) => ({
      ...previous,
      [answerId]: error.message || "Could not post comment.",
    }));
  }
}
async function updateAnswerComment(answerId, commentId) {
  if (!loggedInUser) {
    setCommentErrors((previous) => ({
      ...previous,
      [answerId]: "Please log in to edit a comment.",
    }));
    return;
  }

  if (!editedCommentContent.trim()) {
    setCommentErrors((previous) => ({
      ...previous,
      [answerId]: "Comment cannot be empty.",
    }));
    return;
  }

  try {
    const params = new URLSearchParams({
      userId: String(loggedInUser.id),
      content: editedCommentContent.trim(),
    });

    const response = await apiFetch(
      `/api/comments/${commentId}?${params.toString()}`,
      {
        method: "PUT",
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not update comment.");
    }

    setEditingCommentId(null);
    setEditedCommentContent("");

    await fetchAnswerComments(answerId);

  } catch (error) {
    setCommentErrors((previous) => ({
      ...previous,
      [answerId]: error.message || "Could not update comment.",
    }));
  }
}
async function deleteAnswerComment(answerId, commentId) {
  if (!loggedInUser) {
    setCommentErrors((previous) => ({
      ...previous,
      [answerId]: "Please log in to delete a comment.",
    }));
    return;
  }

  const confirmed = window.confirm(
    "Are you sure you want to delete this comment?"
  );

  if (!confirmed) {
    return;
  }

  try {
    const response = await apiFetch(
      `/api/comments/${commentId}?userId=${loggedInUser.id}`,
      {
        method: "DELETE",
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not delete comment.");
    }

    await fetchAnswerComments(answerId);

  } catch (error) {
    setCommentErrors((previous) => ({
      ...previous,
      [answerId]: error.message || "Could not delete comment.",
    }));
  }
}

  async function toggleAnswers(questionId) {
    if (expandedQuestionId === questionId) {
      setExpandedQuestionId(null);
      return;
    }

    setExpandedQuestionId(questionId);

    if (
      !Object.prototype.hasOwnProperty.call(
        answersByQuestion,
        questionId
      )
    ) {
      await fetchAnswers(questionId);
    }
  }

async function addAnswer(event, questionId) {
    event.preventDefault();

    if (!loggedInUser) {
      setAnswerErrors((previous) => ({
        ...previous,
        [questionId]: "Please log in to post an answer.",
      }));
      return;
    }

    const content = (answerDrafts[questionId] || "").trim();
    const currentUserId = Number(loggedInUser.id);

    if (!Number.isInteger(currentUserId) || currentUserId <= 0) {
      setAnswerErrors((previous) => ({
        ...previous,
        [questionId]:
          "Please enter your existing user ID in the Ask Question form first.",
      }));
      return;
    }

    if (!content) {
      setAnswerErrors((previous) => ({
        ...previous,
        [questionId]: "Please enter your answer.",
      }));
      return;
    }

    setAnswerSubmitting((previous) => ({
      ...previous,
      [questionId]: true,
    }));

    setAnswerErrors((previous) => ({
      ...previous,
      [questionId]: "",
    }));

    try {
      const params = new URLSearchParams({
        userId: String(currentUserId),
        questionId: String(questionId),
        content,
      });

      const response = await apiFetch(`/api/answers?${params.toString()}`, {
        method: "POST",
      });

      if (!response.ok) {
        const message = await response.text();
        throw new Error(
          message || `Could not post answer (${response.status}).`
        );
      }

      const savedAnswer = await response.json();

      setAnswersByQuestion((previous) => ({
        ...previous,
        [questionId]: [
          savedAnswer,
          ...(previous[questionId] || []),
        ],
      }));

      setAnswerDrafts((previous) => ({
        ...previous,
        [questionId]: "",
      }));
    } catch (err) {
      setAnswerErrors((previous) => ({
        ...previous,
        [questionId]: err.message || "Could not post your answer.",
      }));
    } finally {
      setAnswerSubmitting((previous) => ({
        ...previous,
        [questionId]: false,
      }));
    }
  }

  async function voteAnswer(questionId, answerId, voteType) {
    if (!loggedInUser) {
  setAnswerErrors((previous) => ({
    ...previous,
    [questionId]: "Please log in to vote.",
  }));
  return;
}

const currentUserId = Number(loggedInUser.id);

    if (!Number.isInteger(currentUserId) || currentUserId <= 0) {
      setAnswerErrors((previous) => ({
        ...previous,
        [questionId]: "Please enter your existing user ID first.",
      }));
      return;
    }
        setAnswerVoting((previous) => ({
      ...previous,
      [answerId]: true,
    }));

    setAnswerErrors((previous) => ({
      ...previous,
      [questionId]: "",
    }));

    try {
      const endpoint = voteType === "UP" ? "upvote" : "downvote";

      const response = await apiFetch(
        `/api/answers/${answerId}/${endpoint}?userId=${currentUserId}`,
        { method: "PUT" }
      );

      const message = await response.text();

      if (!response.ok) {
        throw new Error(message || `Voting failed (${response.status}).`);
      }

      if (
        message !== "Answer upvoted successfully" &&
        message !== "Answer downvoted successfully"
      ) {
        throw new Error(message || "Could not record your vote.");
      }

      await fetchAnswers(questionId);
    } catch (err) {
      setAnswerErrors((previous) => ({
        ...previous,
        [questionId]: err.message || "Could not vote on this answer.",
      }));
    } finally {
      setAnswerVoting((previous) => ({
        ...previous,
        [answerId]: false,
      }));
    }
  }

  async function acceptAnswer(questionId, answerId) {
    const currentUserId = Number(
      localStorage.getItem("queryhubUserId")
    );

    if (!Number.isInteger(currentUserId) || currentUserId <= 0) {
      setAnswerErrors((previous) => ({
        ...previous,
        [questionId]: "Please enter your existing user ID first.",
      }));
      return;
    }

    setAnswerErrors((previous) => ({
      ...previous,
      [questionId]: "",
    }));

    try {
      const response = await apiFetch(
        `/api/answers/${answerId}/accept?userId=${currentUserId}`,
        { method: "PUT" }
      );

      if (!response.ok) {
        const message = await response.text();
        throw new Error(
          message || `Could not accept answer (${response.status}).`
        );
      }

      await fetchAnswers(questionId);
      await fetchQuestions();
    } catch (err) {
      setAnswerErrors((previous) => ({
        ...previous,
        [questionId]: err.message || "Could not accept answer.",
      }));
    }
  }
  async function deleteAnswer(questionId, answerId) {
  if (!loggedInUser) {
    setAnswerErrors((previous) => ({
      ...previous,
      [questionId]: "Please log in to delete an answer.",
    }));
    return;
  }

  const confirmed = window.confirm(
    "Are you sure you want to delete your answer?"
  );

  if (!confirmed) {
    return;
  }

  try {
    const response = await apiFetch(
      `/api/answers/${answerId}?userId=${loggedInUser.id}`,
      { method: "DELETE" }
    );

    const message = await response.text();

    if (!response.ok) {
      throw new Error(message || "Could not delete answer.");
    }

    await fetchAnswers(questionId);
    await fetchQuestions();

  } catch (err) {
    setAnswerErrors((previous) => ({
      ...previous,
      [questionId]: err.message || "Could not delete answer.",
    }));
  }
}
async function togglePinAnswer(answerId) {
  const parsedUserId = Number(userId);

  if (!parsedUserId) {
    alert("Please login first.");
    return;
  }

  try {
    const response = await apiFetch(
      `/api/answers/${answerId}/pin?userId=${parsedUserId}`,
      {
        method: "PUT",
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not update answer pin.");
    }

    window.location.reload();
  } catch (error) {
    alert(error.message);
  }
}
async function updateAnswer(questionId, answerId) {
  if (!loggedInUser) {
    setAnswerErrors((previous) => ({
      ...previous,
      [questionId]: "Please log in to edit an answer.",
    }));
    return;
  }

  if (!editedAnswerContent.trim()) {
    setAnswerErrors((previous) => ({
      ...previous,
      [questionId]: "Answer cannot be empty.",
    }));
    return;
  }

  try {
    const response = await apiFetch(
      `/api/answers/${answerId}?userId=${loggedInUser.id}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          content: editedAnswerContent.trim(),
        }),
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not update answer.");
    }

    setEditingAnswerId(null);
    setEditedAnswerContent("");
    await fetchAnswers(questionId);
  } catch (err) {
    setAnswerErrors((previous) => ({
      ...previous,
      [questionId]: err.message || "Could not update answer.",
    }));
  }
}

async function fetchAnswerEditHistory(answerId) {
  setAnswerEditHistoryLoading(true);
  setAnswerEditHistoryError("");
  setSelectedHistoryAnswer(answerId);
  setShowAnswerEditHistory(true);

try {
  const answerResponse = await apiFetch(`/api/answers/${answerId}`);

  if (!answerResponse.ok) {
    throw new Error("Could not load current answer.");
  }

  const answerData = await answerResponse.json();
  setCurrentAnswerContent(answerData.content || "");
} catch (error) {
  setAnswerEditHistoryError(error.message);
}

  try {
    const response = await apiFetch(
      `/api/answers/edit-history/${answerId}`
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(`Status: ${response.status} - ${message}`);
    }

    const data = await response.json();
    setAnswerEditHistory(data);
  } catch (error) {
    setAnswerEditHistoryError(error.message);
  } finally {
    setAnswerEditHistoryLoading(false);
  }
}
async function restoreAnswerVersion(answerId, historyId) {
  const parsedUserId = Number(userId);

  if (!parsedUserId) {
    alert("Please login to restore an answer.");
    return;
  }

  const confirmed = window.confirm(
    "Are you sure you want to restore this previous answer version?"
  );

  if (!confirmed) {
    return;
  }

  try {
    const response = await apiFetch(
      `/api/answers/${answerId}/restore/${historyId}?userId=${parsedUserId}`,
      {
        method: "PUT",
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not restore answer.");
    }

    alert("Previous answer version restored successfully!");
    window.location.reload();

  } catch (error) {
    alert(error.message);
  }
}
function compareAnswerText(previousText, currentText) {
  const previousWords = (previousText || "").split(/\s+/);
  const currentWords = (currentText || "").split(/\s+/);

  const previousSet = new Set(currentWords);
  const currentSet = new Set(previousWords);

  return {
    previous: previousWords.map((word) => ({
      word,
      changed: !previousSet.has(word),
    })),
    current: currentWords.map((word) => ({
      word,
      changed: !currentSet.has(word),
    })),
  };
}
async function updateQuestion(questionId, tags) {
  if (!loggedInUser) {
    window.alert("Please log in to edit a question.");
    return;
  }

  if (
    !editedQuestionTitle.trim() ||
    !editedQuestionDescription.trim() ||
    !editedQuestionCategory.trim()
  ) {
    window.alert("Please fill in all question details.");
    return;
  }

  try {
    const response = await apiFetch(
      `/api/questions/${questionId}?userId=${loggedInUser.id}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title: editedQuestionTitle.trim(),
          description: editedQuestionDescription.trim(),
          category: editedQuestionCategory.trim(),
          tags: tags,
        }),
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not update question.");
    }

    setEditingQuestionId(null);
    setEditedQuestionTitle("");
    setEditedQuestionDescription("");
    setEditedQuestionCategory("");

    await fetchQuestions();

    window.alert("Question updated successfully!");

  } catch (error) {
    window.alert(error.message || "Could not update question.");
  }
}
async function deleteQuestion(questionId) {
  if (!loggedInUser) {
    window.alert("Please log in to delete a question.");
    return;
  }

  const confirmed = window.confirm(
    "Are you sure you want to delete this question?"
  );

  if (!confirmed) {
    return;
  }

  try {
    const response = await apiFetch(
      `/api/questions/${questionId}?userId=${loggedInUser.id}`,
      {
        method: "DELETE",
      }
    );

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || "Could not delete question.");
    }

    await fetchQuestions();

    window.alert("Question deleted successfully!");

  } catch (error) {
    window.alert(error.message || "Could not delete question.");
  }
}
async function fetchQuestionEditHistory(questionId) {
 
  setEditHistoryLoading(true);
  setEditHistory([]);

  try {
    const response = await apiFetch(
      `/api/questions/${questionId}/edit-history`
    );

    if (!response.ok) {
      throw new Error("Could not fetch question edit history.");
    }

    const data = await response.json();

    setEditHistory(data);
    setShowEditHistory(true);
  } catch (error) {
    window.alert(error.message);
  } finally {
    setEditHistoryLoading(false);
  }
}

  async function addQuestion(event) {
    event.preventDefault();
    setFormMessage("");

    if (!userId.trim()) {
      setFormMessage("Enter your existing user ID first.");
      return;
    }

    const parsedUserId = Number(userId);

    if (!Number.isInteger(parsedUserId) || parsedUserId <= 0) {
      setFormMessage("Please enter a valid positive user ID.");
      return;
    }

    if (!newTitle.trim() || !newDescription.trim()) {
      setFormMessage("Please enter a title and description.");
      return;
    }

    setSubmitting(true);

    try {
      const questionData = {
  title: newTitle.trim(),
  description: newDescription.trim(),
  category: newCategory,
  tags: newTags.trim(),
  expiresAt: expiryDate ? expiryDate : null,
};

      const response = await apiFetch(
        `/api/questions?userId=${parsedUserId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(questionData),
        }
      );

      if (!response.ok) {
        const responseText = await response.text();
        throw new Error(
          responseText || `Could not post question (${response.status}).`
        );
      }
try {
  const draftsResponse = await apiFetch(
    `/api/questions/drafts/${parsedUserId}`
  );

  if (draftsResponse.ok) {
    const savedDrafts = await draftsResponse.json();
  

    const matchingDraft = savedDrafts.find(
      (draft) =>
        draft.title === questionData.title &&
        draft.description === questionData.description
    );

    const draftIdToDelete = editingDraftId || matchingDraft?.id;

    if (draftIdToDelete) {
      await apiFetch(
        `/api/questions/drafts/${draftIdToDelete}?userId=${parsedUserId}`,
        {
          method: "DELETE",
        }
      );
    }

    await fetchDrafts();
  }
} catch (draftError) {
  console.error("Could not clean up the posted draft:", draftError);
}
      setNewTitle("");
      setNewDescription("");
      setNewCategory("Java");
      setNewTags("");
      setExpiryDate("");
      setFormMessage("");
      setShowForm(false);
      setEditingDraftId(null);
      setCategory("All");
      setSearch("");

      localStorage.setItem("queryhubUserId", String(parsedUserId));
      setUserId(String(parsedUserId));

      setLoading(true);
      await fetchQuestions();
    } catch (err) {
      setFormMessage(
        err.message || "Something went wrong while posting your question."
      );
    } finally {
      setSubmitting(false);
    }
  }

  const filteredNotifications = notifications
    .filter((notification) => {
      const message = String(notification.message || "").toLowerCase();
      const matchesSearch = message.includes(
        notificationSearch.trim().toLowerCase()
      );
      const matchesStatus =
        notificationFilter === "all" ||
        (notificationFilter === "read" && Boolean(notification.read)) ||
        (notificationFilter === "unread" && !notification.read);

      return (
        matchesSearch &&
        matchesStatus &&
        (!hideReadNotifications || !notification.read)
      );
    })
    .sort((a, b) => {
      const dateA = new Date(a.createdAt || 0).getTime();
      const dateB = new Date(b.createdAt || 0).getTime();

      return notificationSort === "oldest"
        ? dateA - dateB
        : dateB - dateA;
    });

  const notificationsPerPage = 5;

  const totalNotificationPages = Math.max(
    1,
    Math.ceil(filteredNotifications.length / notificationsPerPage)
  );

  const paginatedNotifications = filteredNotifications.slice(
    (notificationPage - 1) * notificationsPerPage,
    notificationPage * notificationsPerPage
  );

  useEffect(() => {
    setNotificationPage((currentPage) =>
      Math.min(currentPage, totalNotificationPages)
    );
  }, [totalNotificationPages]);

  return (
    <div className="app">
      <header className="navbar">
        <a className="brand" href="#home">
          
          <span>QueryHub</span>
        </a>

        <nav className="nav-links">
          <a className="active-link" href="#home">
            Home
          </a>
          <a href="#questions">Questions</a>
          <a href="#categories">Categories</a>
          <button
  type="button"
  className="profile-nav-link"
  onClick={() => {
    fetchBookmarks();
    document.getElementById("my-bookmarks")?.scrollIntoView({
      behavior: "smooth",
    });
  }}
>
  🔖 My Bookmarks
</button>
<button
  onClick={() => {
    setShowClosedQuestions(true);
    fetchClosedQuestions();
  }}
>
  My Closed Questions
</button>
<button
  type="button"
 onClick={() => {
  setShowSavedAnswers(true);
  fetchAnswerBookmarks();

  setTimeout(() => {
    document
      .getElementById("saved-answers-section")
      ?.scrollIntoView({ behavior: "smooth" });
  }, 300);
}}
>
  My Saved Answers
</button>
<button
  type="button"
  className="nav-button"
  onClick={() => {
    setShowDrafts(true);
    fetchDrafts();
  }}
>
  📝 My Drafts ({drafts.length})
</button>
<button
  type="button"
  className="nav-button"
  onClick={() => {
    setShowScheduledQuestions(true);
    fetchScheduledQuestions();
  }}
>
  📅 My Scheduled Questions ({scheduledQuestions.length})
</button>
{loggedInUser?.role === "ADMIN" && (
  <button
    type="button"
    className="profile-nav-link"
    onClick={async () => {
      await Promise.all([
        fetchAdminReportStats(),
        fetchAdminAnalytics(),
        fetchAdminUsers(),
        fetchAdminReports(),
        fetchAdminActivityLogs(),
      ]);

      document.getElementById("admin-dashboard")?.scrollIntoView({
        behavior: "smooth",
      });
    }}
  >
    🛡️ Admin Dashboard
  </button>
)}
<button
  type="button"
  className="profile-nav-link"
  onClick={() => {
    fetchFollowedQuestions();
    document.getElementById("my-followed-questions")?.scrollIntoView({
      behavior: "smooth",
    });
  }}
>
  👀 Following
</button>
          <button
  type="button"
  className="profile-nav-link"
  onClick={() => {
    setShowLeaderboard(true);
    fetchLeaderboard();
  }}
>
  🏆 Leaderboard
</button>

          <button
            type="button"
            className="profile-nav-link"
            onClick={openProfile}
          >
            My Profile
          </button>
        </nav>

        <div className="navbar-actions">
                    {loggedInUser ? (
            <>
              <span className="profile-nav-link">
                Hi, {loggedInUser.name}
              </span>

              <button
                type="button"
                className="outline-button"
                onClick={() => {
                  localStorage.removeItem("queryhubUserId");
                  localStorage.removeItem("queryhubUser");
                  setUserId("");
                  setLoggedInUser(null);
                  setNotifications([]);
                  setUnreadCount(0);
                }}
              >
                Logout
              </button>
            </>
          ) : (
            <>
              <button
                type="button"
                className="outline-button"
                onClick={() => {
                  setAuthMode("login");
                  setAuthError("");
                  setShowAuth(true);
                }}
              >
                Login
              </button>

              <button
                type="button"
                className="nav-button"
                onClick={() => {
                  setAuthMode("register");
                  setAuthError("");
                  setShowAuth(true);
                }}
              >
                Register
              </button>
            </>
          )}
          <button
            type="button"
            className="notification-button"
            aria-label={`Notifications, ${unreadCount} unread`}
            onClick={() => {
              const nextState = !showNotifications;
              setShowNotifications(nextState);

              if (nextState) {
                fetchNotifications();
              }
            }}
          >
            🔔
            {unreadCount > 0 && (
              <span className="notification-badge">
                {unreadCount > 99 ? "99+" : unreadCount}
              </span>
            )}
          </button>

          <button
            type="button"
            className="nav-button"
            onClick={() => setShowForm(true)}
          >
            + Ask Question
          </button>
        </div>
      </header>

      {showNotifications && (
        <section className="notification-panel">
          <div className="notification-panel-heading">
            <div>
              <h3>Notifications</h3>
              <p>{unreadCount} unread</p>
            </div>

            <button
              type="button"
              className="close-button"
              onClick={() => setShowNotifications(false)}
              aria-label="Close notifications"
            >
              ×
            </button>
          </div>

          {notificationError && (
            <p role="alert" className="form-message">
              {notificationError}
            </p>
          )}

          <div className="notification-controls">
            <input
              type="search"
              className="notification-search"
              placeholder="Search notifications..."
              value={notificationSearch}
              onChange={(event) => {
                setNotificationSearch(event.target.value);
                setNotificationPage(1);
              }}
              aria-label="Search notifications"
            />

            <div className="notification-filters">
              {["all", "unread", "read"].map((status) => (
                <button
                  type="button"
                  key={status}
                  className={
                    notificationFilter === status ? "active-filter" : ""
                  }
                  onClick={() => {
                    setNotificationFilter(status);
                    setNotificationPage(1);
                  }}
                >
                  {status.charAt(0).toUpperCase() + status.slice(1)}
                </button>
              ))}
            </div>

            <label className="hide-read-control">
              <input
                type="checkbox"
                checked={hideReadNotifications}
                onChange={(event) => {
                  setHideReadNotifications(event.target.checked);
                  setNotificationPage(1);
                }}
              />
              Hide read notifications
            </label>

            <div className="notification-sorting">
              <label htmlFor="notification-sort">Sort:</label>
              <select
                id="notification-sort"
                value={notificationSort}
                onChange={(event) => {
                  setNotificationSort(event.target.value);
                  setNotificationPage(1);
                }}
              >
                <option value="newest">Newest First</option>
                <option value="oldest">Oldest First</option>
              </select>
            </div>
          </div>

          {notificationLoading ? (
            <p>Loading notifications...</p>
          ) : filteredNotifications.length === 0 ? (
            <p className="notification-empty">
              {notifications.length === 0
                ? "No notifications yet."
                : "No notifications match your search or filters."}
            </p>
          ) : (
            <>
              <div className="notification-list">
                {paginatedNotifications.map((notification) => (
                  <div
                    className={
                      notification.read
                        ? "notification-item"
                        : "notification-item unread-notification"
                    }
                    key={notification.id}
                  >
                    <p>{notification.message}</p>

                    <p className="notification-timestamp">
                      {notification.createdAt
                        ? `Created on ${new Date(
                            notification.createdAt
                          ).toLocaleString()}`
                        : "Creation time unavailable"}
                    </p>

                    <div className="notification-actions">
                      {!notification.read && (
                        <button
                          type="button"
                          className="outline-button"
                          onClick={() =>
                            markNotificationAsRead(notification.id)
                          }
                        >
                          Mark as read
                        </button>
                      )}

                      <button
                        type="button"
                        className="delete-notification-button"
                        onClick={() =>
                          deleteNotification(notification.id)
                        }
                      >
                        Delete
                      </button>
                    </div>
                  </div>
                ))}
              </div>

              {filteredNotifications.length > notificationsPerPage && (
                <div className="notification-pagination">
                  <button
                    type="button"
                    disabled={notificationPage === 1}
                    onClick={() =>
                      setNotificationPage((page) => Math.max(1, page - 1))
                    }
                  >
                    Previous
                  </button>

                  <span>
                    Page {notificationPage} of {totalNotificationPages} ·{" "}
                    {filteredNotifications.length} notifications
                  </span>

                  <button
                    type="button"
                    disabled={notificationPage >= totalNotificationPages}
                    onClick={() =>
                      setNotificationPage((page) =>
                        Math.min(totalNotificationPages, page + 1)
                      )
                    }
                  >
                    Next
                  </button>
                </div>
              )}
            </>
          )}

          <div className="notification-footer-actions">
            <button
              type="button"
              className="delete-notification-button"
              onClick={deleteAllNotifications}
              disabled={notifications.length === 0}
            >
              Clear all notifications
            </button>

            {unreadCount > 0 && (
              <button
                type="button"
                className="mark-all-button"
                onClick={markAllNotificationsAsRead}
              >
                Mark all as read
              </button>
            )}

            <button
              type="button"
              className="outline-button"
              onClick={fetchNotifications}
            >
              Refresh notifications
            </button>
          </div>
            </section>
      )}

      {showEditHistory && (
<div
  className="edit-history-overlay"
  style={{
    position: "fixed",
    inset: 0,
    background: "rgba(0, 0, 0, 0.75)",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    zIndex: 999999,
    padding: "20px",
  }}
>
         <section
  className="edit-history-modal"
  style={{
    position: "relative",
    background: "#ffffff",
    color: "#222222",
    width: "100%",
    maxWidth: "750px",
    maxHeight: "85vh",
    overflowY: "auto",
    padding: "25px",
    borderRadius: "16px",
    boxShadow: "0 15px 50px rgba(0, 0, 0, 0.25)",
    boxSizing: "border-box",
  }}
>
            <div className="edit-history-heading">
              <div>
                <span className="eyebrow">QUESTION ACTIVITY</span>
                <h2>Question Edit History</h2>
                <p>Previous versions of this question</p>
              </div>

              <button
                type="button"
                className="close-button"
                onClick={() => setShowEditHistory(false)}
              >
                ×
              </button>
            </div>

            {editHistoryLoading ? (
              <p>Loading edit history...</p>
            ) : editHistory.length === 0 ? (
              <p>No edit history available for this question.</p>
            ) : (
              <div className="edit-history-list">
                {editHistory.map((history) => (
                  <article
                    className="edit-history-card"
                    key={history.id}
                  >
                    <p className="edit-history-date">
                      Edited on:{" "}
                      {history.editedAt
                        ? new Date(history.editedAt).toLocaleString()
                        : "Date unavailable"}
                    </p>

                    <h3>{history.previousTitle}</h3>

                    <p>{history.previousDescription}</p>

                    <p>
                      <strong>Category:</strong>{" "}
                      {history.previousCategory}
                    </p>

                    <p>
                      <strong>Tags:</strong>{" "}
                      {history.previousTags || "No tags"}
                    </p>
                    <button
  type="button"
  className="outline-button"
  onClick={() => setCompareHistory(history)}
>
  Compare with Current Version
</button>
<button
  type="button"
  className="outline-button"
  onClick={() =>
    setCompareAnswerHistory({
      history,
      answerId: selectedHistoryAnswer,
    })
  }
>
  Compare with Current Version
</button>
                    <button
  type="button"
  className="outline-button"
  onClick={async () => {
    const confirmed = window.confirm(
      "Are you sure you want to restore this previous version?"
    );

    if (!confirmed) return;

    try {
      const response = await apiFetch(
        `/api/questions/${history.question.id}/restore/${history.id}`,
        {
          method: "PUT",
        }
      );

      if (!response.ok) {
        throw new Error("Could not restore this version.");
      }

      window.alert("Previous version restored successfully!");

      setShowEditHistory(false);
      fetchQuestions();
    } catch (error) {
      window.alert(error.message);
    }
  }}
>
  Restore This Version
</button>
                  </article>
                ))}
              </div>
            )}
  {compareHistory && (() => {
const currentQuestion = questions.find(
  q => Number(q.id) === Number(compareHistory.question.id)
) || compareHistory.question;
  return (
  <div className="version-comparison">
    <h3>Question Version Comparison</h3>

    <div className="version-comparison-grid">
      <div>
        <h4>Previous Version</h4>
   <p className={compareHistory.previousTitle !== currentQuestion?.title ? "version-changed" : "version-unchanged"}>
  <strong>Title:</strong> {compareHistory.previousTitle}
</p>

<p className={compareHistory.previousDescription !== currentQuestion?.description ? "version-changed" : "version-unchanged"}>
  <strong>Description:</strong> {compareHistory.previousDescription}
</p>

<p className={compareHistory.previousCategory !== currentQuestion?.category ? "version-changed" : "version-unchanged"}>
  <strong>Category:</strong> {compareHistory.previousCategory}
</p>

<p className={compareHistory.previousTags !== currentQuestion?.tags ? "version-changed" : "version-unchanged"}>
  <strong>Tags:</strong> {compareHistory.previousTags || "No tags"}
</p>
      </div>

      <div>
<h4>Current Version</h4>

<p className={compareHistory.previousTitle !== currentQuestion?.title ? "version-changed" : "version-unchanged"}>
  <strong>Title:</strong> {currentQuestion?.title || "Question not found"}
</p>

<p className={compareHistory.previousDescription !== currentQuestion?.description ? "version-changed" : "version-unchanged"}>
  <strong>Description:</strong> {currentQuestion?.description || "Question not found"}
</p>

<p className={compareHistory.previousCategory !== currentQuestion?.category ? "version-changed" : "version-unchanged"}>
  <strong>Category:</strong> {currentQuestion?.category || "Question not found"}
</p>

<p className={compareHistory.previousTags !== currentQuestion?.tags ? "version-changed" : "version-unchanged"}>
  <strong>Tags:</strong> {currentQuestion?.tags || "No tags"}
</p>

      </div>
    </div>

    <button
      type="button"
      className="outline-button"
      onClick={() => setCompareHistory(null)}
    >
      Close Comparison
    </button>
  </div>
  );
})()}
          </section>
        </div>
      )}

{showAnswerEditHistory && (
  <div
    className="edit-history-overlay"
    style={{
      position: "fixed",
      inset: 0,
      background: "rgba(0, 0, 0, 0.75)",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      zIndex: 999999,
      padding: "20px",
    }}
  >
    <section
      className="edit-history-modal"
      style={{
        position: "relative",
        background: "#ffffff",
        color: "#222222",
        width: "100%",
        maxWidth: "750px",
        maxHeight: "85vh",
        overflowY: "auto",
        padding: "25px",
        borderRadius: "16px",
        boxShadow: "0 15px 50px rgba(0, 0, 0, 0.25)",
        boxSizing: "border-box",
      }}
    >
      <div className="edit-history-heading">
        <div>
          <span className="eyebrow">ANSWER ACTIVITY</span>
          <h2>Answer Edit History</h2>
          <p>View previous versions of this answer.</p>
        </div>

        <button
          type="button"
          onClick={() => setShowAnswerEditHistory(false)}
        >
          ×
        </button>
      </div>

      {answerEditHistoryLoading ? (
        <p>Loading answer history...</p>
      ) : answerEditHistoryError ? (
        <p className="form-message">
          {answerEditHistoryError}
        </p>
      ) : answerEditHistory.length === 0 ? (
        <p>No edit history available for this answer.</p>
      ) : (
        <div className="edit-history-list">
          {answerEditHistory.map((history) => (
         <article
  className="edit-history-card"
  key={history.id}
>
  <p>{history.previousContent}</p>

  <p className="edit-history-date">
    Edited on:{" "}
    {history.editedAt
      ? new Date(history.editedAt).toLocaleString()
      : "Date unavailable"}
  </p>

  <button
    type="button"
    className="outline-button"
    onClick={() =>
      restoreAnswerVersion(selectedHistoryAnswer, history.id)
    }
  >
    Restore This Version
  </button>
    <button
    type="button"
    className="outline-button"
    onClick={() =>
      setCompareAnswerHistory({
        history,
        answerId: selectedHistoryAnswer,
      })
    }
  >
    Compare with Current Version
  </button>
</article>
          ))}
        </div>
      )}
    </section>
  </div>
)}
{compareAnswerHistory && (
  <div
    style={{
      position: "fixed",
      inset: 0,
      backgroundColor: "rgba(0,0,0,0.75)",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      zIndex: 1000000,
      padding: "20px",
    }}
  >
    <div
      style={{
        backgroundColor: "#ffffff",
        color: "#222222",
        width: "100%",
        maxWidth: "850px",
        maxHeight: "85vh",
        overflowY: "auto",
        padding: "25px",
        borderRadius: "16px",
      }}
    >
      <h2>Compare Answer Versions</h2>
{(() => {
  const comparison = compareAnswerText(
    compareAnswerHistory.history.previousContent,
    currentAnswerContent
  );

  return (
    <div className="version-comparison">
      <div>
        <h3>Previous Version</h3>
        <p>
          {comparison.previous.map((item, index) => (
            <span
              key={index}
              style={{
                backgroundColor: item.changed ? "#ffd6d6" : "transparent",
                color: "#222222",
                marginRight: "4px",
              }}
            >
              {item.word}{" "}
            </span>
          ))}
        </p>
      </div>

      <div>
        <h3>Current Version</h3>
        <p>
          {comparison.current.map((item, index) => (
            <span
              key={index}
              style={{
                backgroundColor: item.changed ? "#c8f7d2" : "transparent",
                color: "#222222",
                marginRight: "4px",
              }}
            >
              {item.word}{" "}
            </span>
          ))}
        </p>
      </div>
    </div>
  );
})()}


      <button
        type="button"
        onClick={() => setCompareAnswerHistory(null)}
      >
        Close Comparison
      </button>
    </div>
  </div>
)}

      {showStatusHistory && (
  <div
    className="edit-history-overlay"
    style={{
      position: "fixed",
      inset: 0,
      background: "rgba(0, 0, 0, 0.75)",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      zIndex: 999999,
      padding: "20px",
    }}
  >
    <section
      className="edit-history-modal"
      style={{
        position: "relative",
        background: "#ffffff",
        color: "#222222",
        width: "100%",
        maxWidth: "750px",
        maxHeight: "85vh",
        overflowY: "auto",
        padding: "25px",
        borderRadius: "16px",
        boxShadow: "0 15px 50px rgba(0, 0, 0, 0.25)",
        boxSizing: "border-box",
      }}
    >
      <div className="edit-history-heading">
        <div>
          <span className="eyebrow">QUESTION ACTIVITY</span>
          <h2>Question Status History</h2>
          <p>Track every status change of this question.</p>
        </div>

        <button
          type="button"
          className="close-button"
          onClick={() => setShowStatusHistory(false)}
        >
          ×
        </button>
      </div>

      {statusHistoryLoading ? (
        <p>Loading status history...</p>
      ) : statusHistoryError ? (
        <p className="form-message">{statusHistoryError}</p>
      ) : statusHistory.length === 0 ? (
        <p>No status history available for this question.</p>
      ) : (
        <div className="edit-history-list">
          {statusHistory.map((history) => (
            <article
              className="edit-history-card"
              key={history.id}
            >
              <h3>
                {history.previousStatus} → {history.newStatus}
              </h3>

              <p className="edit-history-date">
                Changed on:{" "}
                {history.changedAt
                  ? new Date(history.changedAt).toLocaleString()
                  : "Date unavailable"}
              </p>
            </article>
          ))}
        </div>
      )}
    </section>
  </div>
)}

      {showLeaderboard && (
  <section className="leaderboard-section">
    <div className="leaderboard-heading">
      <div>
        <span className="eyebrow">QUERYHUB COMMUNITY</span>
        <h2>🏆 Leaderboard</h2>
        <p>Celebrating our top knowledge contributors!</p>
      </div>

      <button
        type="button"
        className="close-button"
        onClick={() => setShowLeaderboard(false)}
      >
        ×
      </button>
    </div>

    {leaderboardLoading ? (
      <p>Loading leaderboard...</p>
    ) : leaderboardError ? (
      <p className="form-message">{leaderboardError}</p>
    ) : leaderboardUsers.length === 0 ? (
      <p>No users found.</p>
    ) : (
      <div className="leaderboard-list">
        {leaderboardUsers.map((user, index) => (
          <div className="leaderboard-item" key={user.id}>
            <span className={`leaderboard-rank rank-${index + 1}`}>
              {index === 0
                ? "🥇"
                : index === 1
                ? "🥈"
                : index === 2
                ? "🥉"
                : `#${index + 1}`}
            </span>

            <div className="leaderboard-user-info">
              <h3>{user.name}</h3>
              <p>Community Contributor</p>
            </div>

            <span className="leaderboard-points">
              ⭐ {user.reputation} points
            </span>
          </div>
        ))}
      </div>
    )}
  </section>
)}
{loggedInUser?.role === "ADMIN" && (
  <section id="admin-dashboard" className="admin-dashboard">
    <div className="leaderboard-heading">
      <div>
        <span className="eyebrow">ADMINISTRATION</span>
        <h2>🛡️ Admin Dashboard</h2>
        <p>Manage QueryHub reports and community activity.</p>
      </div>
    </div>
    {adminReportLoading ? (
  <p>Loading report statistics...</p>
) : adminReportError ? (
  <p className="form-message">{adminReportError}</p>
) : adminReportStats ? (
  <div className="admin-stats-grid">
    <div className="admin-stat-card">
      <h3>Total Reports</h3>
      <p>{adminReportStats.totalReports}</p>
    </div>

    <div className="admin-stat-card">
      <h3>Pending Reports</h3>
      <p>{adminReportStats.pendingReports}</p>
    </div>

    <div className="admin-stat-card">
      <h3>Resolved Reports</h3>
      <p>{adminReportStats.resolvedReports}</p>
    </div>

    <div className="admin-stat-card">
      <h3>Rejected Reports</h3>
      <p>{adminReportStats.rejectedReports}</p>
    </div>

    <div className="admin-stat-card">
      <h3>Question Reports</h3>
      <p>{adminReportStats.questionReports}</p>
    </div>

    <div className="admin-stat-card">
      <h3>Answer Reports</h3>
      <p>{adminReportStats.answerReports}</p>
    </div>
  </div>
) : (
  <p>Report statistics are not loaded yet.</p>
)}
  </section>
)}

      <main>
        <section className="hero" id="home">
          <div className="hero-content">
            <span className="eyebrow">
              LEARN TOGETHER. GROW TOGETHER.
            </span>

            <h1>
              Have a question?
              <br />
              <span>Find your answer.</span>
            </h1>

            <p>
              A community for curious minds to ask questions, share
              knowledge, and help each other grow.
            </p>

            <div className="search-box">
              <span>⌕</span>

              <input
                type="text"
                placeholder="Search questions, topics or tags..."
                value={search}
                onChange={(event) => setSearch(event.target.value)}
              />

              <button
                type="button"
                onClick={() =>
                  document.getElementById("questions")?.scrollIntoView({
                    behavior: "smooth",
                  })
                }
              >
                Search
              </button>
            </div>

            <div className="hero-note">
              <span>✓</span> Ask freely. Learn openly. Help everyone.
            </div>
          </div>

          <div className="hero-art" aria-hidden="true">
            <div className="art-circle circle-one"></div>
            <div className="art-circle circle-two"></div>

            <div className="art-card">
              <div className="art-card-top">
                <span className="art-dot"></span>
                <span className="art-dot"></span>
                <span className="art-dot"></span>
              </div>

              <div className="art-question">
                How can we learn better?
              </div>

              <div className="art-line"></div>
              <div className="art-line short"></div>

              <div className="art-answer">
                <span className="answer-check">✓</span>
                Share knowledge. Grow together.
              </div>
            </div>

            <div className="floating-badge badge-top">
              💡 Ask anything
            </div>

            <div className="floating-badge badge-bottom">
              ✦ Learn something new
            </div>
          </div>
        </section>

        <section className="stats">
          <div>
            <strong>Community</strong>
            <span>Learn from one another</span>
          </div>

          <div>
            <strong>Knowledge</strong>
            <span>Share what you know</span>
          </div>

          <div>
            <strong>Growth</strong>
            <span>Improve every day</span>
          </div>
        </section>

        <section className="questions-section" id="questions">
          <div className="section-heading">
            <div>
              <span className="eyebrow">EXPLORE THE COMMUNITY</span>
              <h2>Discover questions</h2>
              <p>Find answers or help someone solve a problem.</p>
            </div>

          <button
  type="button"
  className="nav-button"
  onClick={() => {
    if (!loggedInUser) {
      setAuthMode("login");
      setAuthError("Please log in to ask a question.");
      setShowAuth(true);
      return;
    }

    setShowForm(true);
  }}
>
  + Ask Question
</button>
          </div>

          <div className="category-list" id="categories">
            {categories.map((item) => (
              <button
                type="button"
                key={item}
                className={
                  category === item
                    ? "category active-category"
                    : "category"
                }
                onClick={() => setCategory(item)}
              >
                {item}
              </button>
            ))}
          </div>
          <div className="sort-container">
  <label htmlFor="question-sort">Sort questions:</label>
  <select
    id="question-sort"
    value={sortBy}
    onChange={(event) => setSortBy(event.target.value)}
  >
    <option value="newest">Newest First</option>
    <option value="views">Most Viewed</option>
    <option value="upvotes">Most Upvoted</option>
  </select>
</div>

       
<div className="trending-section">
  <div className="trending-heading">
    <div>
      <span className="section-eyebrow">POPULAR RIGHT NOW</span>
      <h2>🔥 Trending Questions</h2>
      <p>Discover what the community is discussing.</p>
    </div>

    <span className="trending-count">
      {trendingQuestions.length} trending
    </span>
  </div>

  {trendingQuestions.length === 0 ? (
    <p>No trending questions yet.</p>
  ) : (
    <div className="trending-list">
      {trendingQuestions.slice(0, 5).map((question, index) => (
        <article className="trending-card" key={question.id}>
          <div className="trending-card-top">
            <span className="trending-rank">
              #{index + 1}
            </span>

            <span className="trending-score">
              🔥 {question.engagementScore ?? 0} engagement
            </span>
          </div>

          <h3>{question.title}</h3>
        </article>
      ))}
    </div>
  )}
</div>


          {loading ? (
            <p>Loading questions...</p>
          ) : error ? (
            <div role="alert">
              <p>Could not load questions: {error}</p>

              <button
                type="button"
                className="outline-button"
                onClick={() => {
                  setLoading(true);
                  fetchQuestions();
                }}
              >
                Try again
              </button>
            </div>
          ) : (
                        <div className="question-list">
              {sortedQuestions.map((question) => {
                const tags = getTags(question.tags);
                const answers = answersByQuestion[question.id] || [];
                console.log("ANSWER RENDER CHECK", {
  questionId: question.id,
  answersCount: answers.length,
  answers
});

                return (
                  <article
  className="question-card"
  key={question.id}
  id={`question-${question.id}`}
>
                    <div className="vote-box">
                      <strong>
                        {question.upvotes ?? question.votes ?? 0}
                      </strong>
                      <span>votes</span>
                    </div>

                    <div className="question-content">
                      <span className="question-category">
                        {question.category || "General"}
                      </span>

                      <h3>{question.title || "Untitled question"}</h3>
                      <p className="question-views">
  👀 {followerCounts[question.id] ?? 0} followers
</p>
                      <button
  type="button"
  className="outline-button"
onClick={() => toggleBookmark(question.id)}
>
 {bookmarks.some(
  (bookmark) =>
    String(bookmark.question?.id) === String(question.id)
)
  ? "★ Saved"
  : "☆ Save Question"}
</button>
<button
  type="button"
  className="outline-button"
  onClick={() => toggleQuestionFollow(question.id)}
>
  {followedQuestions.some(
    (follow) =>
      String(follow.question?.id) === String(question.id)
  )
    ? "✓ Following"
    : "+ Follow Question"}
</button>
<button
  type="button"
  className="outline-button"
  onClick={() => {
    setReportTargetType("QUESTION");
    setReportTargetId(question.id);
    setReportReason("");
    setReportError("");
    setReportSuccess("");
    setShowReportForm(true);
  }}
>
  🚩 Report Question
</button>
<button
  type="button"
  className="outline-button"
  onClick={() => {
    const questionUrl = `${window.location.origin}/?question=${question.id}`;

    navigator.clipboard.writeText(questionUrl)
      .then(() => alert("Question link copied successfully!"))
      .catch(() => alert("Could not copy the question link."));
  }}
>
  ↗ Share Question
</button>

                      <p>
                        {question.description || "No description provided."}
                      </p>
                     <div className="question-comments-section">
  <button
    type="button"
    className="outline-button"
    onClick={() => {
      const isVisible = visibleQuestionComments[question.id];

      setVisibleQuestionComments((previous) => ({
        ...previous,
        [question.id]: !isVisible,
      }));

      if (!isVisible) {
        fetchQuestionComments(question.id);
      }
    }}
  >
    {visibleQuestionComments[question.id]
      ? "Hide Question Comments"
      : "View Question Comments"}
  </button>

  {visibleQuestionComments[question.id] && (
    <div className="comments-list">
      {questionCommentLoading[question.id] ? (
        <p>Loading comments...</p>
      ) : (
        <>
          {questionCommentErrors[question.id] && (
            <p role="alert" className="form-message">
              {questionCommentErrors[question.id]}
            </p>
          )}

          {(questionComments[question.id] || []).length === 0 ? (
            <p>No comments yet. Start the discussion!</p>
          ) : (
            (questionComments[question.id] || []).map((comment) => (
              <div className="comment-item" key={comment.id}>
                {editingQuestionCommentId === comment.id ? (
                  <div className="edit-comment-form">
                    <textarea
                      value={editedQuestionCommentContent}
                      onChange={(event) =>
                        setEditedQuestionCommentContent(event.target.value)
                      }
                      placeholder="Edit your comment..."
                      rows={2}
                    />

                    <button
                      type="button"
                      onClick={() =>
                        updateQuestionComment(question.id, comment.id)
                      }
                    >
                      Save Changes
                    </button>

                    <button
                      type="button"
                      onClick={() => {
                        setEditingQuestionCommentId(null);
                        setEditedQuestionCommentContent("");
                      }}
                    >
                      Cancel
                    </button>
                  </div>
                ) : (
                  <>
                    <p>{comment.content}</p>

                    <span>
                      By {comment.user?.name || "Community member"}
                    </span>

                    {String(comment.user?.id) ===
                      String(loggedInUser?.id) && (
                      <>
                        <button
                          type="button"
                          className="outline-button"
                          onClick={() => {
                            setEditingQuestionCommentId(comment.id);
                            setEditedQuestionCommentContent(comment.content);
                          }}
                        >
                          Edit Comment
                        </button>

                        <button
                          type="button"
                          className="outline-button"
                          onClick={() =>
                            deleteQuestionComment(question.id, comment.id)
                          }
                        >
                          Delete Comment
                        </button>
                      </>
                    )}
                  </>
                )}
              </div>
            ))
          )}

          <div className="comment-form">
            <textarea
              value={questionCommentDrafts[question.id] || ""}
              onChange={(event) =>
                setQuestionCommentDrafts((previous) => ({
                  ...previous,
                  [question.id]: event.target.value,
                }))
              }
              placeholder="Write a comment on this question..."
              rows={2}
              maxLength={1000}
            />

            <button
              type="button"
              className="nav-button"
              onClick={() => addQuestionComment(question.id)}
            >
              Post Comment
            </button>
          </div>
        </>
      )}
    </div>
  )}
</div>
                      {String(question.user?.id) === String(loggedInUser?.id) && (
  <button
    type="button"
    className="outline-button"
    onClick={() => {
      setEditingQuestionId(question.id);
      setEditedQuestionTitle(question.title || "");
      setEditedQuestionDescription(question.description || "");
      setEditedQuestionCategory(question.category || "");
    }}
  >
    Edit Question
  </button>
)}
{String(question.user?.id) === String(loggedInUser?.id) && (
  <button
    type="button"
    className="outline-button"
    onClick={() => deleteQuestion(question.id)}
  >
    Delete Question
  </button>
)}
<button
  type="button"
  className="outline-button"
  onClick={() => fetchQuestionEditHistory(question.id)}
>
  View Edit History
</button>
<button
  type="button"
  className="outline-button"
  onClick={() => fetchStatusHistory(question.id)}
>
  View Status History
</button>
{editingQuestionId === question.id && (
  <div className="edit-question-form">
    <h4>Edit Your Question</h4>

    <input
      type="text"
      value={editedQuestionTitle}
      onChange={(e) => setEditedQuestionTitle(e.target.value)}
      placeholder="Question title"
    />

    <textarea
      value={editedQuestionDescription}
      onChange={(e) => setEditedQuestionDescription(e.target.value)}
      placeholder="Question description"
    />

    <input
      type="text"
      value={editedQuestionCategory}
      onChange={(e) => setEditedQuestionCategory(e.target.value)}
      placeholder="Category"
    />

    <button
      type="button"
      onClick={() => updateQuestion(question.id, question.tags)}
    >
      Save Changes
    </button>

    <button
      type="button"
      className="outline-button"
      onClick={() => {
        setEditingQuestionId(null);
        setEditedQuestionTitle("");
        setEditedQuestionDescription("");
        setEditedQuestionCategory("");
      }}
    >
      Cancel
    </button>
  </div>
)}

                      <div className="tag-list">
                        {tags.map((tag, index) => (
                          <button
                            type="button"
                            key={`${tag}-${index}`}
                            className="tag"
                            onClick={() => setSearch(tag)}
                          >
                            {tag}
                          </button>
                        ))}
                      </div>
                    </div>

                    <div className="answer-count">
                      <strong>
                        {answersByQuestion[question.id]?.length ??
                          question.answers ??
                          question.answerCount ??
                          0}
                      </strong>
                      <span>answers</span>
                      <small>{question.status || "OPEN"}</small>

                      <button
                        type="button"
                        className="outline-button"
                        onClick={() => toggleAnswers(question.id)}
                      >
                        {expandedQuestionId === question.id
                          ? "Hide answers"
                          : "View answers"}
                      </button>
                    </div>

                    {expandedQuestionId === question.id && (
                      <div className="answers-panel">
                        <h4>Answers</h4>

                        {answerLoading[question.id] ? (
                          <p>Loading answers...</p>
                        ) : (
                          <>
                            {answerErrors[question.id] && (
                              <p role="alert" className="form-message">
                                {answerErrors[question.id]}
                              </p>
                            )}
                            <div className="answer-sort">
  <label htmlFor={`answer-sort-${question.id}`}>
    Sort Answers:
  </label>

  <select
    id={`answer-sort-${question.id}`}
    value={answerSort}
    onChange={(event) => setAnswerSort(event.target.value)}
  >
    <option value="ACCEPTED">Accepted Answer First</option>
    <option value="UPVOTES">Most Upvoted</option>
    <option value="NEWEST">Newest First</option>
    <option value="OLDEST">Oldest First</option>
  </select>
</div>
<div className="answer-search">
  <input
    type="text"
    placeholder="Search answers by keyword..."
    value={answerSearch[question.id] || ""}
    onChange={(event) =>
      setAnswerSearch((previous) => ({
        ...previous,
        [question.id]: event.target.value,
      }))
    }
  />
</div>
                            {answers.length === 0 ? (
                              <p>No answers yet. Be the first to help!</p>
                            ) : (
                              <div className="answers-list">
{[...answers]
  .filter((answer) =>
    (answer.content || "")
      .toLowerCase()
      .includes((answerSearch[question.id] || "").trim().toLowerCase())
  )
.sort((a, b) => {
  if (a.pinned !== b.pinned) {
    return Number(b.pinned) - Number(a.pinned);
  }

  switch (answerSort) {
    case "UPVOTES":
      return (b.upvotes ?? 0) - (a.upvotes ?? 0);

    case "NEWEST":
      return Number(b.id) - Number(a.id);

    case "OLDEST":
      return Number(a.id) - Number(b.id);

    case "ACCEPTED":
    default:
      return Number(Boolean(b.accepted)) -
             Number(Boolean(a.accepted));
  }
})
    .slice(
    (answerPage[question.id] || 0) * answersPerPage,
    (answerPage[question.id] || 0) * answersPerPage + answersPerPage
  )
  .map((answer) => (
                                  <div
  className="answer-item"
  key={answer.id}
  id={`answer-${answer.id}`}
>
                                    {answer.accepted && (
                                      <span className="accepted-label">
                                        ✓ Accepted answer
                                      </span>
                                    )}
                                    {answer.pinned && (
  <span className="pinned-label">
    📌 Pinned Answer
  </span>
)}

                                    <p>{answer.content}</p>

                                    <div className="answer-meta">
                                      <span>
                                        By{" "}
                                        {answer.user?.name ||
                                          answer.user?.email ||
                                          "Community member"}
                                      </span>

                                      <div className="answer-voting">
                                        <button
                                          type="button"
                                          className="vote-button"
                                          disabled={Boolean(
                                            answerVoting[answer.id]
                                          )}
                                          onClick={() =>
                                            voteAnswer(
                                              question.id,
                                              answer.id,
                                              "UP"
                                            )
                                          }
                                        >
                                          ▲ Upvote ({answer.upvotes ?? 0})
                                        </button>
                                        <button
  type="button"
  className="outline-button"
  onClick={() => togglePinAnswer(answer.id)}
>
  {answer.pinned ? "Unpin Answer" : "Pin Answer"}
</button>
<button
  type="button"
  className="outline-button"
  onClick={() => toggleAnswerBookmark(answer.id)}
>
  {answerBookmarks.some(
    (bookmark) =>
      Number(bookmark.answer?.id) === Number(answer.id)
  )
    ? "Remove Bookmark"
    : "Bookmark Answer"}
</button>
<button
  type="button"
  className="outline-button"
  onClick={() => {
    const answerUrl =
      `${window.location.origin}/?question=${question.id}&answer=${answer.id}`;

    navigator.clipboard.writeText(answerUrl)
      .then(() => alert("Answer link copied successfully!"))
      .catch(() => alert("Could not copy answer link."));
  }}
>
  Share Answer
</button>

                                        <button
                                          type="button"
                                          className="vote-button"
                                          disabled={Boolean(
                                            answerVoting[answer.id]
                                          )}
                                          onClick={() =>
                                            voteAnswer(
                                              question.id,
                                              answer.id,
                                              "DOWN"
                                            )
                                          }
                                        >
                                          ▼ Downvote ({answer.downvotes ?? 0})
                                        </button>
                                      </div>
                                      {console.log("Question ID:", question.id,
  "Owner ID:", question.user?.id,
  "Logged-in ID:", localStorage.getItem("queryhubUserId"),
  "Status:", question.status)}
{String(question.user?.id) ===
  String(loggedInUser?.id) &&
  String(answer.user?.id) !==
    String(question.user?.id) &&
  question.status !== "CLOSED" && (
    <button
      type="button"
      className="outline-button"
      onClick={() =>
        acceptAnswer(
          question.id,
          answer.id
        )
      }
    >
      ✓ Accept Answer
    </button>
  )}


{String(answer.user?.id) === String(loggedInUser?.id) && (
  <button
    type="button"
    className="outline-button"
    onClick={() => {
      setEditingAnswerId(answer.id);
      setEditedAnswerContent(answer.content);
    }}
  >
    Edit Answer
  </button>
)}

<button
  type="button"
  className="outline-button"
  onClick={() => fetchAnswerEditHistory(answer.id)}
>
  View Edit History
</button>

{String(answer.user?.id) === String(loggedInUser?.id) && (
  <button
    type="button"
    className="outline-button"
    onClick={() => deleteAnswer(question.id, answer.id)}
  >
    Delete Answer
  </button>
)}
<button
  type="button"
  className="outline-button"
  onClick={() => {
    setReportTargetType("ANSWER");
    setReportTargetId(answer.id);
    setReportReason("");
    setReportError("");
    setReportSuccess("");
    setShowReportForm(true);
  }}
>
  🚩 Report Answer
</button>

{editingAnswerId === answer.id && (
  <div className="edit-answer-form">
    <textarea
      value={editedAnswerContent}
      onChange={(e) => setEditedAnswerContent(e.target.value)}
      placeholder="Edit your answer..."
    />

    <button
      type="button"
      onClick={() => updateAnswer(question.id, answer.id)}
    >
      Save Changes
    </button>

    <button
      type="button"
      onClick={() => {
        setEditingAnswerId(null);
        setEditedAnswerContent("");
      }}
    >
      Cancel
    </button>
  </div>
)}
                                    </div>
                                    <div className="comments-section">
  <button
    type="button"
    className="outline-button"
    onClick={() => {
      const isVisible = visibleComments[answer.id];

      setVisibleComments((previous) => ({
        ...previous,
        [answer.id]: !isVisible,
      }));

      if (!isVisible) {
        fetchAnswerComments(answer.id);
      }
    }}
  >
    {visibleComments[answer.id]
      ? "Hide Comments"
      : "View Comments"}
  </button>

  {visibleComments[answer.id] && (
    <div className="comments-list">
      {commentLoading[answer.id] ? (
        <p>Loading comments...</p>
      ) : (
        <>
          {commentErrors[answer.id] && (
            <p role="alert" className="form-message">
              {commentErrors[answer.id]}
            </p>
          )}

          {(answerComments[answer.id] || []).length === 0 ? (
            <p>No comments yet. Start the discussion!</p>
          ) : (
            (answerComments[answer.id] || []).map((comment) => (
             <div className="comment-item" key={comment.id}>
  {editingCommentId === comment.id ? (
    <div className="edit-comment-form">
      <textarea
        value={editedCommentContent}
        onChange={(event) =>
          setEditedCommentContent(event.target.value)
        }
        placeholder="Edit your comment..."
        rows={2}
      />

      <button
        type="button"
        onClick={() =>
          updateAnswerComment(answer.id, comment.id)
        }
      >
        Save Changes
      </button>

      <button
        type="button"
        onClick={() => {
          setEditingCommentId(null);
          setEditedCommentContent("");
        }}
      >
        Cancel
      </button>
    </div>
  ) : (
    <>
      <p>{comment.content}</p>

      <span>
        By {comment.user?.name || "Community member"}
      </span>

      {String(comment.user?.id) === String(loggedInUser?.id) && (
        <>
          <button
            type="button"
            className="outline-button"
            onClick={() => {
              setEditingCommentId(comment.id);
              setEditedCommentContent(comment.content);
            }}
          >
            Edit Comment
          </button>

          <button
            type="button"
            className="outline-button"
            onClick={() =>
              deleteAnswerComment(answer.id, comment.id)
            }
          >
            Delete Comment
          </button>
        </>
      )}
    </>
  )}
</div>
            ))
          )}

          <div className="comment-form">
            <textarea
              value={commentDrafts[answer.id] || ""}
              onChange={(event) =>
                setCommentDrafts((previous) => ({
                  ...previous,
                  [answer.id]: event.target.value,
                }))
              }
              placeholder="Write a comment..."
              rows={2}
              maxLength={1000}
            />

            <button
              type="button"
              className="nav-button"
              onClick={() => addComment(answer.id)}
            >
              Post Comment
            </button>
          </div>
        </>
      )}
    </div>
  )}
</div>
                                  </div>
                                ))}
                                <div className="answer-pagination">
  <button
    type="button"
    disabled={(answerPage[question.id] || 0) === 0}
    onClick={() =>
      setAnswerPage((previous) => ({
        ...previous,
        [question.id]: (previous[question.id] || 0) - 1,
      }))
    }
  >
    Previous
  </button>

  <span>
    Page {(answerPage[question.id] || 0) + 1}
  </span>

  <button
    type="button"
    disabled={
      (answerPage[question.id] || 0) >=
      Math.ceil(
        answers.filter((answer) =>
          (answer.content || "")
            .toLowerCase()
            .includes((answerSearch[question.id] || "").trim().toLowerCase())
        ).length / answersPerPage
      ) - 1
    }
    onClick={() =>
      setAnswerPage((previous) => ({
        ...previous,
        [question.id]: (previous[question.id] || 0) + 1,
      }))
    }
  >
    Next
  </button>
</div>
                              </div>
                            )}

                            <form
                              className="answer-form"
                              onSubmit={(event) =>
                                addAnswer(event, question.id)
                              }
                            >
                              <label htmlFor={`answer-${question.id}`}>
                                Write your answer
                              </label>

                              <textarea
                                id={`answer-${question.id}`}
                                value={answerDrafts[question.id] || ""}
                                onChange={(event) =>
                                  setAnswerDrafts((previous) => ({
                                    ...previous,
                                    [question.id]: event.target.value,
                                  }))
                                }
                                placeholder="Explain your solution..."
                                rows={3}
                                maxLength={5000}
                                required
                              />
                              {answerErrors[question.id] && (
  <p role="alert" className="form-message">
    {answerErrors[question.id]}
  </p>
)}

                              <button
                                type="submit"
                                className="nav-button"
                                disabled={Boolean(
                                  answerSubmitting[question.id]
                                )}
                              >
                                {answerSubmitting[question.id]
                                  ? "Posting..."
                                  : "Post answer"}
                              </button>
                            </form>
                          </>
                        )}
                      </div>
                    )}
                  </article>
                );
              })}

              {filteredQuestions.length === 0 && (
                <div className="empty-state">
                  <span>🔎</span>
                  <h3>No questions found</h3>
                  <p>
                    Try another search or choose a different category.
                  </p>

                  <button
                    type="button"
                    className="outline-button"
                    onClick={() => {
                      setSearch("");
                      setCategory("All");
                    }}
                  >
                    Clear filters
                  </button>
                </div>
              )}
            </div>
          )}

          <div className="bottom-cta">
            <div>
              <h3>Have something to ask?</h3>
              <p>Your question could help someone else learn too.</p>
            </div>

       <button
  type="button"
  className="nav-button"
  onClick={() => {
    if (!loggedInUser) {
      setAuthMode("login");
      setAuthError("Please log in to ask a question.");
      setShowAuth(true);
      return;
    }

    setShowForm(true);
  }}
>
  Ask your question →
</button>
        
          </div>
        </section>

        <section className="questions-section" id="my-bookmarks">
          <div className="section-heading">
            <div>
              <span className="eyebrow">YOUR SAVED CONTENT</span>
              <h2>🔖 My Bookmarks ({bookmarks.length})</h2>
              <p>Questions you have saved for later.</p>
            </div>

            <button
              type="button"
              className="outline-button"
              onClick={fetchBookmarks}
            >
              Refresh Bookmarks
            </button>
          </div>

          {bookmarkLoading ? (
            <p>Loading bookmarks...</p>
          ) : bookmarkError ? (
            <p role="alert" className="form-message">
              {bookmarkError}
            </p>
          ) : bookmarks.length === 0 ? (
            <div className="empty-state">
              <span>🔖</span>
              <h3>No bookmarks yet</h3>
              <p>Save questions that you want to read later.</p>
            </div>
                   ) : (
           

              <div className="question-list">
              {bookmarks.map((bookmark) => {
                const question = bookmark.question;

                if (!question) {
                  return null;
                }

                return (
                  <article className="question-card" key={bookmark.id}>
                    <div className="question-content">
                      <span className="question-category">
                        {question.category || "General"}
                      </span>

                      <h3>{question.title || "Untitled question"}</h3>
                      <p className="question-views">
  👁 {question.views || 0} views
</p>
<button
  type="button"
  className="outline-button"
  onClick={() => increaseQuestionViews(question.id)}
>
  View Question
</button>

                      <p>
                        {question.description || "No description available."}
                      </p>

                      <button
                        type="button"
                        className="outline-button"
                        onClick={() => toggleBookmark(question.id)}
                      >
                        Remove Bookmark
                      </button>
                    </div>
                  </article>
                );
              })}
            </div>
          )}
        </section>
        <section className="questions-section" id="my-followed-questions">
  <div className="section-heading">
    <div>
      <span className="eyebrow">YOUR FOLLOWED CONTENT</span>
      <h2>👀 My Followed Questions ({followedQuestions.length})</h2>
      <p>Questions you are following for updates.</p>
    </div>

    <button
      type="button"
      className="outline-button"
      onClick={fetchFollowedQuestions}
    >
      Refresh Following
    </button>
  </div>

  {followLoading ? (
    <p>Loading followed questions...</p>
  ) : followError ? (
    <p role="alert" className="form-message">
      {followError}
    </p>
  ) : followedQuestions.length === 0 ? (
    <div className="empty-state">
      <span>👀</span>
      <h3>No followed questions yet</h3>
      <p>Follow questions to keep track of discussions.</p>
    </div>
  ) : (
    <div className="question-list">
      {followedQuestions.map((follow) => {
        const question = follow.question;

        if (!question) {
          return null;
        }

        return (
          <article className="question-card" key={follow.id}>
            <div className="question-content">
              <span className="question-category">
                {question.category || "General"}
              </span>

              <h3>{question.title || "Untitled question"}</h3>

              <p className="question-views">
                👁 {question.views || 0} views
              </p>

              <button
                type="button"
                className="outline-button"
                onClick={() => increaseQuestionViews(question.id)}
              >
                View Question
              </button>

              <p>
                {question.description || "No description available."}
              </p>

              <button
                type="button"
                className="outline-button"
                onClick={() => toggleQuestionFollow(question.id)}
              >
                ✓ Unfollow Question
              </button>
            </div>
          </article>
        );
      })}
    </div>
  )}
</section>
{showDrafts && (
  <section className="questions-section" id="my-drafts">
    <div className="section-heading">
      <div>
        <span className="eyebrow">YOUR SAVED CONTENT</span>
        <h2>📝 My Drafts ({drafts.length})</h2>
        <p>Your unpublished questions.</p>
      </div>

      <button
        type="button"
        className="outline-button"
        onClick={() => setShowDrafts(false)}
      >
        Close
      </button>
    </div>

    {draftsError && <p className="form-message">{draftsError}</p>}

    {draftsLoading ? (
      <p>Loading drafts...</p>
    ) : drafts.length === 0 ? (
      <div className="empty-state">
        <h3>No saved drafts</h3>
        <p>Your saved questions will appear here.</p>
      </div>
    ) : (
      <div className="question-list">
        {drafts.map((draft) => (
          <article className="question-card" key={draft.id}>
            <h3>{draft.title}</h3>
            <p>{draft.description}</p>
            <p>Category: {draft.category}</p>
            <p>Tags: {draft.tags || "No tags"}</p>
<div className="draft-schedule">
  <label htmlFor={`schedule-${draft.id}`}>
    Schedule publication:
  </label>

  <input
    id={`schedule-${draft.id}`}
    type="datetime-local"
    min={new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
      .toISOString()
      .slice(0, 16)}
    onChange={(event) => {
      draft.selectedScheduleTime = event.target.value;
    }}
  />

  <button
    type="button"
    className="nav-button"
    onClick={() => {
      if (!draft.selectedScheduleTime) {
        setDraftsError("Please select a date and time.");
        return;
      }

      scheduleDraft(draft.id, draft.selectedScheduleTime);
    }}
  >
    Schedule Question
  </button>
</div>
            <div className="draft-actions">
              <button
                type="button"
                className="nav-button"
                onClick={() => publishDraft(draft.id)}
              >
                Publish
              </button>
              <button
  type="button"
  className="outline-button"
  onClick={() => editDraft(draft)}
>
  Edit Draft
</button>

              <button
                type="button"
                className="outline-button"
                onClick={() => deleteDraft(draft.id)}
              >
                Delete
              </button>
            </div>
          </article>
        ))}
      </div>
    )}
  </section>
)}
{showClosedQuestions && (
  <section className="closed-questions" id="my-closed-questions">
    <h2>My Closed Questions</h2>

    <button
      type="button"
      onClick={() => setShowClosedQuestions(false)}
    >
      Close
    </button>

    {closedLoading && <p>Loading closed questions...</p>}

    {closedError && <p>{closedError}</p>}

    {!closedLoading &&
      !closedError &&
      closedQuestions.length === 0 && (
        <p>You don't have any closed questions.</p>
      )}

    {closedQuestions.map((question) => (
      <article key={question.id} className="question-card">
        <h3>{question.title}</h3>
        <p>{question.description}</p>
        <p>Category: {question.category}</p>
        <p>Status: {question.status}</p>

        <button
          type="button"
          onClick={() => reopenQuestion(question.id)}
        >
          Reopen Question
        </button>
      </article>
    ))}
  </section>
)}
{showScheduledQuestions && (
  <section className="questions-section" id="my-scheduled-questions">
    <div className="section-heading">
      <div>
        <span className="eyebrow">YOUR SCHEDULED CONTENT</span>
        <h2>📅 My Scheduled Questions ({scheduledQuestions.length})</h2>
        <p>Questions waiting for their scheduled publication time.</p>
      </div>

      <button
        type="button"
        className="outline-button"
        onClick={() => setShowScheduledQuestions(false)}
      >
        Close
      </button>
    </div>

    {draftsError && <p className="form-message">{draftsError}</p>}

    {scheduledLoading ? (
      <p>Loading scheduled questions...</p>
    ) : scheduledQuestions.length === 0 ? (
      <div className="empty-state">
        <h3>No scheduled questions</h3>
        <p>Your scheduled questions will appear here.</p>
      </div>
    ) : (
      <div className="question-list">
        {scheduledQuestions.map((question) => (
          <article className="question-card" key={question.id}>
            <h3>{question.title}</h3>
            <p>{question.description}</p>
            <p>Category: {question.category}</p>
            <p>
              Scheduled for:{" "}
              {question.scheduledAt
                ? new Date(question.scheduledAt).toLocaleString()
                : "Not available"}
            </p>
            <div className="draft-actions">
  <button
    type="button"
    className="outline-button"
    onClick={() => cancelScheduledQuestion(question.id)}
  >
    Cancel Scheduling
  </button>
</div>
          </article>
        ))}
      </div>
    )}
  </section>
)}
{loggedInUser?.role === "ADMIN" && (
  <section className="admin-dashboard" id="admin-dashboard">
 <span className="eyebrow">ADMIN PANEL</span>

<h2>Admin Analytics Dashboard</h2>
<p>Monitor platform activity and user statistics.</p>

<button
  type="button"
  className="outline-button"
  onClick={fetchAdminAnalytics}
>
  Load Analytics
</button>

{adminAnalyticsLoading && <p>Loading analytics...</p>}

{adminAnalyticsError && <p>{adminAnalyticsError}</p>}

{adminDashboardStats && adminActivityStats && adminReputationStats && (
  <>
    <div className="admin-report-list">
      <article className="admin-report-card">
        <h3>Total Users</h3>
        <p>{adminDashboardStats.totalUsers}</p>
      </article>

      <article className="admin-report-card">
        <h3>Total Questions</h3>
        <p>{adminDashboardStats.totalQuestions}</p>
      </article>

      <article className="admin-report-card">
        <h3>Total Answers</h3>
        <p>{adminDashboardStats.totalAnswers}</p>
      </article>

      <article className="admin-report-card">
        <h3>Total Reports</h3>
        <p>{adminDashboardStats.totalReports}</p>
      </article>

      <article className="admin-report-card">
        <h3>Active Users</h3>
        <p>{adminActivityStats.activeUsers}</p>
      </article>

      <article className="admin-report-card">
        <h3>Inactive Users</h3>
        <p>{adminActivityStats.inactiveUsers}</p>
      </article>

      <article className="admin-report-card">
        <h3>Admin Users</h3>
        <p>{adminActivityStats.adminUsers}</p>
      </article>

      <article className="admin-report-card">
        <h3>Normal Users</h3>
        <p>{adminActivityStats.normalUsers}</p>
      </article>

      <article className="admin-report-card">
        <h3>Highest Reputation</h3>
        <p>{adminReputationStats.highestReputation}</p>
      </article>

      <article className="admin-report-card">
        <h3>Lowest Reputation</h3>
        <p>{adminReputationStats.lowestReputation}</p>
      </article>

      <article className="admin-report-card">
        <h3>Average Reputation</h3>
        <p>{Number(adminReputationStats.averageReputation).toFixed(2)}</p>
      </article>
    </div>
        <div className="admin-chart-container">
      <h3>User Status Overview</h3>

      <ResponsiveContainer width="100%" height={300}>
        <PieChart>
          <Pie
            data={[
              {
                name: "Active Users",
                value: adminActivityStats.activeUsers || 0,
              },
              {
                name: "Inactive Users",
                value: adminActivityStats.inactiveUsers || 0,
              },
            ]}
            dataKey="value"
            nameKey="name"
            cx="50%"
            cy="50%"
            outerRadius={100}
            label
          >
            <Cell fill="#22c55e" />
            <Cell fill="#ef4444" />
          </Pie>

          <Tooltip />
          <Legend />
        </PieChart>
      </ResponsiveContainer>
    </div>
    <div className="admin-chart-container">
  <h3>User Role Distribution</h3>

  <ResponsiveContainer width="100%" height={300}>
    <PieChart>
      <Pie
        data={[
          {
            name: "Admin Users",
            value: adminActivityStats.adminUsers || 0,
          },
          {
            name: "Normal Users",
            value: adminActivityStats.normalUsers || 0,
          },
        ]}
        dataKey="value"
        nameKey="name"
        cx="50%"
        cy="50%"
        outerRadius={100}
        label
      >
        <Cell fill="#3b82f6" />
        <Cell fill="#f59e0b" />
      </Pie>

      <Tooltip />
      <Legend />
    </PieChart>
  </ResponsiveContainer>
</div>
<div className="admin-chart-container">
  <h3>Platform Statistics</h3>

  <ResponsiveContainer width="100%" height={300}>
    <BarChart
      data={[
        { name: "Users", total: adminDashboardStats.totalUsers || 0 },
        { name: "Questions", total: adminDashboardStats.totalQuestions || 0 },
        { name: "Answers", total: adminDashboardStats.totalAnswers || 0 },
        { name: "Reports", total: adminDashboardStats.totalReports || 0 },
      ]}
    >
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="name" />
      <YAxis allowDecimals={false} />
      <Tooltip />
      <Bar dataKey="total" fill="#6366f1" />
    </BarChart>
  </ResponsiveContainer>
</div>
<div className="admin-chart-container">
  <h3>Report Status Overview</h3>

  <ResponsiveContainer width="100%" height={300}>
    <PieChart>
      <Pie
        data={[
          {
            name: "Pending",
            value: adminReportStats?.pendingReports || 0,
          },
          {
            name: "Resolved",
            value: adminReportStats?.resolvedReports || 0,
          },
          {
            name: "Rejected",
            value: adminReportStats?.rejectedReports || 0,
          },
        ]}
        dataKey="value"
        nameKey="name"
        cx="50%"
        cy="50%"
        outerRadius={100}
        label
      >
        <Cell fill="#f59e0b" />
        <Cell fill="#22c55e" />
        <Cell fill="#ef4444" />
      </Pie>

      <Tooltip />
      <Legend />
    </PieChart>
  </ResponsiveContainer>
</div>
<div className="admin-chart-container">
  <h3>Report Type Distribution</h3>

  <ResponsiveContainer width="100%" height={300}>
    <PieChart>
      <Pie
        data={[
          {
            name: "Question Reports",
            value: adminReportStats?.questionReports || 0,
          },
          {
            name: "Answer Reports",
            value: adminReportStats?.answerReports || 0,
          },
        ]}
        dataKey="value"
        nameKey="name"
        cx="50%"
        cy="50%"
        outerRadius={100}
        label
      >
        <Cell fill="#8b5cf6" />
        <Cell fill="#06b6d4" />
      </Pie>

      <Tooltip />
      <Legend />
    </PieChart>
  </ResponsiveContainer>
</div>
    
  </>
)}

<h2>User Management</h2>
<p>View, search, and manage registered users.</p>

<div className="admin-report-filters">
  <input
  type="date"
  value={adminActivityStartDate}
  onChange={(event) =>
    setAdminActivityStartDate(event.target.value)
  }
  title="Start Date"
/>

<input
  type="date"
  value={adminActivityEndDate}
  onChange={(event) =>
    setAdminActivityEndDate(event.target.value)
  }
  title="End Date"
/>
<button
  type="button"
  className="outline-button"
  onClick={() => {
    setAdminActivityStartDate("");
    setAdminActivityEndDate("");
    setAdminActivitySearch("");
    setAdminActivityFilter("ALL");
    setAdminActivityPage(0);
  }}
>
  Clear Filters
</button>
  <input
  type="email"
  value={adminActivitySearch}
  onChange={(event) =>
    setAdminActivitySearch(event.target.value)
  }
  placeholder="Search by admin email"
/>

<button
  type="button"
  className="outline-button"
  onClick={fetchAdminActivityLogs}
>
  Search
</button>
  <input
    type="text"
    placeholder="Search by name or email..."
    value={adminUserSearch}
    onChange={(event) => setAdminUserSearch(event.target.value)}
  />

  <button
    type="button"
    onClick={fetchAdminUsers}
  >
    Search
  </button>

  <select
    value={adminUserFilter}
    onChange={(event) => setAdminUserFilter(event.target.value)}
  >
    <option value="ALL">All Users</option>
    <option value="ACTIVE">Active Users</option>
    <option value="INACTIVE">Inactive Users</option>
    <option value="USER">Normal Users</option>
    <option value="ADMIN">Admin Users</option>
  </select>

  <button
    type="button"
    onClick={() => {
      setAdminUserSearch("");
      setAdminUserFilter("ALL");
      fetchAdminUsers();
    }}
  >
    Refresh Users
  </button>
</div>

{adminUsersLoading && <p>Loading users...</p>}

{adminUsersError && <p>{adminUsersError}</p>}

{!adminUsersLoading && adminUsers.length === 0 && (
  <p>No users found.</p>
)}

<div className="admin-report-list">
  {adminUsers.map((user) => (
    <article className="admin-report-card" key={user.id}>
      <h3>{user.name}</h3>

      <p><strong>User ID:</strong> {user.id}</p>
      <p><strong>Email:</strong> {user.email}</p>
      <p><strong>Role:</strong> {user.role}</p>
      <p><strong>Reputation:</strong> {user.reputation}</p>
      <p>
        <strong>Status:</strong>{" "}
        {user.active ? "Active" : "Inactive"}
      </p>

      <div className="admin-report-actions">
        <button
          type="button"
          onClick={() =>
            updateAdminUserStatus(user.id, !user.active)
          }
        >
          {user.active ? "Deactivate" : "Activate"}
        </button>

        <button
          type="button"
          disabled={user.email === loggedInUser?.email}
          onClick={() =>
            updateAdminUserRole(
              user.id,
              user.role === "ADMIN" ? "USER" : "ADMIN"
            )
          }
        >
          {user.role === "ADMIN" ? "Make User" : "Make Admin"}
        </button>
      </div>
    </article>
  ))}
</div>
<h2>Admin Activity Logs</h2>
<p>Track important administrative activities on QueryHub.</p>

<div className="admin-report-filters">
    <input
    type="date"
    value={adminActivityStartDate}
    onChange={(event) =>
      setAdminActivityStartDate(event.target.value)
    }
    title="Start Date"
  />

  <input
    type="date"
    value={adminActivityEndDate}
    onChange={(event) =>
      setAdminActivityEndDate(event.target.value)
    }
    title="End Date"
  />

  <button
    type="button"
    className="outline-button"
    onClick={() => {
      setAdminActivityStartDate("");
      setAdminActivityEndDate("");
      setAdminActivitySearch("");
      setAdminActivityFilter("ALL");
      fetchAdminActivityLogs();
    }}
  >
    Clear Filters
  </button>
  <input
    type="email"
    value={adminActivitySearch}
    onChange={(event) =>
      setAdminActivitySearch(event.target.value)
    }
    placeholder="Search by admin email"
  />

  <button
    type="button"
    className="outline-button"
    onClick={fetchAdminActivityLogs}
  >
    Search
  </button>

  <select
    value={adminActivityFilter}
    onChange={(event) =>
      setAdminActivityFilter(event.target.value)
    }
  >
    <option value="ALL">All Activities</option>
    <option value="LOGIN">Login</option>
    <option value="USER_UPDATE">User Update</option>
    <option value="UPDATE_REPORT_STATUS">Report Update</option>
    <option value="DELETE_REPORT">Delete Report</option>
  </select>

  <button
    type="button"
    className="outline-button"
    onClick={() => {
      setAdminActivitySearch("");
      setAdminActivityFilter("ALL");
      fetchAdminActivityLogs();
    }}
  >
    Load Activity Logs
  </button>
    <button
    type="button"
    className="outline-button"
    onClick={exportAdminActivityLogs}
  >
    Export CSV
  </button>
</div>

{adminActivityLogsLoading && (
  <p>Loading activity logs...</p>
)}

{adminActivityLogsError && (
  <p>{adminActivityLogsError}</p>
)}

{!adminActivityLogsLoading &&
  adminActivityLogs.length === 0 && (
    <p>No activity logs found.</p>
  )}

<div className="admin-report-list">
  {paginatedAdminActivityLogs.map((log) => (
    <article className="admin-report-card" key={log.id}>
      <h3>{log.action}</h3>

      <p>
        <strong>Admin:</strong> {log.adminEmail}
      </p>

      <p>
        <strong>Target Type:</strong> {log.targetType || "N/A"}
      </p>

      <p>
        <strong>Target ID:</strong> {log.targetId ?? "N/A"}
      </p>

      <p>
        <strong>Details:</strong> {log.details || "N/A"}
      </p>

      <p>
        <strong>Date:</strong>{" "}
        {log.createdAt
          ? new Date(log.createdAt).toLocaleString()
          : "N/A"}
      </p>
    </article>
  ))}
</div>

{adminActivityLogs.length > 0 && (
  <div className="admin-report-filters">
    <button
      type="button"
      className="outline-button"
      disabled={adminActivityPage === 0}
      onClick={() =>
        setAdminActivityPage((previous) => previous - 1)
      }
    >
      Previous
    </button>

    <span>
      Page {adminActivityPage + 1} of {adminActivityTotalPages}
    </span>

    <button
      type="button"
      className="outline-button"
      disabled={adminActivityPage >= adminActivityTotalPages - 1}
      onClick={() =>
        setAdminActivityPage((previous) => previous + 1)
      }
    >
      Next
    </button>
  </div>
)}

<h2>Report Management</h2>
    <p>Review and manage reported questions and answers.</p>

    <div className="admin-report-filters">
      <button
        type="button"
        onClick={() => {
          setAdminReportFilter("ALL");
          fetchAdminReports();
        }}
      >
        All Reports
      </button>

      <button
        type="button"
        onClick={() => {
          setAdminReportFilter("PENDING");
          fetchAdminReports();
        }}
      >
        Pending
      </button>

      <button
        type="button"
        onClick={() => {
          setAdminReportFilter("RESOLVED");
          fetchAdminReports();
        }}
      >
        Resolved
      </button>

      <button
        type="button"
        onClick={() => {
          setAdminReportFilter("REJECTED");
          fetchAdminReports();
        }}
      >
        Rejected
      </button>
    </div>

    <button
      type="button"
      className="outline-button"
      onClick={fetchAdminReports}
    >
      Refresh Reports
    </button>

    {adminReportsLoading && <p>Loading reports...</p>}

    {adminReportError && <p>{adminReportError}</p>}

    {!adminReportsLoading && adminReports.length === 0 && (
      <p>No reports found.</p>
    )}

    <div className="admin-report-list">
      {adminReports.map((report) => (
        <article className="admin-report-card" key={report.id}>
          <h3>Report #{report.id}</h3>

          <p>
            <strong>Type:</strong> {report.targetType}
          </p>

          <p>
            <strong>Target ID:</strong> {report.targetId}
          </p>

          <p>
            <strong>Reason:</strong> {report.reason}
          </p>

          <p>
            <strong>Status:</strong> {report.status}
          </p>

          <div className="admin-report-actions">
            <button
              type="button"
              onClick={() =>
                updateReportStatus(report.id, "RESOLVED")
              }
            >
              Mark Resolved
            </button>

            <button
              type="button"
              onClick={() =>
                updateReportStatus(report.id, "REJECTED")
              }
            >
              Reject
            </button>

            <button
              type="button"
              onClick={() => deleteAdminReport(report.id)}
            >
              Delete
            </button>
          </div>
        </article>
      ))}
    </div>
  </section>
)}
{showDraftRecovery && recoveryDraft && (
  <div
    className="edit-history-overlay"
    style={{
      position: "fixed",
      inset: 0,
      background: "rgba(0, 0, 0, 0.75)",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      zIndex: 10000,
    }}
  >
    <section
      className="edit-history-modal"
      style={{ maxWidth: "500px", width: "90%" }}
    >
      <h2>Continue where you left off?</h2>

      <p>You have an unfinished saved question:</p>

      <h3>{recoveryDraft.title}</h3>

      <p>{recoveryDraft.description}</p>

      <div className="draft-actions">
        <button
          type="button"
          className="nav-button"
          onClick={() => {
            editDraft(recoveryDraft);
            setShowDraftRecovery(false);
          }}
        >
          Continue Writing
        </button>

        <button
          type="button"
          className="outline-button"
          onClick={() => setShowDraftRecovery(false)}
        >
          Not Now
        </button>
      </div>
    </section>
  </div>
)}

{showSavedAnswers && (
  <section className="saved-answers-section" id="saved-answers-section">
    <h2>My Saved Answers</h2>

    <button
      type="button"
      onClick={() => setShowSavedAnswers(false)}
    >
      Close
    </button>

    {answerBookmarkLoading ? (
      <p>Loading saved answers...</p>
    ) : answerBookmarks.length === 0 ? (
      <p>You haven't bookmarked any answers yet.</p>
    ) : (
      answerBookmarks.map((bookmark) => (
        <div className="saved-answer-card" key={bookmark.id}>
          <h3>
            {bookmark.answer?.question?.title || "Saved Answer"}
          </h3>

          <p>{bookmark.answer?.content}</p>

          <button
            type="button"
            onClick={() =>
              toggleAnswerBookmark(bookmark.answer.id)
            }
          >
            Remove Bookmark
          </button>
        </div>
      ))
    )}
  </section>
)}

</main>


      <footer>
        <a className="brand footer-brand" href="#home">
          <span className="brand-icon">Q</span>
          <span>QueryHub</span>
        </a>

        <p>Built for curious minds. Powered by community.</p>
      </footer>

      {showForm && (
        <div
          className="modal-backdrop"
          onClick={() => {
            if (!submitting) {
              setShowForm(false);
              setFormMessage("");
            }
          }}
        >
          <div
            className="question-modal"
            onClick={(event) => event.stopPropagation()}
          >
            <button
              type="button"
              className="close-button"
              onClick={() => {
                setShowForm(false);
                setFormMessage("");
              }}
              aria-label="Close"
              disabled={submitting}
            >
              ×
            </button>

            <span className="eyebrow">ASK THE COMMUNITY</span>
            <h2>Post a question</h2>
            <p>Explain what you want to learn. Be clear and specific.</p>

            <form onSubmit={addQuestion}>
              <label htmlFor="question-user-id">Your existing user ID</label>
              <input
                id="question-user-id"
                type="number"
                min="1"
                value={userId}
                onChange={(event) => {
                  setUserId(event.target.value);
                  localStorage.setItem(
                    "queryhubUserId",
                    event.target.value
                  );
                }}
                placeholder="Enter the ID returned by login"
                required
              />

              <label htmlFor="question-title">Question title</label>
              <input
                id="question-title"
                value={newTitle}
                onChange={(event) => setNewTitle(event.target.value)}
                maxLength={150}
                placeholder="e.g. What is polymorphism in Java?"
                required
              />

              <label htmlFor="question-description">Description</label>
              <textarea
                id="question-description"
                value={newDescription}
                onChange={(event) =>
                  setNewDescription(event.target.value)
                }
                maxLength={5000}
                placeholder="Add details about your doubt..."
                rows={5}
                required
              />

              <label htmlFor="question-category">Category</label>
              <select
                id="question-category"
                value={newCategory}
                onChange={(event) => setNewCategory(event.target.value)}
                required
              >
                {categories
                  .filter((item) => item !== "All")
                  .map((item) => (
                    <option key={item} value={item}>
                      {item}
                    </option>
                  ))}
                <option value="General">General</option>
              </select>

              <label htmlFor="question-tags">Tags (optional)</label>
              <input
                id="question-tags"
                value={newTags}
                onChange={(event) => setNewTags(event.target.value)}
                maxLength={200}
                placeholder="e.g. Java, OOP, beginner"
              />
              <label htmlFor="question-expiry">
  Question expiry date (optional)
</label>
<input
  id="question-expiry"
  type="datetime-local"
  value={expiryDate}
  min={new Date(
  Date.now() - new Date().getTimezoneOffset() * 60000
).toISOString().slice(0, 16)}
  onChange={(event) => setExpiryDate(event.target.value)}
/>
<p className="form-hint">
  Your question will automatically close after this date and time.
  Leave empty if you don't want it to expire.
</p>

              {formMessage && (
                <p role="alert" className="form-message">
                  {formMessage}
                </p>
              )}
              {autoSaving && (
  <p className="form-message">Saving draft automatically...</p>
)}

{!autoSaving && autoSaveMessage && (
  <p className="form-message">{autoSaveMessage}</p>
)}

              <button
                type="submit"
                className="nav-button submit-button"
                disabled={submitting}
              >
                {submitting ? "Posting..." : "Post question"}
              </button>
              <button
  type="button"
  className="outline-button"
  onClick={saveDraft}
  disabled={submitting}
>
  {submitting
  ? "Saving..."
  : editingDraftId !== null
    ? "Update Draft"
    : "Save as Draft"}
</button>
            </form>
          </div>
        </div>
      )}

      {showProfile && (
        <div
          className="modal-backdrop"
          onClick={() => setShowProfile(false)}
        >
          <div
            className="question-modal profile-modal"
            onClick={(event) => event.stopPropagation()}
          >
            <button
              type="button"
              className="close-button"
              onClick={() => setShowProfile(false)}
              aria-label="Close profile"
            >
              ×
            </button>

            <span className="eyebrow">YOUR QUERYHUB ACCOUNT</span>
            <h2>My Profile</h2>

            {profileLoading ? (
              <p>Loading your profile...</p>
            ) : profileError ? (
              <div role="alert">
                <p>{profileError}</p>
                <button
                  type="button"
                  className="outline-button"
                  onClick={openProfile}
                >
                  Try again
                </button>
              </div>
            ) : profile ? (
              <>
                <div className="profile-avatar">
                  {(profile.name || profile.email || "U")
                    .charAt(0)
                    .toUpperCase()}
                </div>

                <h3 className="profile-name">
                  {profile.name || "Community member"}
                </h3>

                <p className="profile-email">
                  {profile.email || "No email available"}
                </p>

                <div className="profile-stats">
                  <div>
                    <strong>{profile.reputation ?? 0}</strong>
                    <span>Reputation</span>
                  </div>

                  <div>
                    <strong>{profile.role || "USER"}</strong>
                    <span>Role</span>
                  </div>

                  <div>
                    <strong>{myQuestions.length}</strong>
                    <span>My Questions</span>
                  </div>

                  <div>
                    <strong>{myAnswers.length}</strong>
                    <span>My Answers</span>
                  </div>

                  <div>
                    <strong>
                      {myAnswers.filter((answer) => answer.accepted).length}
                    </strong>
                    <span>Accepted Answers</span>
                  </div>
                </div>
                <div className="profile-badges">
  <h3>🏆 My Badges & Achievements</h3>

  {badgeLoading ? (
    <p>Loading badges...</p>
  ) : badgeError ? (
    <p role="alert">{badgeError}</p>
  ) : badges.length === 0 ? (
    <p>No badges earned yet. Keep contributing!</p>
  ) : (
    <div className="badge-list">
      {badges.map((badge) => (
        <div className="badge-card" key={badge.id}>
          <span className="badge-icon">{badge.icon}</span>
          <strong>{badge.name}</strong>
          <span>{badge.description}</span>
        </div>
      ))}
    </div>
  )}
</div>

                <div className="profile-activity">
                  <h3>My Questions</h3>

                  {activityLoading ? (
                    <p>Loading your activity...</p>
                  ) : activityError ? (
                    <p role="alert">{activityError}</p>
                  ) : myQuestions.length === 0 ? (
                    <p>You haven't posted any questions yet.</p>
                  ) : (
                    <div className="profile-activity-list">
                      {myQuestions.map((question) => (
                        <div
                          className="profile-activity-item"
                          key={question.id}
                        >
                          <strong>{question.title}</strong>
                          <span>
                            Category: {question.category || "General"}
                          </span>
                          <span>Status: {question.status || "OPEN"}</span>
                        </div>
                      ))}
                    </div>
                  )}

                  <h3>My Answers</h3>

                  {activityLoading ? (
                    <p>Loading your answers...</p>
                  ) : activityError ? (
                    <p role="alert">{activityError}</p>
                  ) : myAnswers.length === 0 ? (
                    <p>You haven't posted any answers yet.</p>
                  ) : (
                    <div className="profile-activity-list">
                      {myAnswers.map((answer) => (
                        <div
                          className="profile-activity-item"
                          key={answer.id}
                        >
                          <p>{answer.content}</p>
                          <span>Upvotes: {answer.upvotes ?? 0}</span>
                          <span>Downvotes: {answer.downvotes ?? 0}</span>

                          {answer.accepted && (
                            <span className="accepted-label">
                              ✓ Accepted answer
                            </span>
                          )}
                        </div>
                      ))}
                    </div>
                  )}
                </div>

                <p className="profile-note">
                  Keep asking questions, sharing answers, and helping the
                  community grow!
                </p>
              </>
            ) : null}
          </div>
        </div>
      )}
      {showReportForm && (
  <div
    className="modal-backdrop"
    onClick={() => setShowReportForm(false)}
  >
    <div
      className="question-modal"
      onClick={(event) => event.stopPropagation()}
    >
      <button
        type="button"
        className="close-button"
        onClick={() => setShowReportForm(false)}
      >
        ×
      </button>

      <span className="eyebrow">COMMUNITY SAFETY</span>
      <h2>Report {reportTargetType}</h2>
      <p>Tell us why you are reporting this content.</p>

      <form
        onSubmit={(event) => {
          event.preventDefault();
          submitReport();
        }}
      >
        <label htmlFor="report-reason">Reason for reporting</label>

        <select
          id="report-reason"
          value={reportReason}
          onChange={(event) => setReportReason(event.target.value)}
          required
        >
          <option value="">Select a reason</option>
          <option value="Spam">Spam</option>
          <option value="Harassment or abusive content">
            Harassment or abusive content
          </option>
          <option value="Inappropriate content">
            Inappropriate content
          </option>
          <option value="Misleading information">
            Misleading information
          </option>
          <option value="Other">Other</option>
        </select>

        {reportError && (
          <p role="alert" className="form-message">
            {reportError}
          </p>
        )}

        <button
          type="submit"
          className="nav-button submit-button"
          disabled={reportLoading}
        >
          {reportLoading ? "Submitting..." : "Submit Report"}
        </button>
        <button
  type="button"
  className="outline-button"
  onClick={saveDraft}
  disabled={submitting}
>
  {submitting ? "Saving..." : "Save as Draft"}
</button>
      </form>
    </div>
  </div>
)}
            {showAuth && (
        <div
          className="modal-backdrop"
          onClick={() => setShowAuth(false)}
        >
          <div
            className="question-modal"
            onClick={(event) => event.stopPropagation()}
          >
            <button
              type="button"
              className="close-button"
              onClick={() => setShowAuth(false)}
            >
              ×
            </button>

            <span className="eyebrow">WELCOME TO QUERYHUB</span>
            <h2>{authMode === "login" ? "Login" : "Create Account"}</h2>

            <form onSubmit={handleAuth}>
              {authMode === "register" && (
                <div className="form-group">
                  <label htmlFor="auth-name">Name</label>
                  <input
                    id="auth-name"
                    value={authName}
                    onChange={(event) => setAuthName(event.target.value)}
                    placeholder="Enter your name"
                  />
                </div>
              )}

              <div className="form-group">
                <label htmlFor="auth-email">Email</label>
                <input
                  id="auth-email"
                  type="email"
                  value={authEmail}
                  onChange={(event) => setAuthEmail(event.target.value)}
                  placeholder="Enter your email"
                  required
                />
              </div>

              <div className="form-group">
                <label htmlFor="auth-password">Password</label>
                <input
                  id="auth-password"
                  type="password"
                  value={authPassword}
                  onChange={(event) => setAuthPassword(event.target.value)}
                  placeholder="Enter your password"
                  required
                />
              </div>

              {authError && (
                <p role="alert" className="form-message">
                  {authError}
                </p>
              )}

              <button
                type="submit"
                className="nav-button submit-button"
                disabled={authLoading}
              >
                {authLoading
                  ? "Please wait..."
                  : authMode === "login"
                    ? "Login"
                    : "Register"}
              </button>
            </form>

            <p>
              {authMode === "login"
                ? "Don't have an account?"
                : "Already have an account?"}
              {" "}
              <button
                type="button"
                className="outline-button"
                onClick={() => {
                  setAuthMode(authMode === "login" ? "register" : "login");
                  setAuthError("");
                }}
              >
                {authMode === "login" ? "Register" : "Login"}
              </button>
            </p>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;