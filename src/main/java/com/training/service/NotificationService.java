package com.training.service;

import com.training.model.Notification;
import com.training.model.Participant;
import com.training.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
	@Autowired
     NotificationRepository notificationRepository;
	@Autowired
EmailService emailService;
    
    @Transactional
    public Notification createNotification(Participant participant, String title, String message, 
                                          Notification.NotificationType type, boolean sendEmail) {
        Notification notification = new Notification();
        notification.setParticipant(participant);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);
        notification.setIsRead(false);
        notification.setIsEmailSent(false);
        
        notification = notificationRepository.save(notification);
       // log.info("Notification created for participant: {}", participant.getEmail());
        
        if (sendEmail) {
            sendNotificationEmail(notification);
        }
        
        return notification;
    }
    
    @Transactional
    public void sendNotificationEmail(Notification notification) {
        try {
            emailService.sendSimpleEmail(
                notification.getParticipant().getEmail(),
                notification.getTitle(),
                notification.getMessage()
            );
            
            notification.setIsEmailSent(true);
            notificationRepository.save(notification);
          //  log.info("Email sent for notification ID: {}", notification.getId());
        } catch (Exception e) {
          //  log.error("Failed to send email for notification ID: {}", notification.getId(), e);
        }
    }
    
    @Transactional(readOnly = true)
    public List<Notification> getNotificationsByParticipant(Participant participant) {
        return notificationRepository.findByParticipant(participant);
    }
    
    @Transactional(readOnly = true)
    public List<Notification> getUnreadNotifications(Participant participant) {
        return notificationRepository.findByParticipantAndIsRead(participant, false);
    }
    
    @Transactional(readOnly = true)
    public long getUnreadNotificationCount(Participant participant) {
        return notificationRepository.countByParticipantAndIsRead(participant, false);
    }
    
    @Transactional
    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        
        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());
        
        return notificationRepository.save(notification);
    }
    
    @Transactional
    public void markAllAsRead(Participant participant) {
        List<Notification> unreadNotifications = getUnreadNotifications(participant);
        
        for (Notification notification : unreadNotifications) {
            notification.setIsRead(true);
            notification.setReadAt(LocalDateTime.now());
        }
        
        notificationRepository.saveAll(unreadNotifications);
       // log.info("All notifications marked as read for participant: {}", participant.getEmail());
    }
    
    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
       // log.info("Notification deleted: {}", notificationId);
    }
}
