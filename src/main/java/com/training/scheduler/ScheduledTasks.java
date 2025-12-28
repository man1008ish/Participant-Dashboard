package com.training.scheduler;

import com.training.exception.GlobalExceptionHandler;
import com.training.service.FeedbackService;
import com.training.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	@Autowired
	ScheduleService scheduleService;

	@Autowired
	FeedbackService feedbackService;

	/**
	 * Send session reminders every day at 9:00 AM Cron expression: second, minute,
	 * hour, day of month, month, day of week
	 */
	@Scheduled(cron = "0 0 9 * * *")
	public void sendDailySessionReminders() {
		log.info("Starting daily session reminder task");
		try {
			scheduleService.sendSessionReminders();
			log.info("Daily session reminders sent successfully");
		} catch (Exception e) {
			log.error("Error sending session reminders", e);
		}
	}

	/**
	 * Send feedback requests every week on Monday at 10:00 AM
	 */
	@Scheduled(cron = "0 0 10 * * MON")
	public void sendWeeklyFeedbackRequests() {
		log.info("Starting weekly feedback request task");
		try {
			feedbackService.sendFeedbackRequests();
			log.info("Weekly feedback requests sent successfully");
		} catch (Exception e) {
			log.error("Error sending feedback requests", e);
		}
	}

	/**
	 * Alternative: Run every 24 hours
	 */
	// @Scheduled(fixedRate = 86400000) // 24 hours in milliseconds
	public void periodicSessionReminders() {
		scheduleService.sendSessionReminders();
	}
}
