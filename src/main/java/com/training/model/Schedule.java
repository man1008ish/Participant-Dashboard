package com.training.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_program_id", nullable = false)
    private TrainingProgram trainingProgram;
    
    @Column(name = "session_title", nullable = false)
    private String sessionTitle;
    
    @Column(name = "session_date")
    private LocalDateTime sessionDate;
    
    @Column(name = "duration_minutes")
    private Integer durationMinutes;
    
    @Column(name = "location")
    private String location;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SessionStatus status = SessionStatus.SCHEDULED;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TrainingProgram getTrainingProgram() {
		return trainingProgram;
	}

	public void setTrainingProgram(TrainingProgram trainingProgram) {
		this.trainingProgram = trainingProgram;
	}

	public String getSessionTitle() {
		return sessionTitle;
	}

	public void setSessionTitle(String sessionTitle) {
		this.sessionTitle = sessionTitle;
	}

	public LocalDateTime getSessionDate() {
		return sessionDate;
	}

	public void setSessionDate(LocalDateTime sessionDate) {
		this.sessionDate = sessionDate;
	}

	public Integer getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(Integer durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SessionStatus getStatus() {
		return status;
	}

	public void setStatus(SessionStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    public enum SessionStatus {
        SCHEDULED, ONGOING, COMPLETED, CANCELLED
    }
}
