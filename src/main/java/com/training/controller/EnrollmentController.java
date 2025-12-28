package com.training.controller;

import java.util.List;
import java.util.Map;

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

import com.training.model.Enrollment;
import com.training.model.Participant;
import com.training.model.TrainingProgram;
import com.training.service.EnrollmentService;
import com.training.service.ParticipantService;
import com.training.service.TrainingProgramService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EnrollmentController {
	@Autowired
	EnrollmentService enrollmentService;
	@Autowired
	ParticipantService participantService;
	@Autowired
		TrainingProgramService trainingProgramService;

	@PostMapping
	public ResponseEntity<Enrollment> enrollParticipant(@RequestBody Map<String, Long> request) {
		Long participantId = request.get("participantId");
		Long programId = request.get("programId");

		Participant participant = participantService.getParticipantById(participantId);
		TrainingProgram program = trainingProgramService.getTrainingProgramById(programId);

		Enrollment enrollment = enrollmentService.enrollParticipant(participant, program);
		return ResponseEntity.status(HttpStatus.CREATED).body(enrollment);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Long id) {
		Enrollment enrollment = enrollmentService.getEnrollmentById(id);
		return ResponseEntity.ok(enrollment);
	}

	@GetMapping("/participant/{participantId}")
	public ResponseEntity<List<Enrollment>> getEnrollmentsByParticipant(@PathVariable Long participantId) {
		Participant participant = participantService.getParticipantById(participantId);
		List<Enrollment> enrollments = enrollmentService.getEnrollmentsByParticipant(participant);
		return ResponseEntity.ok(enrollments);
	}

	@GetMapping("/program/{programId}")
	public ResponseEntity<List<Enrollment>> getEnrollmentsByProgram(@PathVariable Long programId) {
		TrainingProgram program = trainingProgramService.getTrainingProgramById(programId);
		List<Enrollment> enrollments = enrollmentService.getEnrollmentsByProgram(program);
		return ResponseEntity.ok(enrollments);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<Enrollment> updateEnrollmentStatus(@PathVariable Long id,
			@RequestBody Map<String, String> request) {
		String status = request.get("status");
		Enrollment.EnrollmentStatus enrollmentStatus = Enrollment.EnrollmentStatus.valueOf(status.toUpperCase());

		Enrollment updated = enrollmentService.updateEnrollmentStatus(id, enrollmentStatus);
		return ResponseEntity.ok(updated);
	}

	@PutMapping("/{id}/completion")
	public ResponseEntity<Enrollment> updateCompletionPercentage(@PathVariable Long id,
			@RequestBody Map<String, Double> request) {
		Double percentage = request.get("percentage");
		Enrollment updated = enrollmentService.updateCompletionPercentage(id, percentage);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> withdrawEnrollment(@PathVariable Long id) {
		enrollmentService.withdrawEnrollment(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/pending-feedback")
	public ResponseEntity<List<Enrollment>> getEnrollmentsPendingFeedback() {
		List<Enrollment> enrollments = enrollmentService.getEnrollmentsPendingFeedback();
		return ResponseEntity.ok(enrollments);
	}
}
