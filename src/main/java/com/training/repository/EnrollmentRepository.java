package com.training.repository;

import com.training.model.Enrollment;
import com.training.model.Participant;
import com.training.model.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    List<Enrollment> findByParticipant(Participant participant);
    
    List<Enrollment> findByTrainingProgram(TrainingProgram trainingProgram);
    
    List<Enrollment> findByStatus(Enrollment.EnrollmentStatus status);
    
    Optional<Enrollment> findByParticipantAndTrainingProgram(Participant participant, TrainingProgram trainingProgram);
    
    List<Enrollment> findByFeedbackSubmitted(Boolean feedbackSubmitted);
    
    long countByTrainingProgram(TrainingProgram trainingProgram);
}
