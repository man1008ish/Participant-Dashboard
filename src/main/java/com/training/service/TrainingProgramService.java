package com.training.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.training.model.TrainingProgram;
import com.training.repository.TrainingProgramRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingProgramService {
	@Autowired
	TrainingProgramRepository trainingProgramRepository;

	@Transactional
	public TrainingProgram createTrainingProgram(TrainingProgram program) {
		TrainingProgram savedProgram = trainingProgramRepository.save(program);
		// log.info("Training program created: {}", savedProgram.getName());
		return savedProgram;
	}

	@Transactional(readOnly = true)
	public TrainingProgram getTrainingProgramById(Long id) {
		return trainingProgramRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Training program not found with id: " + id));
	}

	@Transactional(readOnly = true)
	public List<TrainingProgram> getAllTrainingPrograms() {
		return trainingProgramRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<TrainingProgram> getTrainingProgramsByStatus(TrainingProgram.ProgramStatus status) {
		return trainingProgramRepository.findByStatus(status);
	}

	@Transactional(readOnly = true)
	public List<TrainingProgram> getUpcomingPrograms() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime futureDate = now.plusMonths(3);
		return trainingProgramRepository.findByStartDateBetween(now, futureDate);
	}

	@Transactional
	public TrainingProgram updateTrainingProgram(Long id, TrainingProgram updatedProgram) {
		TrainingProgram program = getTrainingProgramById(id);

		program.setName(updatedProgram.getName());
		program.setDescription(updatedProgram.getDescription());
		program.setTrainerName(updatedProgram.getTrainerName());
		program.setDurationHours(updatedProgram.getDurationHours());
		program.setStartDate(updatedProgram.getStartDate());
		program.setEndDate(updatedProgram.getEndDate());
		program.setMaxParticipants(updatedProgram.getMaxParticipants());
		program.setStatus(updatedProgram.getStatus());

		TrainingProgram saved = trainingProgramRepository.save(program);
		// log.info("Training program updated: {}", saved.getName());
		return saved;
	}

	@Transactional
	public void deleteTrainingProgram(Long id) {
		trainingProgramRepository.deleteById(id);
		// log.info("Training program deleted with id: {}", id);
	}
}
