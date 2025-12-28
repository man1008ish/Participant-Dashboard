package com.training.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;
    
    @Column(name = "rating", nullable = false)
    private Integer rating; // 1-5 scale
    
    @Column(columnDefinition = "TEXT")
    private String comments;
    
    @Column(name = "trainer_rating")
    private Integer trainerRating; // 1-5 scale
    
    @Column(name = "content_rating")
    private Integer contentRating; // 1-5 scale
    
    @Column(name = "delivery_rating")
    private Integer deliveryRating; // 1-5 scale
    
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
    
    @Column(name = "recommendation")
    private Boolean recommendation; // Would recommend to others
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getTrainerRating() {
		return trainerRating;
	}

	public void setTrainerRating(Integer trainerRating) {
		this.trainerRating = trainerRating;
	}

	public Integer getContentRating() {
		return contentRating;
	}

	public void setContentRating(Integer contentRating) {
		this.contentRating = contentRating;
	}

	public Integer getDeliveryRating() {
		return deliveryRating;
	}

	public void setDeliveryRating(Integer deliveryRating) {
		this.deliveryRating = deliveryRating;
	}

	public LocalDateTime getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(LocalDateTime submittedAt) {
		this.submittedAt = submittedAt;
	}

	public Boolean getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Boolean recommendation) {
		this.recommendation = recommendation;
	}

	@PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
    }
}
