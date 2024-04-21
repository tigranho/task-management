package com.amio.taskmanagement.service.impl;


import com.amio.taskmanagement.exception.NotFoundException;
import com.amio.taskmanagement.model.dto.TaskReportDTO;
import com.amio.taskmanagement.model.entity.Task;
import com.amio.taskmanagement.model.dto.TaskDTO;
import com.amio.taskmanagement.model.entity.TeamMember;
import com.amio.taskmanagement.model.enums.Status;
import com.amio.taskmanagement.repository.TaskRepository;
import com.amio.taskmanagement.repository.TeamMemberRepository;
import com.amio.taskmanagement.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private static final String TASK_NOT_FOUND_BY_ID = "Task not found with ID: ";
    private static final String TEAM_MEMBER_NOT_FOUND_BY_ID = "TeamMember not found with ID: ";
    private static final long ZERO = 0;

    private final TaskRepository taskRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TeamMemberRepository teamMemberRepository) {
        this.taskRepository = taskRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    public Task createTask(TaskDTO taskDTO) {
        Long teamMemberId = taskDTO.teamMemberId();
        TeamMember teamMember = null;

        if (teamMemberId != null) {
            teamMember = teamMemberRepository.findById(taskDTO.teamMemberId()).orElse(null);
        }

        Task newTask = new Task(taskDTO.title(), taskDTO.description(), taskDTO.dueDate(), null,
                null,
                taskDTO.priority(),
                Status.TODO, teamMember);

        log.info("Creating new task: {}", newTask);
        return taskRepository.save(newTask);
    }

    @Override
    public Task getTask(Long taskId) {
        log.info("Fetching task with ID: {}", taskId);
        return taskRepository.findById(taskId).orElseThrow(
                () -> new NotFoundException(TASK_NOT_FOUND_BY_ID + taskId));
    }

    @Override
    public Task assignTaskToTeamMember(Long taskId, Long teamMemberId) {
        log.info("Assigning task with ID: {} to team member with ID: {}", taskId, teamMemberId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(TASK_NOT_FOUND_BY_ID + taskId));

        TeamMember teamMember = teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new NotFoundException(TEAM_MEMBER_NOT_FOUND_BY_ID + teamMemberId));

        task.setStartDate(LocalDate.now());
        task.setAssignedTo(teamMember);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTaskStatus(Long taskId, Status newStatus) {
        log.info("Updating status of task with ID: {} to: {}", taskId, newStatus);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException(TASK_NOT_FOUND_BY_ID + taskId));

        task.setStatus(newStatus);
        task.setModifyDate(LocalDate.now());
        return taskRepository.save(task);
    }


    public Task markTaskAsComplete(Long taskId) {
        log.info("Marking task with ID: {} as complete", taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException(TASK_NOT_FOUND_BY_ID + taskId));

        task.setStatus(Status.DONE);
        task.setCompletionDate(LocalDate.now());
        return taskRepository.save(task);
    }


    @Override
    public List<TaskReportDTO> generateTaskCompletionReport(Long teamMemberId, LocalDate startDate, LocalDate endDate) {
        log.info("Generating task completion report for team member with ID: {} between {} and {}", teamMemberId, startDate, endDate);
        List<Task> tasksCompletedByTeamMember = taskRepository.findByAssignedToIdAndCompletionDateBetweenAndStatus(
                teamMemberId, startDate, endDate, Status.DONE);

        int numberOfTasksCompleted = tasksCompletedByTeamMember.size();
        double averageTimeToCompleteTasks = calculateAverageTimeToCompleteTasks(tasksCompletedByTeamMember);

        return Collections.singletonList(new TaskReportDTO(numberOfTasksCompleted, averageTimeToCompleteTasks));
    }

    private double calculateAverageTimeToCompleteTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return ZERO;
        }

        long totalDaysToComplete = ZERO;
        for (Task task : tasks) {
            LocalDate startDate = task.getStartDate();
            LocalDate completionDate = task.getCompletionDate();

            if (startDate == null) {
                return ZERO;
            }

            long daysToComplete = Duration.between(startDate.atStartOfDay(), completionDate.atStartOfDay()).toDays();
            totalDaysToComplete += daysToComplete;
        }

        return (double) totalDaysToComplete / tasks.size();
    }

}

