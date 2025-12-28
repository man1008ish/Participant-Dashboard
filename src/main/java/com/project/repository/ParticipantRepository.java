package com.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
	Optional<Participant> findByEmail(String email);

	List<Participant> findByStatus(String status);

	List<Participant> findByCohort(String cohort);
}