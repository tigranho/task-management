package com.amio.taskmanagement.service;

import com.amio.taskmanagement.exception.NotFoundException;
import com.amio.taskmanagement.model.entity.Task;
import com.amio.taskmanagement.model.dto.TaskDTO;
import com.amio.taskmanagement.model.entity.TeamMember;
import com.amio.taskmanagement.model.enums.Priority;
import com.amio.taskmanagement.repository.TaskRepository;
import com.amio.taskmanagement.repository.TeamMemberRepository;
import com.amio.taskmanagement.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @InjectMocks
    private TaskServiceImpl taskService;


    @Test
    void testCreateTask() {
        TaskDTO taskDTO = new TaskDTO("Sample Title", "Sample Description", LocalDate.now(),
                Priority.MEDIUM, 1L);
        Task createdTask = new Task("Sample Title", "Sample Description", LocalDate.now(), null,
                null, Priority.MEDIUM, null, new TeamMember());
        when(taskRepository.save(any(Task.class))).thenReturn(createdTask);

        Task result = taskService.createTask(taskDTO);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(createdTask);
    }

    @Test
    void testGetTask() {
        Long taskId = 1L;
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task result = taskService.getTask(taskId);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(task);
    }

    @Test
    void testGetTask_NotFound() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTask(taskId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void testAssignTaskToTeamMember() {
        Long taskId = 1L;
        Long teamMemberId = 1L;
        Task task = new Task();
        TeamMember teamMember = new TeamMember();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(teamMemberRepository.findById(teamMemberId)).thenReturn(Optional.of(teamMember));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.assignTaskToTeamMember(taskId, teamMemberId);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(task);
        assertThat(result.getAssignedTo()).isEqualTo(teamMember);
    }


}
