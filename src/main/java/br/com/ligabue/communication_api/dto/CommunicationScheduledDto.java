package br.com.ligabue.communication_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommunicationScheduledDto {

    private Long id;
    private String type;
    private String status;
    private String destination;
    private String scheduledDatetime;
    private String message;
}
