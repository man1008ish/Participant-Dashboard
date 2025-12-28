package com.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.training.model.Enrollment;
import com.training.model.Feedback;
import com.training.model.Notification;
import com.training.repository.FeedbackRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {
	@Autowired
	FeedbackRepository feedbackRepository;
	@Autowired
	EnrollmentService enrollmentService;
	@Autowired
	NotificationService notificationService;
	@Autowired
	EmailService emailService;

	@Transactional
	public Feedback submitFeedback(Feedback feedback, Long enrollmentId) {
		Enrollment enrollment = enrollmentService.getEnrollmentById(enrollmentId);

		feedback.setEnrollment(enrollment);
		Feedback savedFeedback = feedbackRepository.save(feedback);

		// Mark feedback as submitted in enrollment
		enrollment.setFeedbackSubmitted(true);

		// log.info("Feedback submitted for enrollment id: {}", enrollmentId);

		return savedFeedback;
	}

	@Transactional(readOnly = true)
	public Feedback getFeedbackById(Long id) {
		return feedbackRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
	}

	@Transactional(readOnly = true)
	public List<Feedback> getFeedbackByEnrollment(Long enrollmentId) {
		return feedbackRepository.findByEnrollmentId(enrollmentId);
	}

	@Transactional(readOnly = true)
	public Double getAverageProgramRating(Long programId) {
		return feedbackRepository.getAverageRatingByProgramId(programId);
	}

	@Transactional(readOnly = true)
	public Double getAverageTrainerRating(Long programId) {
		return feedbackRepository.getAverageTrainerRatingByProgramId(programId);
	}

	@Transactional
	public void sendFeedbackRequests() {
		List<Enrollment> pendingFeedback = enrollmentService.getEnrollmentsPendingFeedback();

		for (Enrollment enrollment : pendingFeedback) {
			if (enrollment.getStatus() == Enrollment.EnrollmentStatus.COMPLETED) {
				notificationService.createNotification(enrollment.getParticipant(), "Feedback Request",
						"Please provide feedback for " + enrollment.getTrainingProgram().getName(),
						Notification.NotificationType.FEEDBACK_REQUEST, false);

				emailService.sendFeedbackRequest(enrollment.getParticipant().getEmail(),
						enrollment.getParticipant().getName(), enrollment.getTrainingProgram().getName());
			}
		}

		// log.info("Feedback requests sent to {} participants",
		// pendingFeedback.size());
	}
}
