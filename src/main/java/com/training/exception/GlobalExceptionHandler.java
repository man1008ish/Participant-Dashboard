package com.training.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

//import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	// private static final Log log =
	// LoggerFactory.getLog(GlobalExceptionHandler.class);

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
		log.error("Runtime exception occurred: ", ex);
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("errors", errors);
		response.put("timestamp", LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		log.error("Unexpected exception occurred: ", ex);
		ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"An unexpected error occurred", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	public static class ErrorResponse {
		private int status;
		private String message;
		private LocalDateTime timestamp;

		public ErrorResponse(int status, String message, LocalDateTime timestamp) {
			this.status = status;
			this.message = message;
			this.timestamp = timestamp;
		}

		// Getters
		public int getStatus() {
			return status;
		}

		public String getMessage() {
			return message;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}
	}
}
