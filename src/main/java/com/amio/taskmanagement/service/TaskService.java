package com.amio.taskmanagement.service;

import com.amio.taskmanagement.model.dto.TaskReportDTO;
import com.amio.taskmanagement.model.entity.Task;
import com.amio.taskmanagement.model.dto.TaskDTO;
import com.amio.taskmanagement.model.enums.Status;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    Task createTask(TaskDTO taskDTO);

    Task getTask(Long taskId);

    Task assignTaskToTeamMember(Long taskId, Long teamMemberId);

    Task updateTaskStatus(Long taskId, Status newStatus);

    Task markTaskAsComplete(Long taskId);

    List<TaskReportDTO> generateTaskCompletionReport(Long teamMemberId, LocalDate startDate,
                                                                          LocalDate endDate);
}
