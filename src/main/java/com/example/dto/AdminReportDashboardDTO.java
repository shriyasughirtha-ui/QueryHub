package com.example.dto;

public class AdminReportDashboardDTO {

    private long totalReports;
    private long pendingReports;
    private long resolvedReports;
    private long rejectedReports;
    private long questionReports;
    private long answerReports;

    public AdminReportDashboardDTO(
            long totalReports,
            long pendingReports,
            long resolvedReports,
            long rejectedReports,
            long questionReports,
            long answerReports) {

        this.totalReports = totalReports;
        this.pendingReports = pendingReports;
        this.resolvedReports = resolvedReports;
        this.rejectedReports = rejectedReports;
        this.questionReports = questionReports;
        this.answerReports = answerReports;
    }

    public long getTotalReports() {
        return totalReports;
    }

    public long getPendingReports() {
        return pendingReports;
    }

    public long getResolvedReports() {
        return resolvedReports;
    }

    public long getRejectedReports() {
        return rejectedReports;
    }

    public long getQuestionReports() {
        return questionReports;
    }

    public long getAnswerReports() {
        return answerReports;
    }
}