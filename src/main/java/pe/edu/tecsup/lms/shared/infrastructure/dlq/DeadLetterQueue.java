package pe.edu.tecsup.lms.shared.infrastructure.dlq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.edu.tecsup.lms.shared.domain.event.DomainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeadLetterQueue {

    //Sin persistencia
    /*private final ConcurrentLinkedQueue<FailedEvent> failedEvents = new ConcurrentLinkedQueue<>();

    public void add(DomainEvent event, Exception exception) {
        FailedEvent failedEvent = new FailedEvent(
                event,
                exception.getMessage(),
                System.currentTimeMillis()
        );
        failedEvents.add(failedEvent);
        log.warn("Evento agregado a DLQ: {}", event.getEventType());
    }

    public List<FailedEvent> getFailedEvents() {
        return new ArrayList<>(failedEvents);
    }*/

    private final FailedEventRepository repository;
    private final ObjectMapper objectMapper;

    public void add(DomainEvent event, Exception exception) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            FailedEvent failedEvent = new FailedEvent();
            failedEvent.setEventId(event.getEventId());
            failedEvent.setEventType(event.getEventType());
            failedEvent.setPayload(payload);
            failedEvent.setMessage(exception.getMessage());
            failedEvent.setTimestamp(System.currentTimeMillis());
            repository.save(failedEvent);
            log.warn("Evento agregado a DLQ: {}", event.getEventType());
        } catch (Exception e) {
            log.error("Error al guardar en DLQ: {}", e.getMessage());
        }
    }

    public List<FailedEvent> getFailedEvents() {
        return repository.findAll();
    }

}
