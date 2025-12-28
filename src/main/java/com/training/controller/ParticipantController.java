package com.training.controller;

import com.training.dto.ParticipantDTO;
import com.training.model.Participant;
import com.training.service.ParticipantService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ParticipantController {
    @Autowired
    ParticipantService participantService;
    
    @PostMapping
    public ResponseEntity<ParticipantDTO> createParticipant(@Valid @RequestBody Participant participant) {
        Participant created = participantService.createParticipant(participant);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ParticipantDTO.fromEntity(created));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable Long id) {
        Participant participant = participantService.getParticipantById(id);
        return ResponseEntity.ok(ParticipantDTO.fromEntity(participant));
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<ParticipantDTO> getParticipantByEmail(@PathVariable String email) {
        Participant participant = participantService.getParticipantByEmail(email);
        return ResponseEntity.ok(ParticipantDTO.fromEntity(participant));
    }
    
    @GetMapping
    public ResponseEntity<List<ParticipantDTO>> getAllParticipants() {
        List<ParticipantDTO> participants = participantService.getAllParticipants()
            .stream()
            .map(ParticipantDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(participants);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ParticipantDTO>> getParticipantsByStatus(@PathVariable String status) {
        Participant.ParticipantStatus participantStatus = Participant.ParticipantStatus.valueOf(status.toUpperCase());
        List<ParticipantDTO> participants = participantService.getParticipantsByStatus(participantStatus)
            .stream()
            .map(ParticipantDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(participants);
    }
    
    @GetMapping("/department/{department}")
    public ResponseEntity<List<ParticipantDTO>> getParticipantsByDepartment(@PathVariable String department) {
        List<ParticipantDTO> participants = participantService.getParticipantsByDepartment(department)
            .stream()
            .map(ParticipantDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(participants);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ParticipantDTO> updateParticipant(
            @PathVariable Long id,
            @Valid @RequestBody Participant participant) {
        Participant updated = participantService.updateParticipant(id, participant);
        return ResponseEntity.ok(ParticipantDTO.fromEntity(updated));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }
}
