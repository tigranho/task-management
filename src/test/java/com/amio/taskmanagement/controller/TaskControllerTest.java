package com.amio.taskmanagement.controller;

import com.amio.taskmanagement.model.entity.Task;
import com.amio.taskmanagement.model.dto.TaskDTO;
import com.amio.taskmanagement.model.enums.Priority;
import com.amio.taskmanagement.model.enums.Status;
import com.amio.taskmanagement.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    public void testCreateTask() {
        String title = "Sample Task";
        String description = "This is a sample task description";
        LocalDate dueDate = LocalDate.now().plusDays(7);
        Priority priority = Priority.MEDIUM;
        Long teamMemberId = 1L;

        TaskDTO taskDTO = new TaskDTO(title, description, dueDate, priority, teamMemberId);
        Task task = new Task();

        when(taskService.createTask(any(TaskDTO.class))).thenReturn(task);

        ResponseEntity<Task> responseEntity = taskController.createTask(taskDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isEqualTo(task);
    }

    @Test
    public void testGetTaskDetails() {
        Long taskId = 1L;
        Task task = new Task();

        when(taskService.getTask(anyLong())).thenReturn(task);

        ResponseEntity<Task> responseEntity = taskController.getTaskDetails(taskId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(task);
    }

    @Test
    public void testAssignTaskToTeamMember() {
        Long taskId = 1L;
        Long teamMemberId = 1L;
        Task task = new Task();

        when(taskService.assignTaskToTeamMember(anyLong(), anyLong())).thenReturn(task);

        ResponseEntity<Task> responseEntity = taskController.assignTaskToTeamMember(taskId, teamMemberId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(task);
    }

    @Test
    public void testUpdateTaskStatus() {
        Long taskId = 1L;
        Status status = Status.IN_PROGRESS;
        Task task = new Task();

        when(taskService.updateTaskStatus(anyLong(), any(Status.class))).thenReturn(task);

        ResponseEntity<Task> responseEntity = taskController.updateTaskStatus(taskId, status);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(task);
    }

    @Test
    public void testMarkTaskAsComplete() {
        Long taskId = 1L;
        Task task = new Task();

        when(taskService.markTaskAsComplete(anyLong())).thenReturn(task);

        ResponseEntity<Task> responseEntity = taskController.markTaskAsComplete(taskId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(task);
    }
}
