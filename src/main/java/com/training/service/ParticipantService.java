package com.training.service;

import com.training.model.Participant;
import com.training.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantService {
	@Autowired 
    ParticipantRepository participantRepository;
    
    @Transactional
    public Participant createParticipant(Participant participant) {
        if (participantRepository.existsByEmail(participant.getEmail())) {
            throw new RuntimeException("Participant with email " + participant.getEmail() + " already exists");
        }
        
        Participant savedParticipant = participantRepository.save(participant);
        //log.info("Participant created: {}", savedParticipant.getEmail());
        return savedParticipant;
    }
    
    @Transactional(readOnly = true)
    public Participant getParticipantById(Long id) {
        return participantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Participant not found with id: " + id));
    }
    
    @Transactional(readOnly = true)
    public Participant getParticipantByEmail(String email) {
        return participantRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Participant not found with email: " + email));
    }
    
    @Transactional(readOnly = true)
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Participant> getParticipantsByStatus(Participant.ParticipantStatus status) {
        return participantRepository.findByStatus(status);
    }
    
    @Transactional(readOnly = true)
    public List<Participant> getParticipantsByDepartment(String department) {
        return participantRepository.findByDepartment(department);
    }
    
    @Transactional
    public Participant updateParticipant(Long id, Participant updatedParticipant) {
        Participant participant = getParticipantById(id);
        
        participant.setName(updatedParticipant.getName());
        participant.setPhoneNumber(updatedParticipant.getPhoneNumber());
        participant.setDepartment(updatedParticipant.getDepartment());
        participant.setDesignation(updatedParticipant.getDesignation());
        participant.setStatus(updatedParticipant.getStatus());
        
        Participant saved = participantRepository.save(participant);
        //log.info("Participant updated: {}", saved.getEmail());
        return saved;
    }
    
    @Transactional
    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
      //  log.info("Participant deleted with id: {}", id);
    }
}
