package com.training.repository;

import com.training.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    List<Feedback> findByEnrollmentId(Long enrollmentId);
    
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.enrollment.trainingProgram.id = :programId")
    Double getAverageRatingByProgramId(Long programId);
    
    @Query("SELECT AVG(f.trainerRating) FROM Feedback f WHERE f.enrollment.trainingProgram.id = :programId")
    Double getAverageTrainerRatingByProgramId(Long programId);
}
