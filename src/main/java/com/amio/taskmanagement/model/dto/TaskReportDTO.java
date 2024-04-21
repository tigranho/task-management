package com.amio.taskmanagement.model.dto;

public record TaskReportDTO(
        int numberOfTasksCompleted,
        double averageTimeToCompleteTasks
) {
}
