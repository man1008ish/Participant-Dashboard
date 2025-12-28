package com.training.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.model.Feedback;
import com.training.service.FeedbackService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FeedbackController {
	@Autowired
	FeedbackService feedbackService;

	@PostMapping("/enrollment/{enrollmentId}")
	public ResponseEntity<Feedback> submitFeedback(@PathVariable Long enrollmentId,
			@Valid @RequestBody Feedback feedback) {
		Feedback submitted = feedbackService.submitFeedback(feedback, enrollmentId);
		return ResponseEntity.status(HttpStatus.CREATED).body(submitted);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
		Feedback feedback = feedbackService.getFeedbackById(id);
		return ResponseEntity.ok(feedback);
	}

	@GetMapping("/enrollment/{enrollmentId}")
	public ResponseEntity<List<Feedback>> getFeedbackByEnrollment(@PathVariable Long enrollmentId) {
		List<Feedback> feedbacks = feedbackService.getFeedbackByEnrollment(enrollmentId);
		return ResponseEntity.ok(feedbacks);
	}

	@GetMapping("/program/{programId}/average-rating")
	public ResponseEntity<Map<String, Double>> getAverageProgramRating(@PathVariable Long programId) {
		Double avgRating = feedbackService.getAverageProgramRating(programId);
		return ResponseEntity.ok(Map.of("averageRating", avgRating != null ? avgRating : 0.0));
	}

	@GetMapping("/program/{programId}/trainer-rating")
	public ResponseEntity<Map<String, Double>> getAverageTrainerRating(@PathVariable Long programId) {
		Double avgRating = feedbackService.getAverageTrainerRating(programId);
		return ResponseEntity.ok(Map.of("averageTrainerRating", avgRating != null ? avgRating : 0.0));
	}

	@PostMapping("/send-requests")
	public ResponseEntity<String> sendFeedbackRequests() {
		feedbackService.sendFeedbackRequests();
		return ResponseEntity.ok("Feedback requests sent successfully");
	}
}
