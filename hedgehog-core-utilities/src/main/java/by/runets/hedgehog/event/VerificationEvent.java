package by.runets.hedgehog.event;

import by.runets.hedgehog.domain.Subject;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class VerificationEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private EventType eventType;
    private String code;
    private Subject subject;
    private Instant occurredAt;

    public VerificationEvent() {
    }

    public VerificationEvent(String code, Subject subject, Instant occurredAt, EventType eventType) {
        this.id = UUID.randomUUID();
        this.eventType = eventType;
        this.code = code;
        this.subject = subject;
        this.occurredAt = occurredAt;
    }

    public UUID getId() {
        return id;
    }
    public String getCode() {
        return code;
    }
    public Subject getSubject() {
        return subject;
    }
    public Instant getOccurredAt() {
        return occurredAt;
    }
    public EventType getEventType() {
        return eventType;
    }
}
