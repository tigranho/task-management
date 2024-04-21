package com.amio.taskmanagement.model.dto;

import com.amio.taskmanagement.model.enums.Priority;

import java.time.LocalDate;

public record TaskDTO(
        String title,
        String description,
        LocalDate dueDate,
        Priority priority,
        Long teamMemberId
) {
}
