package com.amio.taskmanagement.controller;

import com.amio.taskmanagement.model.dto.TaskReportDTO;
import com.amio.taskmanagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class TaskReportController {

    private final TaskService taskService;

    @Operation(summary = "Generate user task completion report")
    @GetMapping("/task-completion")
    public ResponseEntity<List<TaskReportDTO>> generateTaskCompletionReport(
            @RequestParam("teamMemberId") Long teamMemberId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok(taskService.generateTaskCompletionReport(teamMemberId, startDate,
                endDate));
    }


}
