# Participant Dashboard Application

A comprehensive Java Spring Boot application for automating training program management, including scheduling, participant tracking, feedback delivery, and automated email workflows.

## Features

### Core Functionality
- **Participant Management**: Complete CRUD operations for managing training participants
- **Training Program Management**: Create and manage training programs with schedules
- **Automated Scheduling**: Schedule training sessions with automatic notifications
- **Enrollment System**: Enroll participants in programs with capacity management
- **Feedback System**: Collect and analyze participant feedback
- **Notification System**: In-app notifications with email integration
- **Automated Email Workflows**: 
  - Enrollment confirmations
  - Session reminders
  - Feedback requests
  - Completion certificates

### Technology Stack
- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: MySQL (H2 for development)
- **ORM**: Spring Data JPA / Hibernate
- **Email**: Spring Mail with SMTP
- **Build Tool**: Maven

## Project Structure

```
participant-dashboard/
├── src/
│   ├── main/
│   │   ├── java/com/training/
│   │   │   ├── controller/          # REST API Controllers
│   │   │   │   ├── ParticipantController.java
│   │   │   │   ├── TrainingProgramController.java
│   │   │   │   ├── EnrollmentController.java
│   │   │   │   ├── ScheduleController.java
│   │   │   │   ├── FeedbackController.java
│   │   │   │   └── NotificationController.java
│   │   │   ├── model/               # Entity Models
│   │   │   │   ├── Participant.java
│   │   │   │   ├── TrainingProgram.java
│   │   │   │   ├── Enrollment.java
│   │   │   │   ├── Schedule.java
│   │   │   │   ├── Feedback.java
│   │   │   │   └── Notification.java
│   │   │   ├── repository/          # Data Access Layer
│   │   │   ├── service/             # Business Logic
│   │   │   │   ├── ParticipantService.java
│   │   │   │   ├── TrainingProgramService.java
│   │   │   │   ├── EnrollmentService.java
│   │   │   │   ├── ScheduleService.java
│   │   │   │   ├── FeedbackService.java
│   │   │   │   ├── NotificationService.java
│   │   │   │   └── EmailService.java
│   │   │   ├── scheduler/           # Scheduled Tasks
│   │   │   │   └── ScheduledTasks.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   └── ParticipantDashboardApplication.java
│   │   └── resources/
│   │       └── application.properties
└── pom.xml
```

## Database Schema

### Main Entities
1. **Participants**: Store participant information
2. **Training Programs**: Manage training courses
3. **Enrollments**: Link participants to programs
4. **Schedules**: Session schedules for programs
5. **Feedbacks**: Participant feedback and ratings
6. **Notifications**: User notifications

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (optional, H2 included for development)
- SMTP email server credentials (Gmail recommended)

### Configuration

1. **Database Configuration** (application.properties)

For Development (H2 - Default):
```properties
spring.datasource.url=jdbc:h2:mem:trainingdb
spring.h2.console.enabled=true
```

For Production (MySQL):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/training_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

2. **Email Configuration** (application.properties)

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

**Note**: For Gmail, you need to create an "App Password" from your Google Account settings.

### Running the Application

