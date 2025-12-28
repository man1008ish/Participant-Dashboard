package com.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ParticipantDto {
	@NotBlank
	@Size(max = 100)
	private String fullName;
	@Email
	@NotBlank
	private String email;
	@NotBlank
	@Size(max = 50)
	private String status;
	@Size(max = 50)
	private String cohort;
}
