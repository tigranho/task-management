package com.amio.taskmanagement.controller;

import com.amio.taskmanagement.model.dto.TeamMemberDTO;
import com.amio.taskmanagement.model.entity.TeamMember;
import com.amio.taskmanagement.service.TeamMemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/team-member")
@RequiredArgsConstructor
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    @Operation(summary = "Create team member")
    @PostMapping("/create")
    public ResponseEntity<TeamMember> createTask(@RequestBody @Valid TeamMemberDTO teamMember) {
        return new ResponseEntity<>(teamMemberService.createTeamMember(teamMember), HttpStatus.CREATED);
    }
}
