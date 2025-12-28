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

import com.training.model.Schedule;
import com.training.model.TrainingProgram;
import com.training.service.ScheduleService;
import com.training.service.TrainingProgramService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ScheduleController {
	@Autowired
	ScheduleService scheduleService;
	@Autowired
	TrainingProgramService trainingProgramService;

	@PostMapping
	public ResponseEntity<Schedule> createSchedule(@Valid @RequestBody Schedule schedule) {
		Schedule created = scheduleService.createSchedule(schedule);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
		Schedule schedule = scheduleService.getScheduleById(id);
		return ResponseEntity.ok(schedule);
	}

	@GetMapping("/program/{programId}")
	public ResponseEntity<List<Schedule>> getSchedulesByProgram(@PathVariable Long programId) {
		TrainingProgram program = trainingProgramService.getTrainingProgramById(programId);
		List<Schedule> schedules = scheduleService.getSchedulesByProgram(program);
		return ResponseEntity.ok(schedules);
	}

	@GetMapping("/upcoming")
	public ResponseEntity<List<Schedule>> getUpcomingSessions() {
		List<Schedule> schedules = scheduleService.getUpcomingSessions();
		return ResponseEntity.ok(schedules);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @Valid @RequestBody Schedule schedule) {
		Schedule updated = scheduleService.updateSchedule(id, schedule);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
		scheduleService.deleteSchedule(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/send-reminders")
	public ResponseEntity<String> sendSessionReminders() {
		scheduleService.sendSessionReminders();
		return ResponseEntity.ok("Session reminders sent successfully");
	}
}
