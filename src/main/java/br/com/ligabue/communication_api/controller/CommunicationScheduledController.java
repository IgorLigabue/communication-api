package br.com.ligabue.communication_api.controller;

import br.com.ligabue.communication_api.dto.CommunicationScheduledDto;
import br.com.ligabue.communication_api.service.CommunicationScheduledService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("communications/scheduled")
public class CommunicationScheduledController {

    CommunicationScheduledService communicationScheduledService;

    @PostMapping
    public ResponseEntity<CommunicationScheduledDto> schedule(
            @RequestBody CommunicationScheduledDto communicationScheduledDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(communicationScheduledService.schedule(communicationScheduledDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunicationScheduledDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(communicationScheduledService.getById(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelById(@PathVariable Long id) {
        communicationScheduledService.cancel(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
