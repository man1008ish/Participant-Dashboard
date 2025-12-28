package com.training.repository;

import com.training.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    
    Optional<Participant> findByEmail(String email);
    
    List<Participant> findByStatus(Participant.ParticipantStatus status);
    
    List<Participant> findByDepartment(String department);
    
    boolean existsByEmail(String email);
}
