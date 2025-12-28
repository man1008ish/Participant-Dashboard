package com.training.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_program_id", nullable = false)
    private TrainingProgram trainingProgram;
    
    @Column(name = "enrollment_date")
    private LocalDateTime enrollmentDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EnrollmentStatus status = EnrollmentStatus.ENROLLED;
    
    @Column(name = "completion_percentage")
    private Double completionPercentage = 0.0;
    
    @Column(name = "feedback_submitted")
    private Boolean feedbackSubmitted = false;
    
    @Column(name = "certificate_issued")
    private Boolean certificateIssued = false;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public TrainingProgram getTrainingProgram() {
		return trainingProgram;
	}

	public void setTrainingProgram(TrainingProgram trainingProgram) {
		this.trainingProgram = trainingProgram;
	}

	public LocalDateTime getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(LocalDateTime enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public EnrollmentStatus getStatus() {
		return status;
	}

	public void setStatus(EnrollmentStatus status) {
		this.status = status;
	}

	public Double getCompletionPercentage() {
		return completionPercentage;
	}

	public void setCompletionPercentage(Double completionPercentage) {
		this.completionPercentage = completionPercentage;
	}

	public Boolean getFeedbackSubmitted() {
		return feedbackSubmitted;
	}

	public void setFeedbackSubmitted(Boolean feedbackSubmitted) {
		this.feedbackSubmitted = feedbackSubmitted;
	}

	public Boolean getCertificateIssued() {
		return certificateIssued;
	}

	public void setCertificateIssued(Boolean certificateIssued) {
		this.certificateIssued = certificateIssued;
	}

	@PrePersist
    protected void onCreate() {
        enrollmentDate = LocalDateTime.now();
    }
    
    public enum EnrollmentStatus {
        ENROLLED, IN_PROGRESS, COMPLETED, DROPPED, FAILED
    }
}
