package com.amio.taskmanagement.controller;

import com.amio.taskmanagement.model.dto.TaskReportDTO;
import com.amio.taskmanagement.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskReportControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskReportController taskReportController;

    @Test
    public void testGenerateTaskCompletionReport() {
        // Arrange
        Long teamMemberId = 1L;
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);

        List<TaskReportDTO> mockTaskReports = Collections.singletonList(new TaskReportDTO(5, 3.5));
        when(taskService.generateTaskCompletionReport(teamMemberId, startDate, endDate))
                .thenReturn(mockTaskReports);

        // Act
        ResponseEntity<List<TaskReportDTO>> response = taskReportController.generateTaskCompletionReport(teamMemberId, startDate, endDate);

        // Assert
        verify(taskService).generateTaskCompletionReport(teamMemberId, startDate, endDate);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockTaskReports);
    }
}
