package br.com.ligabue.communication_api.entity;

import br.com.ligabue.communication_api.enumeration.CommunicationScheduledStatus;
import br.com.ligabue.communication_api.enumeration.CommunicationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "communication_scheduled")
public class CommunicationScheduled {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CommunicationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CommunicationScheduledStatus status;

    @Column(name = "destination")
    private String destination;

    @Column(name = "scheduled_datetime")
    private ZonedDateTime scheduledDatetime;

    @Column(name = "inserted_on")
    private ZonedDateTime insertedOn;

    @Column(name = "message")
    private String message;

    @PrePersist
    public void prePersist() {
        this.insertedOn = ZonedDateTime.now();
    }
}