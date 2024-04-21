package com.amio.taskmanagement.model.entity;


import com.amio.taskmanagement.model.enums.Priority;
import com.amio.taskmanagement.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task extends BaseEntity {

    private String title;
    private String description;
    private LocalDate dueDate;
    private LocalDate startDate;
    private LocalDate completionDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "team_member_id")
    private TeamMember assignedTo;


}
