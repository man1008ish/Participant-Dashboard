package com.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.model.TrainingProgram;
import com.training.service.TrainingProgramService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TrainingProgramController {
	@Autowired
	TrainingProgramService trainingProgramService;

	@PostMapping
	public ResponseEntity<TrainingProgram> createProgram(@Valid @RequestBody TrainingProgram program) {
		TrainingProgram created = trainingProgramService.createTrainingProgram(program);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TrainingProgram> getProgramById(@PathVariable Long id) {
		TrainingProgram program = trainingProgramService.getTrainingProgramById(id);
		return ResponseEntity.ok(program);
	}

	@GetMapping
	public ResponseEntity<List<TrainingProgram>> getAllPrograms() {
		List<TrainingProgram> programs = trainingProgramService.getAllTrainingPrograms();
		return ResponseEntity.ok(programs);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<TrainingProgram>> getProgramsByStatus(@PathVariable String status) {
		TrainingProgram.ProgramStatus programStatus = TrainingProgram.ProgramStatus.valueOf(status.toUpperCase());
		List<TrainingProgram> programs = trainingProgramService.getTrainingProgramsByStatus(programStatus);
		return ResponseEntity.ok(programs);
	}

	@GetMapping("/upcoming")
	public ResponseEntity<List<TrainingProgram>> getUpcomingPrograms() {
		List<TrainingProgram> programs = trainingProgramService.getUpcomingPrograms();
		return ResponseEntity.ok(programs);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TrainingProgram> updateProgram(@PathVariable Long id,
			@Valid @RequestBody TrainingProgram program) {
		TrainingProgram updated = trainingProgramService.updateTrainingProgram(id, program);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
		trainingProgramService.deleteTrainingProgram(id);
		return ResponseEntity.noContent().build();
	}
}
