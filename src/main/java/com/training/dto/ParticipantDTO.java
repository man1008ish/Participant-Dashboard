package com.training.dto;

import com.training.model.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String department;
    private String designation;
    private LocalDateTime enrollmentDate;
    private String status;
    
    
    
    public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPhoneNumber() {
		return phoneNumber;
	}



	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public String getDepartment() {
		return department;
	}



	public void setDepartment(String department) {
		this.department = department;
	}



	public String getDesignation() {
		return designation;
	}



	public void setDesignation(String designation) {
		this.designation = designation;
	}



	public LocalDateTime getEnrollmentDate() {
		return enrollmentDate;
	}



	public void setEnrollmentDate(LocalDateTime enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public static ParticipantDTO fromEntity(Participant participant) {
        ParticipantDTO dto = new ParticipantDTO();
        dto.setId(participant.getId());
        dto.setName(participant.getName());
        dto.setEmail(participant.getEmail());
        dto.setPhoneNumber(participant.getPhoneNumber());
        dto.setDepartment(participant.getDepartment());
        dto.setDesignation(participant.getDesignation());
        dto.setEnrollmentDate(participant.getEnrollmentDate());
        dto.setStatus(participant.getStatus().name());
        return dto;
    }
}
