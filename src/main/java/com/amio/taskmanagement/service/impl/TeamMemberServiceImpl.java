package com.amio.taskmanagement.service.impl;


import com.amio.taskmanagement.exception.ResourceAlreadyExistsException;
import com.amio.taskmanagement.model.dto.TeamMemberDTO;
import com.amio.taskmanagement.model.entity.TeamMember;
import com.amio.taskmanagement.repository.TeamMemberRepository;
import com.amio.taskmanagement.service.TeamMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    private static final String TEAM_MEMBER_ALREADY_EXIST = "Team member with the given name already exists";

    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    public TeamMemberServiceImpl(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }


    @Override
    public TeamMember createTeamMember(TeamMemberDTO teamMemberDTO) {
        if (teamMemberRepository.findByName(teamMemberDTO.name()).isPresent()) {
            log.warn(TEAM_MEMBER_ALREADY_EXIST + ": {}", teamMemberDTO.name());
            throw new ResourceAlreadyExistsException((TEAM_MEMBER_ALREADY_EXIST + teamMemberDTO.name()));
        }
        log.info("Creating new team member: {}", teamMemberDTO.name());
        return teamMemberRepository.save(new TeamMember(teamMemberDTO.name()));
    }
}
