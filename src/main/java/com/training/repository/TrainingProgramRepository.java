package com.training.repository;

import com.training.model.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {
    
    List<TrainingProgram> findByStatus(TrainingProgram.ProgramStatus status);
    
    List<TrainingProgram> findByStartDateBetween(LocalDateTime start, LocalDateTime end);
    
    List<TrainingProgram> findByTrainerName(String trainerName);
}
