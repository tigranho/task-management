package com.amio.taskmanagement.controller;


import com.amio.taskmanagement.model.entity.Task;
import com.amio.taskmanagement.model.dto.TaskDTO;
import com.amio.taskmanagement.model.enums.Status;
import com.amio.taskmanagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Create user task")
    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskDTO taskDTO) {
        return new ResponseEntity<>(taskService.createTask(taskDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get user task details")
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskDetails(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok(taskService.getTask(taskId));
    }

    @Operation(summary = "Assign user task to teamMember")
    @PutMapping("/{taskId}/assign/{teamMemberId}")
    public ResponseEntity<Task> assignTaskToTeamMember(
            @PathVariable("taskId") Long taskId,
            @PathVariable("teamMemberId") Long teamMemberId
    ) {
        return ResponseEntity.ok(taskService.assignTaskToTeamMember(taskId, teamMemberId));
    }

    @Operation(summary = "Update user task status")
    @PutMapping("/{taskId}/status/{status}")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable("taskId") Long taskId,
            @PathVariable("status") Status status
    ) {
        return ResponseEntity.ok(taskService.updateTaskStatus(taskId, status));
    }

    @Operation(summary = "Mark as Complete user task")
    @PutMapping("/{taskId}/complete")
    public ResponseEntity<Task> markTaskAsComplete(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok(taskService.markTaskAsComplete(taskId));
    }

}