1. Clone the repository
2. Update `application.properties` with your database and email credentials
3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### H2 Console Access
If using H2 database, access the console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:trainingdb`
- Username: `sa`
- Password: (leave empty)

## API Endpoints

### Participants
- `POST /api/participants` - Create new participant
- `GET /api/participants` - Get all participants
- `GET /api/participants/{id}` - Get participant by ID
- `GET /api/participants/email/{email}` - Get participant by email
- `PUT /api/participants/{id}` - Update participant
- `DELETE /api/participants/{id}` - Delete participant

### Training Programs
- `POST /api/programs` - Create training program
- `GET /api/programs` - Get all programs
- `GET /api/programs/{id}` - Get program by ID
- `GET /api/programs/upcoming` - Get upcoming programs
- `PUT /api/programs/{id}` - Update program
- `DELETE /api/programs/{id}` - Delete program

### Enrollments
- `POST /api/enrollments` - Enroll participant (requires participantId and programId)
- `GET /api/enrollments/{id}` - Get enrollment by ID
- `GET /api/enrollments/participant/{participantId}` - Get participant's enrollments
- `GET /api/enrollments/program/{programId}` - Get program enrollments
- `PUT /api/enrollments/{id}/status` - Update enrollment status
- `PUT /api/enrollments/{id}/completion` - Update completion percentage
- `DELETE /api/enrollments/{id}` - Withdraw enrollment

### Schedules
- `POST /api/schedules` - Create schedule
- `GET /api/schedules/{id}` - Get schedule by ID
- `GET /api/schedules/program/{programId}` - Get program schedules
- `GET /api/schedules/upcoming` - Get upcoming sessions
- `PUT /api/schedules/{id}` - Update schedule
- `POST /api/schedules/send-reminders` - Manually send session reminders
- `DELETE /api/schedules/{id}` - Delete schedule

### Feedbacks
- `POST /api/feedbacks/enrollment/{enrollmentId}` - Submit feedback
- `GET /api/feedbacks/{id}` - Get feedback by ID
- `GET /api/feedbacks/enrollment/{enrollmentId}` - Get enrollment feedback
- `GET /api/feedbacks/program/{programId}/average-rating` - Get program average rating
- `POST /api/feedbacks/send-requests` - Manually send feedback requests

### Notifications
- `GET /api/notifications/participant/{participantId}` - Get participant notifications
- `GET /api/notifications/participant/{participantId}/unread` - Get unread notifications
- `GET /api/notifications/participant/{participantId}/unread-count` - Get unread count
- `PUT /api/notifications/{id}/read` - Mark notification as read
- `PUT /api/notifications/participant/{participantId}/mark-all-read` - Mark all as read
- `DELETE /api/notifications/{id}` - Delete notification

## Example API Calls

### Create a Participant
```bash
curl -X POST http://localhost:8080/api/participants \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "department": "IT",
    "designation": "Software Engineer"
  }'
```

### Create a Training Program
```bash
curl -X POST http://localhost:8080/api/programs \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Spring Boot Fundamentals",
    "description": "Comprehensive Spring Boot training",
    "trainerName": "Jane Smith",
    "durationHours": 40,
    "startDate": "2025-01-15T09:00:00",
    "endDate": "2025-01-19T17:00:00",
    "maxParticipants": 20,
    "status": "SCHEDULED"
  }'
```

### Enroll a Participant
```bash
curl -X POST http://localhost:8080/api/enrollments \
  -H "Content-Type: application/json" \
  -d '{
    "participantId": 1,
    "programId": 1
  }'
```

## Automated Features

### Scheduled Tasks
The application includes automated scheduled tasks:

1. **Daily Session Reminders** (9:00 AM daily)
   - Sends reminders for sessions happening in the next 24 hours
   - Notifications sent via in-app and email

2. **Weekly Feedback Requests** (10:00 AM every Monday)
   - Sends feedback requests to participants who completed programs
   - Only sent to those who haven't submitted feedback

### Email Workflows
Automated emails are sent for:
- Enrollment confirmation
- Session reminders (24 hours before)
- Schedule updates
- Feedback requests
- Completion certificates

## Development Notes

### Adding Security (Future Enhancement)
This version does not include security implementation. To add security:
1. Add Spring Security dependency
2. Implement authentication (JWT/OAuth2)
3. Add authorization to endpoints
4. Implement user roles (Admin, Trainer, Participant)

### Customization
- Modify email templates in `EmailService.java`
- Adjust scheduled task timings in `ScheduledTasks.java`
- Add custom validation in entity models
- Extend functionality with additional features

## Testing

Use tools like:
- **Postman**: For API testing
- **curl**: For command-line testing
- **H2 Console**: For database inspection
- **Spring Boot Actuator**: For application monitoring (add dependency if needed)

## Troubleshooting

### Email Not Sending
- Verify SMTP credentials
- Enable "Less secure app access" or use App Password for Gmail
- Check firewall settings for port 587

### Database Connection Issues
- Verify MySQL is running
- Check database credentials
- Ensure database exists (`training_db`)

### Port Already in Use
Change the port in `application.properties`:
```properties
server.port=8081
```

## License
This is a sample project for demonstration purposes.

## Contact
For questions or support, please contact the development team.
