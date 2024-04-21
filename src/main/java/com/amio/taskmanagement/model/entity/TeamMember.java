package com.amio.taskmanagement.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "team_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember extends BaseEntity {
    private String name;

    @Override
    public String toString() {
        return "TeamMember[id=" + getId() + ", name=" + getName() + "]";
    }

}
