package com.training.repository;

import com.training.model.Schedule;
import com.training.model.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    
    List<Schedule> findByTrainingProgram(TrainingProgram trainingProgram);
    
    List<Schedule> findBySessionDateBetween(LocalDateTime start, LocalDateTime end);
    
    List<Schedule> findByStatus(Schedule.SessionStatus status);
}
