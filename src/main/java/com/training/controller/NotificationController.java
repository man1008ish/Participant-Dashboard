package com.training.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.model.Notification;
import com.training.model.Participant;
import com.training.service.NotificationService;
import com.training.service.ParticipantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationController {
	@Autowired
	NotificationService notificationService;

	@Autowired
	ParticipantService participantService;

	@GetMapping("/participant/{participantId}")
	public ResponseEntity<List<Notification>> getNotificationsByParticipant(@PathVariable Long participantId) {
		Participant participant = participantService.getParticipantById(participantId);
		List<Notification> notifications = notificationService.getNotificationsByParticipant(participant);
		return ResponseEntity.ok(notifications);
	}

	@GetMapping("/participant/{participantId}/unread")
	public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Long participantId) {
		Participant participant = participantService.getParticipantById(participantId);
		List<Notification> notifications = notificationService.getUnreadNotifications(participant);
		return ResponseEntity.ok(notifications);
	}

	@GetMapping("/participant/{participantId}/unread-count")
	public ResponseEntity<Map<String, Long>> getUnreadNotificationCount(@PathVariable Long participantId) {
		Participant participant = participantService.getParticipantById(participantId);
		long count = notificationService.getUnreadNotificationCount(participant);
		return ResponseEntity.ok(Map.of("unreadCount", count));
	}

	@PutMapping("/{id}/read")
	public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
		Notification notification = notificationService.markAsRead(id);
		return ResponseEntity.ok(notification);
	}

	@PutMapping("/participant/{participantId}/mark-all-read")
	public ResponseEntity<String> markAllAsRead(@PathVariable Long participantId) {
		Participant participant = participantService.getParticipantById(participantId);
		notificationService.markAllAsRead(participant);
		return ResponseEntity.ok("All notifications marked as read");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
		notificationService.deleteNotification(id);
		return ResponseEntity.noContent().build();
	}
}
