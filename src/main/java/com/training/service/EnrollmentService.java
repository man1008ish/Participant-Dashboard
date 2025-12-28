package com.training.service;

	import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.training.model.Enrollment;
import com.training.model.Notification;
import com.training.model.Participant;
import com.training.model.TrainingProgram;
import com.training.repository.EnrollmentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {
	@Autowired
	EnrollmentRepository enrollmentRepository;
	@Autowired
	NotificationService notificationService;
	@Autowired
	EmailService emailService;

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(EnrollmentService.class);

	@Transactional
	public Enrollment enrollParticipant(Participant participant, TrainingProgram trainingProgram) {
		// Check if already enrolled
		if (enrollmentRepository.findByParticipantAndTrainingProgram(participant, trainingProgram).isPresent()) {
			throw new RuntimeException("Participant already enrolled in this program");
		}

		// Check if program has available slots
		long currentEnrollments = enrollmentRepository.countByTrainingProgram(trainingProgram);
		if (trainingProgram.getMaxParticipants() != null
				&& currentEnrollments >= trainingProgram.getMaxParticipants()) {
			throw new RuntimeException("Training program is full");
		}

		// Create enrollment
		Enrollment enrollment = new Enrollment();
		enrollment.setParticipant(participant);
		enrollment.setTrainingProgram(trainingProgram);
		enrollment.setStatus(Enrollment.EnrollmentStatus.ENROLLED);

		Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
		log.info("Participant {} enrolled in program {}", participant.getEmail(), trainingProgram.getName());

		// Send enrollment confirmation notification and email
		notificationService.createNotification(participant, "Enrollment Confirmation",
				"You have been successfully enrolled in " + trainingProgram.getName(),
				Notification.NotificationType.ENROLLMENT_CONFIRMATION, false);

		emailService.sendEnrollmentConfirmation(participant.getEmail(), participant.getName(),
				trainingProgram.getName());

		return savedEnrollment;
	}

	@Transactional(readOnly = true)
	public Enrollment getEnrollmentById(Long id) {
		return enrollmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
	}

	@Transactional(readOnly = true)
	public List<Enrollment> getEnrollmentsByParticipant(Participant participant) {
		return enrollmentRepository.findByParticipant(participant);
	}

	@Transactional(readOnly = true)
	public List<Enrollment> getEnrollmentsByProgram(TrainingProgram trainingProgram) {
		return enrollmentRepository.findByTrainingProgram(trainingProgram);
	}

	@Transactional
	public Enrollment updateEnrollmentStatus(Long enrollmentId, Enrollment.EnrollmentStatus status) {
		Enrollment enrollment = getEnrollmentById(enrollmentId);
		enrollment.setStatus(status);

		Enrollment saved = enrollmentRepository.save(enrollment);
		log.info("Enrollment status updated to {} for enrollment id: {}", status, enrollmentId);

		// Send notification for status change
		if (status == Enrollment.EnrollmentStatus.COMPLETED) {
			notificationService.createNotification(enrollment.getParticipant(), "Training Completed",
					"Congratulations! You have completed " + enrollment.getTrainingProgram().getName(),
					Notification.NotificationType.COMPLETION_CERTIFICATE, false);

			emailService.sendCompletionCertificate(enrollment.getParticipant().getEmail(),
					enrollment.getParticipant().getName(), enrollment.getTrainingProgram().getName());
		}

		return saved;
	}

	@Transactional
	public Enrollment updateCompletionPercentage(Long enrollmentId, Double percentage) {
		Enrollment enrollment = getEnrollmentById(enrollmentId);
		enrollment.setCompletionPercentage(percentage);

		if (percentage >= 100.0) {
			enrollment.setStatus(Enrollment.EnrollmentStatus.COMPLETED);
		} else if (percentage > 0) {
			enrollment.setStatus(Enrollment.EnrollmentStatus.IN_PROGRESS);
		}

		return enrollmentRepository.save(enrollment);
	}

	@Transactional
	public void withdrawEnrollment(Long enrollmentId) {
		Enrollment enrollment = getEnrollmentById(enrollmentId);
		enrollment.setStatus(Enrollment.EnrollmentStatus.DROPPED);
		enrollmentRepository.save(enrollment);
		log.info("Enrollment withdrawn: {}", enrollmentId);
	}

	@Transactional(readOnly = true)
	public List<Enrollment> getEnrollmentsPendingFeedback() {
		return enrollmentRepository.findByFeedbackSubmitted(false);
	}
}
