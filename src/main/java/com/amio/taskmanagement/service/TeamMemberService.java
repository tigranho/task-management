package com.amio.taskmanagement.service;


import com.amio.taskmanagement.model.dto.TeamMemberDTO;
import com.amio.taskmanagement.model.entity.TeamMember;

public interface TeamMemberService {
    TeamMember createTeamMember(TeamMemberDTO teamMemberDTO);

}
