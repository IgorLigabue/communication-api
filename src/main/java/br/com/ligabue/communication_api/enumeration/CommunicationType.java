package br.com.ligabue.communication_api.enumeration;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommunicationType {
    EMAIL,
    SMS,
    PUSH,
    WHATSAPP;

    @Nullable
    public static CommunicationType fromString(String type) {
        for (CommunicationType communicationType : CommunicationType.values()) {
            if (communicationType.name().equalsIgnoreCase(type)) {
                return communicationType;
            }
        }
        throw new IllegalArgumentException("Invalid CommunicationType: " + type);
    }

    @Nullable
    public static String toString(CommunicationType type) {
        return type != null ? type.name() : null;
    }
}
