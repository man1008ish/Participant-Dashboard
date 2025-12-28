package com.training.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.training.model.Participant;
import com.training.model.TrainingProgram;
import com.training.repository.ParticipantRepository;
import com.training.repository.TrainingProgramRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
	@Autowired
	ParticipantRepository participantRepository;
	@Autowired
	TrainingProgramRepository trainingProgramRepository;

	@Override
	public void run(String... args) {
		// log.info("Initializing sample data...");
		System.out.println("Initializing sample data...");

		// Create sample participants
		if (participantRepository.count() == 0) {
			Participant p1 = new Participant();

			p1.setName("Alice Johnson");
			p1.setEmail("alice.johnson@company.com");
			p1.setPhoneNumber("+1234567890");
			p1.setDepartment("IT");
			p1.setDesignation("Software Developer");
			p1.setStatus(Participant.ParticipantStatus.ACTIVE);

			Participant p2 = new Participant();
			p2.setName("Bob Smith");
			p2.setEmail("bob.smith@company.com");
			p2.setPhoneNumber("+1234567891");
			p2.setDepartment("IT");
			p2.setDesignation("Senior Developer");
			p2.setStatus(Participant.ParticipantStatus.ACTIVE);

			Participant p3 = new Participant();
			p3.setName("Carol White");
			p3.setEmail("carol.white@company.com");
			p3.setPhoneNumber("+1234567892");
			p3.setDepartment("HR");
			p3.setDesignation("HR Manager");
			p3.setStatus(Participant.ParticipantStatus.ACTIVE);

			participantRepository.save(p1);
			participantRepository.save(p2);
			participantRepository.save(p3);

//			log.info("Sample participants created");
			System.out.println("Sample participants created");
		}

		// Create sample training programs
		if (trainingProgramRepository.count() == 0) {
			TrainingProgram program1 = new TrainingProgram();
			program1.setName("Spring Boot Fundamentals");
			program1.setDescription(
					"Learn the basics of Spring Boot framework including dependency injection, REST APIs, and data access.");
			program1.setTrainerName("Dr. John Anderson");
			program1.setDurationHours(40);
			program1.setStartDate(LocalDateTime.now().plusDays(7));
			program1.setEndDate(LocalDateTime.now().plusDays(12));
			program1.setMaxParticipants(25);
			program1.setStatus(TrainingProgram.ProgramStatus.SCHEDULED);

			TrainingProgram program2 = new TrainingProgram();
			program2.setName("Advanced Java Programming");
			program2.setDescription(
					"Deep dive into advanced Java concepts including concurrency, streams, and design patterns.");
			program2.setTrainerName("Prof. Sarah Miller");
			program2.setDurationHours(32);
			program2.setStartDate(LocalDateTime.now().plusDays(14));
			program2.setEndDate(LocalDateTime.now().plusDays(18));
			program2.setMaxParticipants(20);
			program2.setStatus(TrainingProgram.ProgramStatus.SCHEDULED);

			TrainingProgram program3 = new TrainingProgram();
			program3.setName("Microservices Architecture");
			program3.setDescription("Learn how to design and implement microservices using Spring Cloud and Docker.");
			program3.setTrainerName("Michael Chen");
			program3.setDurationHours(24);
			program3.setStartDate(LocalDateTime.now().plusDays(21));
			program3.setEndDate(LocalDateTime.now().plusDays(24));
			program3.setMaxParticipants(15);
			program3.setStatus(TrainingProgram.ProgramStatus.SCHEDULED);

			trainingProgramRepository.save(program1);
			trainingProgramRepository.save(program2);
			trainingProgramRepository.save(program3);

//			log.info("Sample training programs created");
			System.out.println("Sample training programs created");
		}

//		log.info("Sample data initialization completed");

		System.out.println("Sample data initialization completed");
	}
}
