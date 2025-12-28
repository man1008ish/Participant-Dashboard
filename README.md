# Participant Dashboard Application

A comprehensive Java Spring Boot application for automating training program management, including scheduling, participant tracking, and  feedback delivery

## Features

### Core Functionality
- **Participant Management**: Complete CRUD operations for managing training participants
- **Training Program Management**: Create and manage training programs with schedules
- **Automated Scheduling**: Schedule training sessions with automatic notifications
- **Enrollment System**: Enroll participants in programs with capacity management
- **Feedback System**: Collect and analyze participant feedback
### Technology Stack
- **Framework**: Spring Boot 3.2.4
- **Language**: Java 21
- **Database**: MySQL 
- **ORM**: Spring Data JPA 
- **Email**: Spring Mail with SMTP
- **Build Tool**: Maven

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

## License
This is a sample project for demonstration purposes.

## Contact
For questions or support, please contact the Me(8420382264).
