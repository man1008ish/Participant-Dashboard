package com.training.service;

import com.training.model.Enrollment;
import com.training.model.Notification;
import com.training.model.Schedule;
import com.training.model.TrainingProgram;
import com.training.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
	@Autowired
	ScheduleRepository scheduleRepository;
	@Autowired
	EnrollmentService enrollmentService;
	@Autowired
	NotificationService notificationService;
	@Autowired
	EmailService emailService;

	@Transactional
	public Schedule createSchedule(Schedule schedule) {
		Schedule savedSchedule = scheduleRepository.save(schedule);
		// log.info("Schedule created for program: {}",
		// schedule.getTrainingProgram().getName());

		// Notify all enrolled participants about the new session
		notifyParticipantsAboutSchedule(savedSchedule);

		return savedSchedule;
	}

	@Transactional(readOnly = true)
	public Schedule getScheduleById(Long id) {
		return scheduleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Schedule not found with id: " + id));
	}

	@Transactional(readOnly = true)
	public List<Schedule> getSchedulesByProgram(TrainingProgram trainingProgram) {
		return scheduleRepository.findByTrainingProgram(trainingProgram);
	}

	@Transactional(readOnly = true)
	public List<Schedule> getUpcomingSessions() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime futureDate = now.plusDays(7);
		return scheduleRepository.findBySessionDateBetween(now, futureDate);
	}

	@Transactional
	public Schedule updateSchedule(Long id, Schedule updatedSchedule) {
		Schedule schedule = getScheduleById(id);

		schedule.setSessionTitle(updatedSchedule.getSessionTitle());
		schedule.setSessionDate(updatedSchedule.getSessionDate());
		schedule.setDurationMinutes(updatedSchedule.getDurationMinutes());
		schedule.setLocation(updatedSchedule.getLocation());
		schedule.setDescription(updatedSchedule.getDescription());
		schedule.setStatus(updatedSchedule.getStatus());

		Schedule saved = scheduleRepository.save(schedule);
		// log.info("Schedule updated: {}", saved.getSessionTitle());

		// Notify participants about schedule update
		notifyParticipantsAboutScheduleUpdate(saved);

		return saved;
	}

	@Transactional
	public void deleteSchedule(Long id) {
		scheduleRepository.deleteById(id);
		// log.info("Schedule deleted with id: {}", id);
	}

	@Transactional
	public void sendSessionReminders() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime tomorrow = now.plusDays(1);

		List<Schedule> upcomingSessions = scheduleRepository.findBySessionDateBetween(now, tomorrow);

		for (Schedule schedule : upcomingSessions) {
			List<Enrollment> enrollments = enrollmentService.getEnrollmentsByProgram(schedule.getTrainingProgram());

			for (Enrollment enrollment : enrollments) {
				if (enrollment.getStatus() == Enrollment.EnrollmentStatus.ENROLLED
						|| enrollment.getStatus() == Enrollment.EnrollmentStatus.IN_PROGRESS) {

					String dateStr = schedule.getSessionDate()
							.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));

					notificationService.createNotification(enrollment.getParticipant(), "Session Reminder",
							"Upcoming session: " + schedule.getSessionTitle() + " on " + dateStr,
							Notification.NotificationType.SESSION_REMINDER, false);

					emailService.sendSessionReminder(enrollment.getParticipant().getEmail(),
							enrollment.getParticipant().getName(), schedule.getSessionTitle(), dateStr);
				}
			}
		}

		// log.info("Session reminders sent for {} sessions", upcomingSessions.size());
	}

	private void notifyParticipantsAboutSchedule(Schedule schedule) {
		List<Enrollment> enrollments = enrollmentService.getEnrollmentsByProgram(schedule.getTrainingProgram());

		String dateStr = schedule.getSessionDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));

		for (Enrollment enrollment : enrollments) {
			notificationService.createNotification(enrollment.getParticipant(), "New Session Scheduled",
					schedule.getSessionTitle() + " scheduled on " + dateStr,
					Notification.NotificationType.SCHEDULE_UPDATE, true);
		}
	}

	private void notifyParticipantsAboutScheduleUpdate(Schedule schedule) {
		List<Enrollment> enrollments = enrollmentService.getEnrollmentsByProgram(schedule.getTrainingProgram());

		String dateStr = schedule.getSessionDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));

		for (Enrollment enrollment : enrollments) {
			notificationService.createNotification(enrollment.getParticipant(), "Schedule Updated",
					"Session " + schedule.getSessionTitle() + " has been updated. New date: " + dateStr,
					Notification.NotificationType.SCHEDULE_UPDATE, true);
		}
	}
}
