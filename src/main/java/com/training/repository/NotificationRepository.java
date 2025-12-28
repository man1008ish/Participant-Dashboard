package com.training.repository;

import com.training.model.Notification;
import com.training.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByParticipant(Participant participant);
    
    List<Notification> findByParticipantAndIsRead(Participant participant, Boolean isRead);
    
    List<Notification> findByIsEmailSent(Boolean isEmailSent);
    
    long countByParticipantAndIsRead(Participant participant, Boolean isRead);
}
