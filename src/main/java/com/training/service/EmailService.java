package com.training.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	JavaMailSender mailSender;

	@Value("${app.email.from}")
	private String fromEmail;

	@Value("${app.email.enabled}")
	private boolean emailEnabled;

	public void sendSimpleEmail(String to, String subject, String text) {
		if (!emailEnabled) {
			log.info("Email sending is disabled. Would have sent to: {}", to);
			return;
		}

		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromEmail);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);

			mailSender.send(message);
			log.info("Email sent successfully to: {}", to);
		} catch (Exception e) {
			log.error("Failed to send email to: {}", to, e);
		}
	}

	public void sendHtmlEmail(String to, String subject, String htmlContent) {
		if (!emailEnabled) {
			log.info("Email sending is disabled. Would have sent HTML email to: {}", to);
			return;
		}

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setFrom(fromEmail);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true);

			mailSender.send(mimeMessage);
			log.info("HTML email sent successfully to: {}", to);
		} catch (MessagingException e) {
			log.error("Failed to send HTML email to: {}", to, e);
		}
	}

	public void sendEnrollmentConfirmation(String to, String participantName, String programName) {
		String subject = "Enrollment Confirmation - " + programName;
		String content = buildEnrollmentEmailContent(participantName, programName);
		sendHtmlEmail(to, subject, content);
	}

	public void sendSessionReminder(String to, String participantName, String sessionTitle, String sessionDate) {
		String subject = "Session Reminder - " + sessionTitle;
		String content = buildReminderEmailContent(participantName, sessionTitle, sessionDate);
		sendHtmlEmail(to, subject, content);
	}

	public void sendFeedbackRequest(String to, String participantName, String programName) {
		String subject = "Feedback Request - " + programName;
		String content = buildFeedbackRequestContent(participantName, programName);
		sendHtmlEmail(to, subject, content);
	}

	public void sendCompletionCertificate(String to, String participantName, String programName) {
		String subject = "Training Completion Certificate - " + programName;
		String content = buildCompletionEmailContent(participantName, programName);
		sendHtmlEmail(to, subject, content);
	}

	private String buildEnrollmentEmailContent(String participantName, String programName) {
		return String.format("""
				<html>
				<body>
				    <h2>Welcome to the Training Program!</h2>
				    <p>Dear %s,</p>
				    <p>Congratulations! You have been successfully enrolled in <strong>%s</strong>.</p>
				    <p>You will receive further updates about the schedule and sessions soon.</p>
				    <br>
				    <p>Best regards,<br>Training Team</p>
				</body>
				</html>
				""", participantName, programName);
	}

	private String buildReminderEmailContent(String participantName, String sessionTitle, String sessionDate) {
		return String.format("""
				<html>
				<body>
				    <h2>Session Reminder</h2>
				    <p>Dear %s,</p>
				    <p>This is a reminder that you have an upcoming session:</p>
				    <ul>
				        <li><strong>Session:</strong> %s</li>
				        <li><strong>Date:</strong> %s</li>
				    </ul>
				    <p>Please make sure to attend on time.</p>
				    <br>
				    <p>Best regards,<br>Training Team</p>
				</body>
				</html>
				""", participantName, sessionTitle, sessionDate);
	}

	private String buildFeedbackRequestContent(String participantName, String programName) {
		return String.format(
				"""
						<html>
						<body>
						    <h2>We Value Your Feedback</h2>
						    <p>Dear %s,</p>
						    <p>Thank you for completing <strong>%s</strong>.</p>
						    <p>We would greatly appreciate if you could take a few minutes to provide your feedback on the training program.</p>
						    <p>Your feedback helps us improve our training programs.</p>
						    <br>
						    <p>Best regards,<br>Training Team</p>
						</body>
						</html>
						""",
				participantName, programName);
	}

	private String buildCompletionEmailContent(String participantName, String programName) {
		return String.format("""
				<html>
				<body>
				    <h2>Congratulations on Completing the Training!</h2>
				    <p>Dear %s,</p>
				    <p>Congratulations! You have successfully completed <strong>%s</strong>.</p>
				    <p>Your certificate of completion is now available.</p>
				    <p>We hope this training has been valuable to your professional development.</p>
				    <br>
				    <p>Best regards,<br>Training Team</p>
				</body>
				</html>
				""", participantName, programName);
	}
}
