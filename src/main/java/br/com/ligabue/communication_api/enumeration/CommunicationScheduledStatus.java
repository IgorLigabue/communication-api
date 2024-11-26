package br.com.ligabue.communication_api.enumeration;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommunicationScheduledStatus {
    PENDING,
    SUBMITTED,
    CANCELLED,
    FAILURE;

    @Nullable
    public static CommunicationScheduledStatus fromString(String type) {
        for (CommunicationScheduledStatus communicationScheduledStatus : CommunicationScheduledStatus.values()) {
            if (communicationScheduledStatus.name().equalsIgnoreCase(type)) {
                return communicationScheduledStatus;
            }
        }
        throw new IllegalArgumentException("Invalid CommunicationScheduledStatus: " + type);
    }

    @Nullable
    public static String toString(CommunicationScheduledStatus status) {
        return status != null ? status.name() : null;
    }
}