package com.amio.taskmanagement.repository;


import com.amio.taskmanagement.model.entity.Task;
import com.amio.taskmanagement.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedToIdAndCompletionDateBetweenAndStatus(
            Long teamMemberId, LocalDate startDate, LocalDate endDate, Status status);
}
